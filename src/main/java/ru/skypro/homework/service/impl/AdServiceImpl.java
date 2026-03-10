package ru.skypro.homework.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;

    private static final String IMAGES_DIR = "images/ads/";

    @Override
    public Ads getAllAds() {
        List<AdEntity> ads = adRepository.findAll();
        Ads result = new Ads();
        result.setCount(ads.size());
        result.setResults(ads.stream()
                .map(adMapper::mapToAdDto)
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    @Transactional
    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication) {
        UserEntity author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AdEntity adEntity = adMapper.mapToEntity(properties, author);

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            adEntity.setImage(imagePath);
        }

        AdEntity savedAd = adRepository.save(adEntity);
        return adMapper.mapToAdDto(savedAd);
    }

    @Override
    public ExtendedAd getAd(Integer id) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
        return adMapper.mapToExtendedAdDto(adEntity);
    }

    @Override
    @Transactional
    public void removeAd(Integer id, Authentication authentication) {
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found"));

        checkAccess(adEntity, authentication);

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
        return adMapper.mapToAdDto(updatedAd);
    }

    @Override
    public Ads getAdsMe(Authentication authentication) {
        UserEntity user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<AdEntity> ads = adRepository.findByAuthor(user);
        Ads result = new Ads();
        result.setCount(ads.size());
        result.setResults(ads.stream()
                .map(adMapper::mapToAdDto)
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
            String imagePath = saveImage(image);
            adEntity.setImage(imagePath);
            adRepository.save(adEntity);
        }

        return new byte[0];
    }

    private void checkAccess(AdEntity adEntity, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !adEntity.getAuthor().getEmail().equals(authentication.getName())) {
            throw new AccessDeniedException("Access denied");
        }
    }

    private String saveImage(MultipartFile image) {
        try {
            Path uploadPath = Paths.get(IMAGES_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(image.getInputStream(), filePath);

            return "/ads/images/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
