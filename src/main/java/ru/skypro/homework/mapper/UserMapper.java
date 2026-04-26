package ru.skypro.homework.mapper;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

@Component
public class UserMapper {
    /**
     * Преобразует сущность пользователя в DTO User.
     *
     * @param entity сущность пользователя
     * @return DTO пользователя
     */
    public User toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User dto = new User();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setImage(entity.getImage());
        return dto;
    }
    /**
     * Создаёт сущность пользователя из DTO регистрации.
     *
     * @param register DTO регистрации
     * @return созданная сущность пользователя
     */
    public UserEntity toEntity(Register register) {
        if (register == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setEmail(register.getUsername());
        entity.setFirstName(register.getFirstName());
        entity.setLastName(register.getLastName());
        entity.setPhone(register.getPhone());
        entity.setRole(register.getRole());
        return entity;
    }
    /**
     * Обновляет существующую сущность пользователя данными из DTO UpdateUser.
     *
     * @param updateUser DTO с обновляемыми данными
     * @param entity     сущность для обновления
     */
    public void updateEntity(UpdateUser updateUser, UserEntity entity) {
        if (updateUser == null || entity == null) {
            return;
        }

        if (updateUser.getFirstName() != null) {
            entity.setFirstName(updateUser.getFirstName());
        }
        if (updateUser.getLastName() != null) {
            entity.setLastName(updateUser.getLastName());
        }
        if (updateUser.getPhone() != null) {
            entity.setPhone(updateUser.getPhone());
        }
    }
}
