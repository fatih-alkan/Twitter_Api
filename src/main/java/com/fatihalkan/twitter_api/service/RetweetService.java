package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.retweet.RetweetRequestDto;
import com.fatihalkan.twitter_api.dto.retweet.RetweetResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface RetweetService {
    List<RetweetResponseDto> getAll();
    List<RetweetResponseDto> getByTweetId(Long tweetId);
    RetweetResponseDto create(UserDetails userDetails, RetweetRequestDto retweetRequestDto);
    void delete(Long id, UserDetails userDetails);
    long countRetweets(Long tweetId);
    boolean isRetweetsByUser(UserDetails userDetails, Long tweetId);
    List<RetweetResponseDto> findByUserId(Long id);
}
