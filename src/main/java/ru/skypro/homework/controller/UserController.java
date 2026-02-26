package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "API для работы с пользователями")
public class UserController {
    @Operation(summary = "Обновление пароля")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword) {
        // TODO: Implement password update logic
        log.info("Received request to set new password");
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<User> getUser() {
        // TODO: Implement get current user logic
        log.info("Received request to get current user");
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhone("+7 (123) 456-78-90");
        user.setRole(Role.USER);
        user.setImage("/users/me/image");
        return ResponseEntity.ok(user);
    }
    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateUser.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser) {
        // TODO: Implement update user logic
        log.info("Received request to update user");
        return ResponseEntity.ok(updateUser);
    }
    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping(value = "/me/image", consumes = "multipart/form-data")
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image) {
        // TODO: Implement update user image logic
        log.info("Received request to update user image");
        return ResponseEntity.ok().build();
    }
}
