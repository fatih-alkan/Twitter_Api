package com.fatihalkan.twitter_api.dto.like;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record LikeRequestDto(
        @NotNull
        @JsonProperty("tweet_id")
        Long tweetId
) {
}
