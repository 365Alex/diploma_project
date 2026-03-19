package ru.skypro.homework.service;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
/**
 * Сервис для работы с комментариями.
 */
public interface CommentService {
    /**
     * Возвращает все комментарии для указанного объявления.
     *
     * @param adId идентификатор объявления
     * @return объект Comments со списком комментариев
     */
    Comments getComments(Integer adId);
    /**
     * Добавляет комментарий к объявлению.
     *
     * @param adId           идентификатор объявления
     * @param comment        данные комментария
     * @param authentication данные аутентификации текущего пользователя
     * @return созданный комментарий
     */
    Comment addComment(Integer adId, CreateOrUpdateComment comment, Authentication authentication);
    /**
     * Удаляет комментарий.
     *
     * @param adId           идентификатор объявления
     * @param commentId      идентификатор комментария
     * @param authentication данные аутентификации
     */
    void deleteComment(Integer adId, Integer commentId, Authentication authentication);

    /**
     * Обновляет существующий комментарий.
     *
     * @param adId           идентификатор объявления
     * @param commentId      идентификатор комментария
     * @param comment        новые данные комментария
     * @param authentication данные аутентификации
     * @return обновлённый комментарий
     */
    Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment,
                          Authentication authentication);
}
