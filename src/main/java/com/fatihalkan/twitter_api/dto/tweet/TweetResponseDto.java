package com.fatihalkan.twitter_api.dto.tweet;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record TweetResponseDto(
        Long tweetId,
        String content,
        Long userId,
        String userName,
        String firstName,
        String lastName,
        OffsetDateTime createdAt
) {
}
