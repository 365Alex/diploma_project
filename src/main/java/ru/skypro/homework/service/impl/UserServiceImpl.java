package ru.skypro.homework.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Value("${image.base.url}")
    private String baseUrl;

    @Override
    @Transactional
    public void setPassword(NewPassword newPassword, Authentication authentication) {
        UserEntity user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUser(Authentication authentication) {
        UserEntity user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserDtoWithImageUrl(user);
    }

    @Override
    @Transactional
    public UpdateUser updateUser(UpdateUser updateUser, Authentication authentication) {
        UserEntity user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateEntity(updateUser, user);
        userRepository.save(user);

        return updateUser;
    }

    @Override
    @Transactional
    public void updateUserImage(MultipartFile image, Authentication authentication) {
        UserEntity user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (image != null && !image.isEmpty()) {
            // Удаляем старый аватар
            if (user.getImage() != null) {
                imageService.deleteImage(user.getImage());
            }

            // Сохраняем новый
            String imageUrl = imageService.saveImage(image, "avatars");
            user.setImage(imageUrl);
            userRepository.save(user);
        }
    }

    private User mapToUserDtoWithImageUrl(UserEntity entity) {
        User dto = userMapper.toDto(entity);
        if (dto != null && entity.getImage() != null) {
            dto.setImage(entity.getImage());
        }
        return dto;
    }
}
