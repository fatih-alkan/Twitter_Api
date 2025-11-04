package com.fatihalkan.twitter_api.dto.retweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public record RetweetResponseDto(
        Long userId,
        String userName,
        Long tweetId,
        String tweetText,
        LocalDateTime createdAt
) {
}
