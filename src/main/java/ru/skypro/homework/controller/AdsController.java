package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

import java.util.ArrayList;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        // TODO: Implement get all ads logic
        log.info("Received request to get all ads");
        Ads ads = new Ads();
        ads.setCount(0);
        ads.setResults(new ArrayList<>());
        return ResponseEntity.ok(ads);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Ad> addAd(
            @RequestPart("properties") CreateOrUpdateAd properties,
            @RequestPart("image") MultipartFile image) {
        // TODO: Implement add ad logic
        log.info("Received request to add ad");
        Ad ad = new Ad();
        ad.setPk(1);
        ad.setAuthor(1);
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setImage("/ads/1/image");
        return ResponseEntity.status(HttpStatus.CREATED).body(ad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable Integer id) {
        // TODO: Implement get ad by id logic
        log.info("Received request to get ad with id: {}", id);
        ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(id);
        extendedAd.setAuthorFirstName("John");
        extendedAd.setAuthorLastName("Doe");
        extendedAd.setDescription("Sample description");
        extendedAd.setEmail("user@example.com");
        extendedAd.setImage("/ads/" + id + "/image");
        extendedAd.setPhone("+7 (123) 456-78-90");
        extendedAd.setPrice(1000);
        extendedAd.setTitle("Sample Ad");
        return ResponseEntity.ok(extendedAd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Integer id) {
        // TODO: Implement remove ad logic
        log.info("Received request to remove ad with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ad> updateAds(@PathVariable Integer id,
                                        @RequestBody CreateOrUpdateAd ad) {
        // TODO: Implement update ad logic
        log.info("Received request to update ad with id: {}", id);
        Ad updatedAd = new Ad();
        updatedAd.setPk(id);
        updatedAd.setAuthor(1);
        updatedAd.setTitle(ad.getTitle());
        updatedAd.setPrice(ad.getPrice());
        updatedAd.setImage("/ads/" + id + "/image");
        return ResponseEntity.ok(updatedAd);
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getAdsMe() {
        // TODO: Implement get current user ads logic
        log.info("Received request to get current user ads");
        Ads ads = new Ads();
        ads.setCount(0);
        ads.setResults(new ArrayList<>());
        return ResponseEntity.ok(ads);
    }

    @PatchMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam("image") MultipartFile image) {
        // TODO: Implement update ad image logic
        log.info("Received request to update ad image with id: {}", id);
        return ResponseEntity.ok(new byte[0]);
    }
}
