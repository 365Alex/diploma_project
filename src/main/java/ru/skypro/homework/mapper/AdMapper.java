package ru.skypro.homework.mapper;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
/**
 * Маппер для преобразования между сущностью объявления и DTO.
 */
@Component
public class AdMapper {
    /**
     * Преобразует сущность объявления в расширенное DTO ExtendedAd.
     *
     * @param entity сущность объявления
     * @return расширенное DTO объявления
     */
    public Ad mapToAdDto(AdEntity entity) {
        if (entity == null) {
            return null;
        }

        Ad dto = new Ad();
        dto.setAuthor(entity.getAuthor().getId());
        dto.setImage(entity.getImage());
        dto.setPk(entity.getPk());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }
    /**
     * Преобразует сущность объявления в расширенное DTO ExtendedAd.
     *
     * @param entity сущность объявления
     * @return расширенное DTO объявления
     */
    public ExtendedAd mapToExtendedAdDto(AdEntity entity) {
        if (entity == null || entity.getAuthor() == null) {
            return null;
        }

        ExtendedAd dto = new ExtendedAd();
        UserEntity author = entity.getAuthor();

        dto.setPk(entity.getPk());
        dto.setAuthorFirstName(author.getFirstName());
        dto.setAuthorLastName(author.getLastName());
        dto.setDescription(entity.getDescription());
        dto.setEmail(author.getEmail());
        dto.setImage(entity.getImage());
        dto.setPhone(author.getPhone());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    /**
     * Создаёт сущность объявления из DTO CreateOrUpdateAd и автора.
     *
     * @param createOrUpdateAd DTO с данными для создания
     * @param author           автор объявления
     * @return созданная сущность
     */
    public AdEntity mapToEntity(CreateOrUpdateAd createOrUpdateAd, UserEntity author) {
        if (createOrUpdateAd == null || author == null) {
            return null;
        }

        AdEntity entity = new AdEntity();
        entity.setAuthor(author);
        updateEntity(createOrUpdateAd, entity);
        return entity;
    }
    /**
     * Обновляет существующую сущность объявления данными из DTO.
     *
     * @param createOrUpdateAd DTO с обновляемыми данными
     * @param entity           сущность для обновления
     */
    public void updateEntity(CreateOrUpdateAd createOrUpdateAd, AdEntity entity) {
        if (createOrUpdateAd == null || entity == null) {
            return;
        }

        if (createOrUpdateAd.getTitle() != null) {
            entity.setTitle(createOrUpdateAd.getTitle());
        }
        if (createOrUpdateAd.getPrice() != null) {
            entity.setPrice(createOrUpdateAd.getPrice());
        }
        if (createOrUpdateAd.getDescription() != null) {
            entity.setDescription(createOrUpdateAd.getDescription());
        }
    }
}
