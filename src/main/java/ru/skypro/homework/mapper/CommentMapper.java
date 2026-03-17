package ru.skypro.homework.mapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

@Component
public class CommentMapper {

    @Value("${image.base.url}")
    private String baseUrl;

    public Comment mapToDto(CommentEntity entity) {
        if (entity == null || entity.getAuthor() == null) {
            return null;
        }

        Comment dto = new Comment();
        UserEntity author = entity.getAuthor();

        dto.setAuthor(author.getId());

        // Устанавливаем полный URL для аватара автора комментария
        if (author.getImage() != null) {
            dto.setAuthorImage(author.getImage());
        } else {
            dto.setAuthorImage(null);
        }

        dto.setAuthorFirstName(author.getFirstName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setPk(entity.getPk());
        dto.setText(entity.getText());
        return dto;
    }

    public CommentEntity mapToEntity(CreateOrUpdateComment createOrUpdateComment,
                                     UserEntity author,
                                     AdEntity ad) {
        if (createOrUpdateComment == null || author == null || ad == null) {
            return null;
        }

        CommentEntity entity = new CommentEntity();
        entity.setAuthor(author);
        entity.setAd(ad);
        entity.setText(createOrUpdateComment.getText());
        return entity;
    }

    public void updateEntity(CreateOrUpdateComment createOrUpdateComment, CommentEntity entity) {
        if (createOrUpdateComment == null || entity == null) {
            return;
        }

        if (createOrUpdateComment.getText() != null) {
            entity.setText(createOrUpdateComment.getText());
        }
    }
}
