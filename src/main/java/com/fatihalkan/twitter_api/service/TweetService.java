package com.fatihalkan.twitter_api.service;



import com.fatihalkan.twitter_api.dto.tweet.TweetRequestDto;
import com.fatihalkan.twitter_api.dto.tweet.TweetResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface TweetService {
    //GET
    List<TweetResponseDto> getAll(UserDetails userDetails);
    TweetResponseDto getById(Long id);
    //POST
    TweetResponseDto create(UserDetails userDetails,TweetRequestDto tweetRequestDto);
    //PATCH
    TweetResponseDto update(Long id, TweetRequestDto tweetRequestDto, UserDetails userDetails);
    //DELETE
    void delete(Long id, UserDetails userDetails);

    List<TweetResponseDto> getByUserId(Long id);

}
