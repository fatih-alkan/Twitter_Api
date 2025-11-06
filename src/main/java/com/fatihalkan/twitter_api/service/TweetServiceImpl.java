package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.tweet.TweetRequestDto;
import com.fatihalkan.twitter_api.dto.tweet.TweetResponseDto;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.TweetNotFoundException;
import com.fatihalkan.twitter_api.mapper.TweetMapper;
import com.fatihalkan.twitter_api.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{

    @Autowired
    private final TweetRepository repository;
    @Autowired
    private final TweetMapper mapper;
    @Autowired
    private final UserService userService;


    @Override
    public List<TweetResponseDto> getAll() {
        List<Tweet> tweets = repository.findAll();
        return tweets.stream().map(mapper::toResponseDto).toList();
    }

    @Override
    public TweetResponseDto getById(Long id) {
        Tweet tweet = repository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException("Tweet not found id : " + id));
        return mapper.toResponseDto(tweet);
    }

    @Override
    public TweetResponseDto create(UserDetails userDetails, TweetRequestDto tweetRequestDto) {
        User user = userService.findByUsername(userDetails.getUsername());
        Tweet tweet = mapper.toEntity(tweetRequestDto, user);
        return mapper.toResponseDto(repository.save(tweet));
    }


    @Override
    public TweetResponseDto update(Long id, TweetRequestDto tweetRequestDto, UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        Tweet tweetToUpdate = repository.
                findById(id).
                orElseThrow(()-> new TweetNotFoundException("Tweet not found id : " + id));
        if (tweetToUpdate.getUser().getId().equals(user.getId())){
            tweetToUpdate.setContent(tweetRequestDto.content());
            repository.save(tweetToUpdate);
        } else {
            throw new AccessDeniedException("Forbidden: You are not the owner of this tweet");
        }
        return mapper.toResponseDto(tweetToUpdate);
    }

    @Override
    public void delete(Long id,UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        Tweet tweet = repository.findById(id).
                orElseThrow(()-> new TweetNotFoundException("Tweet not found  id: " + id));
        if(!tweet.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("Forbidden: You are not the owner of this tweet");
        }
        repository.deleteById(id);
    }

    @Override
    public List<TweetResponseDto> getByUserId(Long id) {
        return repository.findByUserId(id).stream().map(mapper::toResponseDto).toList();
    }

}
