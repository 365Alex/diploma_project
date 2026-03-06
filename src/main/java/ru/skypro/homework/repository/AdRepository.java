package ru.skypro.homework.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Integer> {

    // Поиск по ID автора (наиболее удобный вариант)
    List<AdEntity> findByAuthorId(Integer authorId);

    // Поиск по объекту UserEntity (оставляем для совместимости)
    List<AdEntity> findByAuthor(UserEntity author);

    // Проверка авторства объявления
    boolean existsByPkAndAuthorId(Integer adId, Integer authorId);

    // Получение объявления с проверкой авторства
    Optional<AdEntity> findByPkAndAuthorId(Integer pk, Integer authorId);
}