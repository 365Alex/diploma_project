package ru.skypro.homework.service;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

public interface UserService {
    /**
     * Устанавливает новый пароль для пользователя.
     *
     * @param newPassword    данные нового пароля
     * @param authentication данные аутентификации
     */
    void setPassword(NewPassword newPassword, Authentication authentication);
    /**
     * Возвращает информацию о текущем пользователе.
     *
     * @param authentication данные аутентификации
     * @return DTO пользователя
     */
    User getUser(Authentication authentication);
    /**
     * Обновляет информацию о пользователе.
     *
     * @param updateUser     новые данные
     * @param authentication данные аутентификации
     * @return обновлённые данные
     */
    UpdateUser updateUser(UpdateUser updateUser, Authentication authentication);
    /**
     * Обновляет аватар пользователя.
     *
     * @param image          новый файл аватара
     * @param authentication данные аутентификации
     */
    void updateUserImage(MultipartFile image, Authentication authentication);
}
