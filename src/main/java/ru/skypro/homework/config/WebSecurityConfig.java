package ru.skypro.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.skypro.homework.filter.BasicAuthCorsFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final BasicAuthCorsFilter basicAuthCorsFilter;
    private final DataSource dataSource;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(authorization ->
                        authorization
                                .mvcMatchers(AUTH_WHITELIST).permitAll()
                                .mvcMatchers(HttpMethod.GET, "/ads").permitAll()
                                .mvcMatchers("/ads/**", "/users/**").authenticated()
                                .anyRequest().authenticated())
                .cors()
                .and()
                .addFilterBefore(basicAuthCorsFilter, BasicAuthenticationFilter.class)
                .httpBasic(withDefaults())
                .userDetailsService(userDetailsService());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Настройка SQL запросов для работы с нашей схемой БД
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, true from users where email = ?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select email, 'ROLE_' || role from users where email = ?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
