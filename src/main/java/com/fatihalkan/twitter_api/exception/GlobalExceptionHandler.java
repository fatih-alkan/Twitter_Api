package com.fatihalkan.twitter_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TwitterException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(TwitterException twitterException){

        TwitterErrorResponse twitterErrorResponse = new TwitterErrorResponse(
                twitterException.getMessage(),
                twitterException.getHttpStatus().value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(twitterErrorResponse, twitterException.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(IllegalArgumentException exception){

        TwitterErrorResponse twitterErrorResponse = new TwitterErrorResponse(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(twitterErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
