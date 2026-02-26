package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

import java.util.ArrayList;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class CommentController {
    @Operation(summary = "Получение комментариев объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Comments.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping("/ads/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable Integer id) {
        // TODO: Implement get comments logic
        log.info("Received request to get comments for ad with id: {}", id);
        Comments comments = new Comments();
        comments.setCount(0);
        comments.setResults(new ArrayList<>());
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Добавление комментария к объявлению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping("/ads/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Integer id,
                                              @RequestBody CreateOrUpdateComment comment) {
        // TODO: Implement add comment logic
        log.info("Received request to add comment for ad with id: {}", id);
        Comment newComment = new Comment();
        newComment.setPk(1);
        newComment.setAuthor(1);
        newComment.setText(comment.getText());
        newComment.setCreatedAt(System.currentTimeMillis());
        newComment.setAuthorFirstName("John");
        newComment.setAuthorImage("/users/me/image");
        return ResponseEntity.ok(newComment);
    }
    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId,
                                           @PathVariable Integer commentId) {
        // TODO: Implement delete comment logic
        log.info("Received request to delete comment with id: {} for ad with id: {}", commentId, adId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer adId,
                                                 @PathVariable Integer commentId,
                                                 @RequestBody CreateOrUpdateComment comment) {
        // TODO: Implement update comment logic
        log.info("Received request to update comment with id: {} for ad with id: {}", commentId, adId);
        Comment updatedComment = new Comment();
        updatedComment.setPk(commentId);
        updatedComment.setAuthor(1);
        updatedComment.setText(comment.getText());
        updatedComment.setCreatedAt(System.currentTimeMillis());
        updatedComment.setAuthorFirstName("John");
        updatedComment.setAuthorImage("/users/me/image");
        return ResponseEntity.ok(updatedComment);
    }
}
