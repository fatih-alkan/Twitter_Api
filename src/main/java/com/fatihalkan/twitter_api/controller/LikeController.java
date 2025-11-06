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
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LikeController {
    @Autowired
    private final LikeService service;

    @GetMapping("likes")
    public List<LikeResponseDto> getAll(@AuthenticationPrincipal UserDetails userDetails){
        return service.getAll(userDetails);
    }

    @GetMapping("/like/{id}")
    public LikeResponseDto getById(@Positive @Min(1) @PathVariable("id") Long likeId){
        return service.getById(likeId);
    }

    @PostMapping("/like")
    public LikeResponseDto create(@AuthenticationPrincipal UserDetails userDetails,
                                  @Validated @RequestBody LikeRequestDto likeRequestDto){
        return service.create(userDetails,likeRequestDto);
    }
    @GetMapping("/like/count/{tweetId}")
    public Map<String, Object> getLikeCountAndStatus(@AuthenticationPrincipal UserDetails userDetails,
                                                     @PathVariable Long tweetId) {
        boolean liked = service.isLikedByUser(userDetails, tweetId);
        long count = service.countLikes(tweetId);
        return Map.of("liked", liked, "count", count);
    }
    @PostMapping("/dislike")
    public void delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @Validated @RequestBody LikeRequestDto likeRequestDto){
        service.delete(userDetails, likeRequestDto.tweetId());
    }
}
