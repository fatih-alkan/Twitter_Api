package com.fatihalkan.twitter_api.controller;

import com.fatihalkan.twitter_api.dto.retweet.RetweetRequestDto;
import com.fatihalkan.twitter_api.dto.retweet.RetweetResponseDto;
import com.fatihalkan.twitter_api.service.RetweetService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/retweet")
public class RetweetController {

    @Autowired
    private RetweetService service;

    @GetMapping
    public List<RetweetResponseDto> getAll(@AuthenticationPrincipal UserDetails userDetails){
        return service.getAll(userDetails);
    }

    @GetMapping("/tweet/{tweetId}")
    public List<RetweetResponseDto> getByTweetId(@Positive @Min(1) @PathVariable Long tweetId) {
        return service.getByTweetId(tweetId);
    }

    @PostMapping
    public RetweetResponseDto create(@AuthenticationPrincipal UserDetails userDetails,
            @Validated @RequestBody RetweetRequestDto retweetRequestDto){
        return service.create(userDetails, retweetRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@Positive @Min(1) @PathVariable("id") Long id,
                       @AuthenticationPrincipal UserDetails userDetails){
        service.delete(id, userDetails);
    }
}
