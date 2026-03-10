package ru.skypro.homework.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comments getComments(Integer adId) {
        AdEntity ad = adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found"));

        List<CommentEntity> comments = commentRepository.findByAd(ad);
        Comments result = new Comments();
        result.setCount(comments.size());
        result.setResults(comments.stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    @Transactional
    public Comment addComment(Integer adId, CreateOrUpdateComment comment, Authentication authentication) {
        AdEntity ad = adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found"));

        UserEntity author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommentEntity commentEntity = commentMapper.mapToEntity(comment, author, ad);
        CommentEntity savedComment = commentRepository.save(commentEntity);

        return commentMapper.mapToDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Integer adId, Integer commentId, Authentication authentication) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getAd().getPk().equals(adId)) {
            throw new RuntimeException("Comment does not belong to ad");
        }

        checkAccess(comment, authentication);

        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Integer adId, Integer commentId,
                                 CreateOrUpdateComment comment, Authentication authentication) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!commentEntity.getAd().getPk().equals(adId)) {
            throw new RuntimeException("Comment does not belong to ad");
        }

        checkAccess(commentEntity, authentication);

        commentMapper.updateEntity(comment, commentEntity);
        CommentEntity updatedComment = commentRepository.save(commentEntity);

        return commentMapper.mapToDto(updatedComment);
    }

    private void checkAccess(CommentEntity commentEntity, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !commentEntity.getAuthor().getEmail().equals(authentication.getName())) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
