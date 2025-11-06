package com.fatihalkan.twitter_api.controller;

import com.fatihalkan.twitter_api.dto.tweet.TweetRequestDto;
import com.fatihalkan.twitter_api.dto.tweet.TweetResponseDto;
import com.fatihalkan.twitter_api.service.TweetService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweet")
public class TweetController {
    @Autowired
    private final TweetService service;

    @GetMapping
    public  List<TweetResponseDto> getAll(){
        return service.getAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto create(@AuthenticationPrincipal UserDetails userDetails,
                                   @Validated @RequestBody TweetRequestDto tweetRequestDto) {
        return service.create(userDetails, tweetRequestDto);
    }

    @GetMapping("/findByUserId/{userId}")
    public List<TweetResponseDto> getByUserId(@Positive @Min(1) @PathVariable("userId") Long userId) {
        return service.getByUserId(userId);
    }

    @GetMapping("/findById/{id}")
    public TweetResponseDto getById(@Positive @Min(1) @PathVariable("id") Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public TweetResponseDto update(@Positive @Min(1) @PathVariable("id") Long id,
                                   @Validated @RequestBody TweetRequestDto tweetRequestDto,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        return service.update(id, tweetRequestDto, userDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @Min(1) @PathVariable("id") Long id,
                       @AuthenticationPrincipal UserDetails userDetails) {
        service.delete(id, userDetails);
    }
}

