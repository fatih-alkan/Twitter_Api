package com.fatihalkan.twitter_api.dto.like;

public record LikeResponseDto(
        Long userId,
        String username,
        Long tweetId,
        String tweetContent
) {
}
