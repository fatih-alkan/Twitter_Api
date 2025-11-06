package com.fatihalkan.twitter_api.dto.retweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public record RetweetResponseDto(
        Long id,
        Long userId,
        Long originalUserId,
        String userName,
        String firstName,
        String lastName,
        Long tweetId,
        String tweetText,
        LocalDateTime createdAt
) {
}
