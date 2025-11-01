package com.fatihalkan.twitter_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record UserResponseDto(
        String userName,
        String firstName,
        String lastName,
        String email
) {
}
