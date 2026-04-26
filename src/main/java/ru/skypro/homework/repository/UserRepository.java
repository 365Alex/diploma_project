package ru.skypro.homework.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * Находит пользователя по email.
     *
     * @param email email пользователя
     * @return Optional с пользователем или пустой
     */
    Optional<UserEntity> findByEmail(String email);
    /**
     * Проверяет, существует ли пользователь с указанным email.
     *
     * @param email email
     * @return true, если существует, иначе false
     */
    boolean existsByEmail(String email);
}
