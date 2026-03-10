package ru.skypro.homework.service;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
public interface AdService {
    Ads getAllAds();
    Ad addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication);
    ExtendedAd getAd(Integer id);
    void removeAd(Integer id, Authentication authentication);
    Ad updateAd(Integer id, CreateOrUpdateAd ad, Authentication authentication);
    Ads getAdsMe(Authentication authentication);
    byte[] updateImage(Integer id, MultipartFile image, Authentication authentication);
}
