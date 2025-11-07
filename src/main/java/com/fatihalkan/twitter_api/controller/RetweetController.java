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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/retweet")
public class RetweetController {

    @Autowired
    private RetweetService service;

    @GetMapping
    public List<RetweetResponseDto> getAll(){
        return service.getAll();
    }
    @GetMapping("/user/{id}")
    public List<RetweetResponseDto> getByUserId(@Positive @Min(1) @PathVariable("id") Long id){
        return service.findByUserId(id);
    }

    @GetMapping("/tweet/{tweetId}")
    public List<RetweetResponseDto> getByTweetId(@Positive @Min(1) @PathVariable("id") Long tweetId) {
        return service.getByTweetId(tweetId);
    }

    @PostMapping
    public RetweetResponseDto create(@AuthenticationPrincipal UserDetails userDetails,
            @Validated @RequestBody RetweetRequestDto retweetRequestDto){
        return service.create(userDetails, retweetRequestDto);
    }
    @GetMapping("/count/{tweetId}")
    public Map<String, Object> getRetweetCountAndStatus(@AuthenticationPrincipal UserDetails userDetails,
                                                     @PathVariable Long tweetId) {
        boolean retweeted = service.isRetweetsByUser(userDetails, tweetId);
        long count = service.countRetweets(tweetId);
        return Map.of("retweeted", retweeted, "count", count);
    }
    @DeleteMapping("/{id}")
    public void delete(@Positive @Min(1) @PathVariable("id") Long id,
                       @AuthenticationPrincipal UserDetails userDetails){
        service.delete(id, userDetails);
    }
}
