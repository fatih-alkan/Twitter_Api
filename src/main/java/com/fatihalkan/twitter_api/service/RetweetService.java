package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.retweet.RetweetRequestDto;
import com.fatihalkan.twitter_api.dto.retweet.RetweetResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface RetweetService {
    List<RetweetResponseDto> getAll(UserDetails userDetails);
    List<RetweetResponseDto> getByTweetId(Long tweetId);
    RetweetResponseDto create(UserDetails userDetails, RetweetRequestDto retweetRequestDto);
    void delete(Long id, UserDetails userDetails);
}
