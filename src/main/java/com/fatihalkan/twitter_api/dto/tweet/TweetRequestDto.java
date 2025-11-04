package com.fatihalkan.twitter_api.dto.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record TweetRequestDto(
        @NotBlank
        @NotEmpty
        @NotNull
        @JsonProperty("content")
        String content
) {
}
