package com.fatihalkan.twitter_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TwitterErrorResponse {
    private String message;
    private int status;
    private long timestamp;
    private LocalDateTime localDateTime;
}
