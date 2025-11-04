package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.retweet.RetweetRequestDto;
import com.fatihalkan.twitter_api.dto.retweet.RetweetResponseDto;
import com.fatihalkan.twitter_api.entity.Retweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.RetweetNotFoundException;
import com.fatihalkan.twitter_api.mapper.RetweetMapper;
import com.fatihalkan.twitter_api.repository.RetweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService{

    @Autowired
    private RetweetRepository repository;
    @Autowired
    private RetweetMapper mapper;
    @Autowired
    private UserService userService;


    @Override
    public List<RetweetResponseDto> getAll(UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Retweet> retweets = repository.findByUserId(user.getId());
        return retweets.stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<RetweetResponseDto> getByTweetId(Long tweetId) {
        List<Retweet> retweets = repository.findByTweetId(tweetId);
        return retweets.stream().map(mapper::toResponse).toList();
    }

    @Override
    public RetweetResponseDto create(UserDetails userDetails, RetweetRequestDto retweetRequestDto) {
        User user = userService.findByUsername(userDetails.getUsername());
        Retweet retweet = mapper.toEntity(retweetRequestDto,user);
        return mapper.toResponse(repository.save(retweet));
    }

    @Override
    public void delete(Long id, UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        Retweet retweet = repository.findById(id).
                orElseThrow(()-> new RetweetNotFoundException("Retweet not found id: " + id));

        if (!retweet.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You can only delete your own retweets");
        }
        repository.deleteById(id);
    }
}
