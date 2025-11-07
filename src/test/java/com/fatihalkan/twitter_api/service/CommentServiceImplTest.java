package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.comment.CommentRequestDto;
import com.fatihalkan.twitter_api.dto.comment.CommentResponseDto;
import com.fatihalkan.twitter_api.entity.Comment;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.CommentNotFoundException;
import com.fatihalkan.twitter_api.mapper.CommentMapper;
import com.fatihalkan.twitter_api.repository.CommentRepository;
import com.fatihalkan.twitter_api.repository.TweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private UserService userService;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserDetails userDetails;

    private User user;
    private Comment comment;
    private Tweet tweet;
    private CommentRequestDto commentRequestDto;
    private CommentResponseDto commentResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUserName("testuser");
        user.setFirstName("Test");
        user.setLastName("User");

        tweet = new Tweet();
        tweet.setId(10L);
        tweet.setUser(user);
        tweet.setContent("Hello world");

        comment = new Comment();
        comment.setId(100L);
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setText("Nice tweet!");
        comment.setCreatedAt(LocalDateTime.now());

        commentRequestDto = new CommentRequestDto("Updated comment",10L);

        commentResponseDto = new CommentResponseDto(
                comment.getId(),
                user.getId(),
                tweet.getId(),
                comment.getText(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                comment.getCreatedAt()
        );

        when(userDetails.getUsername()).thenReturn(user.getUsername());
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
    }

    @Test
    @DisplayName("Get all comments for a user")
    void testGetAllCommentsByUser() {
        when(commentRepository.findByUserId(user.getId())).thenReturn(List.of(comment));
        when(commentMapper.toResponse(comment)).thenReturn(commentResponseDto);

        List<CommentResponseDto> comments = commentService.getAll(userDetails);
        assertEquals(1, comments.size());
        assertEquals(commentResponseDto, comments.get(0));
    }

    @Test
    @DisplayName("Get comment by ID")
    void testGetCommentById() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentMapper.toResponse(comment)).thenReturn(commentResponseDto);

        CommentResponseDto response = commentService.getById(comment.getId());
        assertEquals(commentResponseDto, response);
    }

    @Test
    @DisplayName("Get comment by ID throws CommentNotFoundException")
    void testGetCommentById_NotFound() {
        when(commentRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> commentService.getById(999L));
    }

    @Test
    @DisplayName("Get all comments for a tweet")
    void testGetCommentsByTweetId() {
        when(commentRepository.findByTweetId(tweet.getId())).thenReturn(List.of(comment));
        when(commentMapper.toResponse(comment)).thenReturn(commentResponseDto);

        List<CommentResponseDto> comments = commentService.getByTweetId(tweet.getId());
        assertEquals(1, comments.size());
        assertEquals(commentResponseDto, comments.get(0));
    }

    @Test
    @DisplayName("Create a new comment")
    void testCreateComment() {
        when(commentMapper.toEntity(commentRequestDto, user)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toResponse(comment)).thenReturn(commentResponseDto);

        CommentResponseDto response = commentService.create(userDetails, commentRequestDto);
        assertEquals(commentResponseDto, response);
    }

    @Test
    @DisplayName("Update a comment successfully")
    void testUpdateComment_Success() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toResponse(comment)).thenReturn(commentResponseDto);

        CommentResponseDto response = commentService.update(comment.getId(), commentRequestDto, userDetails);
        assertEquals(commentResponseDto, response);
    }

    @Test
    @DisplayName("Update comment throws AccessDeniedException for another user")
    void testUpdateComment_AccessDenied() {
        User otherUser = new User();
        otherUser.setId(999L);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(userService.findByUsername(user.getUsername())).thenReturn(otherUser);

        assertThrows(AccessDeniedException.class,
                () -> commentService.update(comment.getId(), commentRequestDto, userDetails));
    }

    @Test
    @DisplayName("Delete a comment by owner")
    void testDeleteComment_Success() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        assertDoesNotThrow(() -> commentService.delete(comment.getId(), userDetails));
        verify(commentRepository, times(1)).deleteById(comment.getId());
    }

    @Test
    @DisplayName("Delete comment throws AccessDeniedException for unauthorized user")
    void testDeleteComment_AccessDenied() {
        User otherUser = new User();
        otherUser.setId(999L);
        when(userService.findByUsername(user.getUsername())).thenReturn(otherUser);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        assertThrows(AccessDeniedException.class,
                () -> commentService.delete(comment.getId(), userDetails));
    }
}
