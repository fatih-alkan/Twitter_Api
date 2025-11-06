package com.fatihalkan.twitter_api.dto.retweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record RetweetRequestDto(
        @JsonProperty("tweet_id")
        @NotNull
        Long tweetId
) {
}
