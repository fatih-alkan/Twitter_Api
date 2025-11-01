package com.fatihalkan.twitter_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.ToString;

public record UserRequestDto(
        @JsonProperty("user_name")
        @Size(max = 50)
        @NotNull
        @NotEmpty
        @NotBlank
        String userName,

        @JsonProperty("first_name")
        @Size(max = 100)
        @NotNull
        @NotEmpty
        @NotBlank
        String firstName,

        @JsonProperty("last_name")
        @Size(max = 50)
        @NotNull
        @NotEmpty
        @NotBlank
        String lastName,

        @JsonProperty("email")
        @Size(max = 100)
        @Email
        @NotNull
        @NotEmpty
        @NotBlank
        String email,

        @JsonProperty("phone_number")
        @Size(max = 30)
        String phoneNumber,

        @JsonProperty("password")
        @NotNull
        @NotEmpty
        @NotBlank
        String password
) {
}
