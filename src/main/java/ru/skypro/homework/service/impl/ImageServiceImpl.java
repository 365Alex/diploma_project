package ru.skypro.homework.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{
    @Value("${image.avatars.dir}")
    private String avatarsDir;

    @Value("${image.ads.dir}")
    private String adsDir;

    @Value("${image.base.url}")
    private String baseUrl;

    @Override
    public String saveImage(MultipartFile image, String directory) {
        try {
            // Определяем директорию для сохранения
            String targetDir;
            if ("avatars".equals(directory)) {
                targetDir = avatarsDir;
            } else if ("ads".equals(directory)) {
                targetDir = adsDir;
            } else {
                targetDir = directory;
            }

            // Создаем директорию, если её нет
            Path uploadPath = Paths.get(targetDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Генерируем уникальное имя файла
            String originalFilename = image.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID() + extension;

            // Сохраняем файл
            Path filePath = uploadPath.resolve(filename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Image saved: {}", filePath);

            // Возвращаем URL для доступа к изображению
            return baseUrl + "/" + filename;

        } catch (IOException e) {
            log.error("Failed to save image", e);
            throw new RuntimeException("Failed to save image", e);
        }
    }

    @Override
    public byte[] getImage(String imageName) throws IOException {
        // Ищем изображение сначала в папке с аватарами, затем в папке с объявлениями
        Path avatarPath = Paths.get(avatarsDir, imageName);
        Path adPath = Paths.get(adsDir, imageName);

        if (Files.exists(avatarPath)) {
            return Files.readAllBytes(avatarPath);
        } else if (Files.exists(adPath)) {
            return Files.readAllBytes(adPath);
        } else {
            log.warn("Image not found: {}", imageName);
            throw new IOException("Image not found: " + imageName);
        }
    }

    @Override
    public void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return;
        }

        try {
            // Извлекаем имя файла из URL
            String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);

            // Удаляем из обеих возможных директорий
            Path avatarPath = Paths.get(avatarsDir, filename);
            Path adPath = Paths.get(adsDir, filename);

            if (Files.exists(avatarPath)) {
                Files.delete(avatarPath);
                log.info("Deleted avatar: {}", avatarPath);
            } else if (Files.exists(adPath)) {
                Files.delete(adPath);
                log.info("Deleted ad image: {}", adPath);
            }
        } catch (IOException e) {
            log.error("Failed to delete image: {}", imagePath, e);
        }
    }
}
