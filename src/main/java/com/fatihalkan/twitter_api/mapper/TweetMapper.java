package com.fatihalkan.twitter_api.mapper;

import com.fatihalkan.twitter_api.dto.tweet.TweetRequestDto;
import com.fatihalkan.twitter_api.dto.tweet.TweetResponseDto;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetMapper {
    public Tweet toEntity(TweetRequestDto tweetRequestDto, User user){
        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequestDto.content());
        tweet.setUser(user);
        return tweet;
    }

    public TweetResponseDto toResponseDto(Tweet tweet){
        return new TweetResponseDto(
                tweet.getId(),
                tweet.getContent(),
                tweet.getUser().getId(),
                tweet.getUser().getUsername(),
                tweet.getUser().getFirstName(),
                tweet.getUser().getLastName(),
                tweet.getCreatedAt());
    }
}
