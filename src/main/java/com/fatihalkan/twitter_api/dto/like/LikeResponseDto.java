package com.fatihalkan.twitter_api.dto.like;

public record LikeResponseDto(
        Long id,
        Long userId,
        String username,
        Long tweetId,
        String tweetContent
) {
}
