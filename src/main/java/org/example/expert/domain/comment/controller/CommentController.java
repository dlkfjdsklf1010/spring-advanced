package org.example.expert.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.example.expert.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentSaveResponse> saveComment(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable long todoId,
            @Valid @RequestBody CommentSaveRequest request
    ) {

        AuthUser authUser = new AuthUser(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getUserRole()
        );

        return ResponseEntity.ok(
                commentService.saveComment(
                        authUser,
                        todoId,
                        request
                )
        );
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable long todoId) {
        return ResponseEntity.ok(commentService.getComments(todoId));
    }
}
