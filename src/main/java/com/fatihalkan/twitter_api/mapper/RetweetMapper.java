package com.fatihalkan.twitter_api.mapper;

import com.fatihalkan.twitter_api.dto.retweet.RetweetRequestDto;
import com.fatihalkan.twitter_api.dto.retweet.RetweetResponseDto;
import com.fatihalkan.twitter_api.entity.Retweet;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.TweetNotFoundException;
import com.fatihalkan.twitter_api.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetweetMapper {

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
                retweet.getId(),
                retweet.getUser().getId(),
                retweet.getTweet().getUser().getId(),
                retweet.getTweet().getUser().getUsername(),
                retweet.getUser().getFirstName(),
                retweet.getUser().getLastName(),
                retweet.getTweet().getId(),
                retweet.getTweet().getContent(),
                retweet.getCreatedAt());
    }
}
