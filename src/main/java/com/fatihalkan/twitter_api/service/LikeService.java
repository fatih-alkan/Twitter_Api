package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.like.LikeRequestDto;
import com.fatihalkan.twitter_api.dto.like.LikeResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface LikeService {
    List<LikeResponseDto> getAll(UserDetails userDetails);
    LikeResponseDto getById(Long likeId);
    LikeResponseDto create(UserDetails userDetails,LikeRequestDto likeRequestDto);
    void delete(UserDetails userDetails, Long tweetId);
}
