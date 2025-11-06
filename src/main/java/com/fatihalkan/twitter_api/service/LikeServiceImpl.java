package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.like.LikeRequestDto;
import com.fatihalkan.twitter_api.dto.like.LikeResponseDto;
import com.fatihalkan.twitter_api.entity.Like;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.LikeNotFoundException;
import com.fatihalkan.twitter_api.mapper.LikeMapper;
import com.fatihalkan.twitter_api.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final UserService userService;
    private final LikeRepository repository;
    private final LikeMapper mapper;
    @Override
    public List<LikeResponseDto> getAll(UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        List<Like> likes = repository.findByUserId(user.getId());
        return likes.stream().map(mapper::toResponse).toList();
    }

    @Override
    public LikeResponseDto getById(Long likeId) {
        Like like = repository.findById(likeId)
                .orElseThrow(() -> new LikeNotFoundException("Like not found id: " + likeId));
        return mapper.toResponse(like);
    }


    @Override
    public LikeResponseDto create(UserDetails userDetails, LikeRequestDto likeRequestDto) {
        User user = userService.findByUsername(userDetails.getUsername());

        if (repository.findByUserIdAndTweetId(user.getId(), likeRequestDto.tweetId()).isPresent()) {
            throw new RuntimeException("User already liked this tweet");
        }

        Like like = mapper.toEntity(likeRequestDto,user);
        Like saved = repository.save(like);
        return mapper.toResponse(saved);
    }

    @Override
    public boolean isLikedByUser(UserDetails userDetails, Long tweetId) {
        User user = userService.findByUsername(userDetails.getUsername());
        return repository.findByUserIdAndTweetId(user.getId(), tweetId).isPresent();
    }

    public long countLikes(Long tweetId) {
        return repository.countByTweetId(tweetId);
    }


    @Override
    public void delete(UserDetails userDetails, Long tweetId) {
        User user = userService.findByUsername(userDetails.getUsername());
        Like like = repository.findByUserIdAndTweetId(user.getId(), tweetId)
                .orElseThrow(() -> new LikeNotFoundException("Like not found for this user and tweet"));

        repository.delete(like);
    }

}
