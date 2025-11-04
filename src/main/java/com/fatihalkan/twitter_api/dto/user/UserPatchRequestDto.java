package com.fatihalkan.twitter_api.dto.user;

public record UserPatchRequestDto(
        String userName,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password
) {
}
