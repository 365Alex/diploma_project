package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
/**
 * Контроллер для управления объявлениями.
 * Предоставляет эндпоинты для получения, создания, обновления и удаления объявлений.
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    private final AdService adService;
    private final ObjectMapper objectMapper;
    /**
     * Получает все объявления.
     *
     * @return список всех объявлений
     */
    @Operation(summary = "Получение всех объявлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ads.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }
    /**
     * Добавляет новое объявление.
     *
     * @param propertiesJson JSON-строка с данными объявления
     * @param image          файл изображения
     * @param authentication данные аутентификации текущего пользователя
     * @return созданное объявление
     */
    @Operation(summary = "Добавление объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ad.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAd(
            @RequestParam("properties") String propertiesJson,
            @RequestParam("image") MultipartFile image,
            Authentication authentication) {

        log.info("========== POST /ads ==========");
        log.info("Authentication: {}", authentication != null ? authentication.getName() : "null");
        log.info("Properties JSON: {}", propertiesJson);
        log.info("Image: name={}, size={}, contentType={}",
                image.getOriginalFilename(), image.getSize(), image.getContentType());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateOrUpdateAd properties = objectMapper.readValue(propertiesJson, CreateOrUpdateAd.class);

            Ad ad = adService.addAd(properties, image, authentication);
            return ResponseEntity.status(HttpStatus.CREATED).body(ad);

        } catch (IOException e) {
            log.error("Failed to parse properties JSON", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid properties JSON format: " + e.getMessage());
        }
    }
    /**
     * Получает информацию об объявлении по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return расширенная информация об объявлении
     */

    @Operation(summary = "Получение информации об объявлении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExtendedAd.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adService.getAd(id));
    }
    /**
     * Удаляет объявление.
     *
     * @param id             идентификатор объявления
     * @param authentication данные аутентификации
     * @return пустой ответ с кодом 204
     */
    @Operation(summary = "Удаление объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Integer id, Authentication authentication) {
        adService.removeAd(id, authentication);
        return ResponseEntity.noContent().build();
    }
    /**
     * Обновляет информацию об объявлении.
     *
     * @param id             идентификатор объявления
     * @param ad             новые данные объявления
     * @param authentication данные аутентификации
     * @return обновлённое объявление
     */
    @Operation(summary = "Обновление информации об объявлении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ad.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Ad> updateAds(@PathVariable Integer id,
                                        @RequestBody CreateOrUpdateAd ad,
                                        Authentication authentication) {
        return ResponseEntity.ok(adService.updateAd(id, ad, authentication));
    }
    /**
     * Получает объявления текущего авторизованного пользователя.
     *
     * @param authentication данные аутентификации
     * @return список объявлений пользователя
     */
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ads.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<Ads> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getAdsMe(authentication));
    }
    /**
     * Обновляет изображение объявления.
     *
     * @param id             идентификатор объявления
     * @param image          новый файл изображения
     * @param authentication данные аутентификации
     * @return массив байтов нового изображения (может быть пустым)
     */
    @Operation(summary = "Обновление картинки объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            schema = @Schema(type = "string", format = "byte"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam("image") MultipartFile image,
                                              Authentication authentication) {
        return ResponseEntity.ok(adService.updateImage(id, image, authentication));
    }
}