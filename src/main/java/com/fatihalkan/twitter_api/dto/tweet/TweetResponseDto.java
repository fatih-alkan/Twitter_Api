package com.fatihalkan.twitter_api.dto.tweet;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record TweetResponseDto(
        String content,
        Long userId,
        String userName,
        OffsetDateTime createdAt
) {
}
