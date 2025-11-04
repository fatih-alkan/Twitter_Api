package com.fatihalkan.twitter_api.dto.comment;

public record CommentResponseDto(
        Long tweetId,
        Long userId,
        String text
) {
}
