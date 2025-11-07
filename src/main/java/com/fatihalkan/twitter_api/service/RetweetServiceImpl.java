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
import org.springframework.security.access.AccessDeniedException;
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
    public List<RetweetResponseDto> getAll() {
        List<Retweet> retweets = repository.findAll();
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
            throw new AccessDeniedException("You can only delete your own retweets");
        }
        repository.deleteById(id);
    }

    @Override
    public long countRetweets(Long tweetId) {
        return repository.countByTweetId(tweetId);
    }

    @Override
    public boolean isRetweetsByUser(UserDetails userDetails, Long tweetId) {
        User user = userService.findByUsername(userDetails.getUsername());
        return repository.findByUserIdAndTweetId(user.getId(), tweetId).isPresent();
    }

    @Override
    public List<RetweetResponseDto> findByUserId(Long id){
        return repository.findByUserId(id).stream().map(mapper::toResponse).toList();
    }
}
