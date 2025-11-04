package com.fatihalkan.twitter_api.controller;

import com.fatihalkan.twitter_api.dto.comment.CommentRequestDto;
import com.fatihalkan.twitter_api.dto.comment.CommentResponseDto;
import com.fatihalkan.twitter_api.service.CommentService;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService service;

    @GetMapping
    public List<CommentResponseDto> getAll(@AuthenticationPrincipal UserDetails userDetails){
        return service.getAll(userDetails);
    }

    @GetMapping("/{id}")
    public CommentResponseDto getById(@Positive @Min(1) @PathVariable("id") Long id){
        return service.getById(id);
    }

    @GetMapping("/tweet/{id}")
    public List<CommentResponseDto> getByTweetId(@Positive @Min(1) @PathVariable("id") Long tweetId){
        return service.getByTweetId(tweetId);
    }
    @PostMapping
    public CommentResponseDto create(@AuthenticationPrincipal UserDetails userDetails,
                                     @Validated @RequestBody CommentRequestDto commentRequestDto){
        return service.create(userDetails,commentRequestDto);
    }
    @PatchMapping("/{id}")
    public CommentResponseDto update(@Positive @Min(1) @PathVariable("id") Long id,
                                     @Validated @RequestBody CommentRequestDto commentRequestDto,
                                     @AuthenticationPrincipal UserDetails userDetails){
        return service.update(id, commentRequestDto, userDetails);
    }
}
