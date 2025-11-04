package com.fatihalkan.twitter_api.dto.retweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

public record RetweetRequestDto(
        @JsonProperty("tweet_id")
        @NotNull
        Long tweetId
) {
}
