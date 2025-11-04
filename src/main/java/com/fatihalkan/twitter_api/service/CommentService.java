package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.comment.CommentRequestDto;
import com.fatihalkan.twitter_api.dto.comment.CommentResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> getAll(UserDetails userDetails);
    CommentResponseDto getById(Long id);
    List<CommentResponseDto> getByTweetId(Long tweetId);
    CommentResponseDto create(UserDetails userDetails, CommentRequestDto commentRequestDto);
    CommentResponseDto update(Long id, CommentRequestDto commentRequestDto, UserDetails userDetails);
    void delete(Long id);
}
