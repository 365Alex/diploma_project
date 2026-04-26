package ru.skypro.homework.service;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
/**
 * Сервис для работы с объявлениями.
 */
public interface AdService {
    /**
     * Возвращает все объявления.
     *
     * @return объект Ads со списком объявлений
     */
    Ads getAllAds();
    /**
     * Добавляет новое объявление.
     *
     * @param properties     данные объявления
     * @param image          файл изображения
     * @param authentication данные аутентификации текущего пользователя
     * @return созданное объявление
     */
    Ad addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication);

    /**
     * Возвращает расширенную информацию об объявлении по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return расширенное DTO объявления
     */
    ExtendedAd getAd(Integer id);
    /**
     * Удаляет объявление.
     *
     * @param id             идентификатор объявления
     * @param authentication данные аутентификации
     */
    void removeAd(Integer id, Authentication authentication);
    /**
     * Обновляет информацию об объявлении.
     *
     * @param id             идентификатор объявления
     * @param ad             новые данные объявления
     * @param authentication данные аутентификации
     * @return обновлённое объявление
     */
    Ad updateAd(Integer id, CreateOrUpdateAd ad, Authentication authentication);
    /**
     * Возвращает объявления текущего пользователя.
     *
     * @param authentication данные аутентификации
     * @return объект Ads со списком объявлений пользователя
     */
    Ads getAdsMe(Authentication authentication);
    /**
     * Обновляет изображение объявления.
     *
     * @param id             идентификатор объявления
     * @param image          новый файл изображения
     * @param authentication данные аутентификации
     * @return массив байтов нового изображения (может быть пустым)
     */
    byte[] updateImage(Integer id, MultipartFile image, Authentication authentication);
}
