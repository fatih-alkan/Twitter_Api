package com.fatihalkan.twitter_api.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CommentRequestDto(
        @NotBlank
        @NotEmpty
        @NotNull
        @JsonProperty("text")
        String text,
        @NotNull
        @JsonProperty("tweet_id")
        Long tweetId
) {
}
