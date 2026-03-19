package ru.skypro.homework.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Integer> {

    /**
     * Находит все объявления по идентификатору автора.
     *
     * @param authorId идентификатор автора
     * @return список объявлений
     */
    List<AdEntity> findByAuthorId(Integer authorId);

    /**
     * Находит все объявления по автору.
     *
     * @param author автор
     * @return список объявлений
     */
    List<AdEntity> findByAuthor(UserEntity author);

    /**
     * Проверяет, существует ли объявление с данным идентификатором и автором.
     *
     * @param adId     идентификатор объявления
     * @param authorId идентификатор автора
     * @return true, если существует, иначе false
     */
    boolean existsByPkAndAuthorId(Integer adId, Integer authorId);

    /**
     * Находит объявление по идентификатору и автору.
     *
     * @param pk       идентификатор объявления
     * @param authorId идентификатор автора
     * @return Optional с объявлением или пустой
     */
    Optional<AdEntity> findByPkAndAuthorId(Integer pk, Integer authorId);
}