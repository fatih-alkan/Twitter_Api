package com.fatihalkan.twitter_api.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long userId,
        Long tweetId,
        String text,
        String userName,
        String firstName,
        String lastName,
        LocalDateTime createdAt
) {
}
