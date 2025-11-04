package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.comment.CommentRequestDto;
import com.fatihalkan.twitter_api.dto.comment.CommentResponseDto;
import com.fatihalkan.twitter_api.entity.Comment;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.CommentNotFoundException;
import com.fatihalkan.twitter_api.exception.TweetNotFoundException;
import com.fatihalkan.twitter_api.mapper.CommentMapper;
import com.fatihalkan.twitter_api.repository.CommentRepository;
import com.fatihalkan.twitter_api.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.ref.PhantomReference;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository repository;
    @Autowired
    private CommentMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public List<CommentResponseDto> getAll(UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Comment> comments = repository.findByUserId(user.getId());
        return comments.stream().map(mapper::toResponse).toList();

    }

    @Override
    public CommentResponseDto getById(Long id) {
        Optional<Comment> optionalComment = repository.findById(id);
        if(optionalComment.isPresent()){
            return mapper.toResponse(optionalComment.get());
        }
        throw new CommentNotFoundException("Comment Not Found id: " + id);
    }

    @Override
    public List<CommentResponseDto> getByTweetId(Long tweetId) {
        List<Comment> comments = repository.findByTweetId(tweetId);
        if (comments.isEmpty()){
            throw  new TweetNotFoundException("No comments found for tweet id: "+tweetId);
        }
        return comments.stream().map(mapper::toResponse).toList();
    }

    @Override
    public CommentResponseDto create(UserDetails userDetails, CommentRequestDto commentRequestDto) {
        User user = userService.findByUsername(userDetails.getUsername());
        Comment comment = mapper.toEntity(commentRequestDto,user);
        return mapper.toResponse(repository.save(comment));
    }

    @Override
    public CommentResponseDto update(Long id, CommentRequestDto commentRequestDto, UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        Comment comment = repository.findById(id).
                orElseThrow(()-> new CommentNotFoundException("Comment Not Found id: " + id));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this comment");
        }

        comment.setText(commentRequestDto.text());
        return mapper.toResponse(repository.save(comment));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
