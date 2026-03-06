package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private static final Logger log = Logger.getLogger(AuthServiceImpl.class.getName());

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean login(String userName, String password) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            return encoder.matches(password, userDetails.getPassword());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Login error for user: " + userName, e);
            return false;
        }
    }

    @Override
    public boolean register(Register register) {
        try {
            log.info("Attempting to register user: " + register.getUsername());

            if (userRepository.existsByEmail(register.getUsername())) {
                log.warning("User already exists: " + register.getUsername());
                return false;
            }

            UserEntity userEntity = userMapper.toEntity(register);
            userEntity.setPassword(encoder.encode(register.getPassword()));
            userRepository.save(userEntity);

            log.info("User successfully registered: " + register.getUsername());
            return true;

        } catch (Exception e) {
            log.log(Level.SEVERE, "Registration error for user: " + register.getUsername(), e);
            return false;
        }
    }
}
