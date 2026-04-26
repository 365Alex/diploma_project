package ru.skypro.homework.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;

    @Value("${image.base.url}")
    private String baseUrl;

    @Override
    public Ads getAllAds() {
        List<AdEntity> ads = adRepository.findAll();
        Ads result = new Ads();
        result.setCount(ads.size());
        result.setResults(ads.stream()
                .map(this::mapToAdDtoWithImageUrl)
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    @Transactional
    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication) {
        log.info("========== AdService.addAd ==========");
        log.info("Authentication: {}", authentication.getName());
        log.info("Properties: {}", properties);

        // Получаем автора
        UserEntity author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> {
                    log.error("User not found: {}", authentication.getName());
                    return new RuntimeException("User not found: " + authentication.getName());
                });
        log.info("Author found: ID={}, email={}", author.getId(), author.getEmail());

        // Создаем объявление
        AdEntity adEntity = adMapper.mapToEntity(properties, author);
        log.info("Ad entity created: {}", adEntity);

        // Сохраняем изображение
        if (image != null && !image.isEmpty()) {
            log.info("Saving image: {}", image.getOriginalFilename());
            String imageUrl = imageService.saveImage(image, "ads");
            adEntity.setImage(imageUrl);
            log.info("Image saved with URL: {}", imageUrl);
        } else {
            log.warn("Image is empty or null");
        }

        // Сохраняем объявление
        AdEntity savedAd = adRepository.save(adEntity);
        log.info("Ad saved with ID: {}", savedAd.getPk());

        return mapToAdDtoWithImageUrl(savedAd);
    }

    @Override
    public ExtendedAd getAd(Integer id) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
        return mapToExtendedAdDtoWithImageUrl(adEntity);
    }

    @Override
    @Transactional
    public void removeAd(Integer id, Authentication authentication) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found"));

        checkAccess(adEntity, authentication);

        // Удаляем изображение, если оно есть
        if (adEntity.getImage() != null) {
            imageService.deleteImage(adEntity.getImage());
        }

        adRepository.delete(adEntity);
    }

    @Override
    @Transactional
    public Ad updateAd(Integer id, CreateOrUpdateAd ad, Authentication authentication) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found"));

        checkAccess(adEntity, authentication);

        adMapper.updateEntity(ad, adEntity);
        AdEntity updatedAd = adRepository.save(adEntity);
        return mapToAdDtoWithImageUrl(updatedAd);
    }

    @Override
    public Ads getAdsMe(Authentication authentication) {
        UserEntity user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<AdEntity> ads = adRepository.findByAuthor(user);
        Ads result = new Ads();
        result.setCount(ads.size());
        result.setResults(ads.stream()
                .map(this::mapToAdDtoWithImageUrl)
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    @Transactional
    public byte[] updateImage(Integer id, MultipartFile image, Authentication authentication) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found"));

        checkAccess(adEntity, authentication);

        if (image != null && !image.isEmpty()) {
            // Удаляем старое изображение
            if (adEntity.getImage() != null) {
                imageService.deleteImage(adEntity.getImage());
            }

            // Сохраняем новое
            String imageUrl = imageService.saveImage(image, "ads");
            adEntity.setImage(imageUrl);
            adRepository.save(adEntity);
        }

        return new byte[0];
    }

    private Ad mapToAdDtoWithImageUrl(AdEntity entity) {
        Ad dto = adMapper.mapToAdDto(entity);
        if (dto != null && entity.getImage() != null) {
            dto.setImage(entity.getImage());
        }
        return dto;
    }

    private ExtendedAd mapToExtendedAdDtoWithImageUrl(AdEntity entity) {
        ExtendedAd dto = adMapper.mapToExtendedAdDto(entity);
        if (dto != null && entity.getImage() != null) {
            dto.setImage(entity.getImage());
        }
        return dto;
    }

    private void checkAccess(AdEntity adEntity, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !adEntity.getAuthor().getEmail().equals(authentication.getName())) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
