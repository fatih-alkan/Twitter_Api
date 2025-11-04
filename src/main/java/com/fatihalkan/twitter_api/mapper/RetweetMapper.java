package com.fatihalkan.twitter_api.mapper;

import com.fatihalkan.twitter_api.dto.retweet.RetweetRequestDto;
import com.fatihalkan.twitter_api.dto.retweet.RetweetResponseDto;
import com.fatihalkan.twitter_api.entity.Retweet;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.TweetNotFoundException;
import com.fatihalkan.twitter_api.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetweetMapper {

    @Autowired
    private final TweetRepository tweetRepository;

    public Retweet toEntity(RetweetRequestDto retweetRequestDto, User user){
        Tweet tweet = tweetRepository.findById(retweetRequestDto.tweetId()).
                orElseThrow(()-> new TweetNotFoundException("Tweet not found id: "+ retweetRequestDto.tweetId()));
        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        return retweet;
    }

    public RetweetResponseDto toResponse(Retweet retweet){
        return new RetweetResponseDto(
                retweet.getUser().getId(),
                retweet.getUser().getUserName(),
                retweet.getTweet().getId(),
                retweet.getTweet().getContent(),
                retweet.getCreatedAt());
    }
}
