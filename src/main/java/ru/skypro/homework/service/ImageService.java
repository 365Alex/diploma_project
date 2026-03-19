package ru.skypro.homework.service;
import org.springframework.web.multipart.MultipartFile;
/*
 * Сервис для работы с изображениями (сохранение, получение, удаление).
 */
import java.io.IOException;
public interface ImageService {

    /**
     * Сохраняет изображение в указанную директорию.
     *
     * @param image     файл изображения
     * @param directory поддиректория (avatars, ads и т.д.)
     * @return относительный путь к сохранённому файлу
     */
    String saveImage(MultipartFile image, String directory);
    /**
     * Получает изображение по имени файла.
     *
     * @param imageName имя файла изображения
     * @return массив байтов изображения
     * @throws IOException если файл не найден или ошибка чтения
     */
    byte[] getImage(String imageName) throws IOException;
    /**
     * Удаляет изображение по пути.
     *
     * @param imagePath путь к изображению
     */
    void deleteImage(String imagePath);
}
