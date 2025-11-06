package com.fatihalkan.twitter_api.mapper;

import com.fatihalkan.twitter_api.dto.like.LikeRequestDto;
import com.fatihalkan.twitter_api.dto.like.LikeResponseDto;
import com.fatihalkan.twitter_api.entity.Like;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.TweetNotFoundException;
import com.fatihalkan.twitter_api.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeMapper {
    private final TweetRepository tweetRepository;
    public Like toEntity(LikeRequestDto likeRequestDto, User user) {
        Tweet tweet = tweetRepository.
                findById(likeRequestDto.tweetId()).
                orElseThrow(()-> new TweetNotFoundException("Tweet not found id :" + likeRequestDto.tweetId()));
        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        return like;
    }

    public LikeResponseDto toResponse(Like like) {
        return new LikeResponseDto(
                like.getId(),
                like.getUser().getId(),
                like.getUser().getUsername(),
                like.getTweet().getId(),
                like.getTweet().getContent()
        );
    }
}
