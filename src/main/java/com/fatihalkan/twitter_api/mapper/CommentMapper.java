package com.fatihalkan.twitter_api.mapper;

import com.fatihalkan.twitter_api.dto.comment.CommentRequestDto;
import com.fatihalkan.twitter_api.dto.comment.CommentResponseDto;
import com.fatihalkan.twitter_api.entity.Comment;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.TweetNotFoundException;
import com.fatihalkan.twitter_api.exception.UserNotFoundException;
import com.fatihalkan.twitter_api.repository.TweetRepository;
import com.fatihalkan.twitter_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TweetRepository tweetRepository;

    public Comment toEntity(CommentRequestDto commentRequestDto,User user){
        Tweet tweet = tweetRepository.findById(commentRequestDto.tweetId())
            .orElseThrow(() -> new TweetNotFoundException("Tweet Not Found id : " + commentRequestDto.tweetId()));

        Comment comment = new Comment();
        comment.setText(commentRequestDto.text());
        comment.setTweet(tweet);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        return comment;
    }

    public CommentResponseDto toResponse(Comment comment){
        return new CommentResponseDto(comment.getUser().getId(), comment.getTweet().getId(), comment.getText());
    }
}
