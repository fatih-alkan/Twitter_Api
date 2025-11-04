package com.fatihalkan.twitter_api.dto.user;

public record UserResponseDto(
        String userName,
        String firstName,
        String lastName,
        String email
) {
}
