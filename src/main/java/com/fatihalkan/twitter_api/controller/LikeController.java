package com.fatihalkan.twitter_api.controller;

import com.fatihalkan.twitter_api.dto.like.LikeRequestDto;
import com.fatihalkan.twitter_api.dto.like.LikeResponseDto;
import com.fatihalkan.twitter_api.service.LikeService;
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
@RequestMapping("likes")
@RequiredArgsConstructor
public class LikeController {
    @Autowired
    private final LikeService service;

    @GetMapping
    public List<LikeResponseDto> getAll(@AuthenticationPrincipal UserDetails userDetails){
        return service.getAll(userDetails);
    }

    @GetMapping("/{id}")
    public LikeResponseDto getById(@Positive @Min(1) @PathVariable("id") Long likeId){
        return service.getById(likeId);
    }

    @PostMapping
    public LikeResponseDto create(@AuthenticationPrincipal UserDetails userDetails,
                                  @Validated @RequestBody LikeRequestDto likeRequestDto){
        return service.create(userDetails,likeRequestDto);
    }

    @DeleteMapping("/tweet/{tweetId}")
    public void delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable @Positive Long tweetId){
        service.delete(userDetails, tweetId);
    }
}
