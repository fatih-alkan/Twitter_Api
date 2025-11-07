package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.tweet.TweetRequestDto;
import com.fatihalkan.twitter_api.dto.tweet.TweetResponseDto;
import com.fatihalkan.twitter_api.entity.Tweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.TweetNotFoundException;
import com.fatihalkan.twitter_api.mapper.TweetMapper;
import com.fatihalkan.twitter_api.repository.TweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TweetServiceImplTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private TweetMapper tweetMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private TweetServiceImpl tweetService;

    private User user;
    private Tweet tweet;
    private TweetRequestDto requestDto;
    private TweetResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUserName("testuser");

        tweet = new Tweet();
        tweet.setId(10L);
        tweet.setContent("Hello world");
        tweet.setUser(user);

        requestDto = new TweetRequestDto("Hello world");
        responseDto = new TweetResponseDto(
                10L,
                "Hello world",
                1L,
                "testuser",
                null,
                null,
                OffsetDateTime.now()
        );

        when(userDetails.getUsername()).thenReturn("testuser");
    }

    // ---------------------------------------------------------------
    @Test
    @DisplayName("getAll() — tüm tweetleri listelemeli ve DTO'ya dönüştürmeli")
    void testGetAll() {
        when(tweetRepository.findAll()).thenReturn(List.of(tweet));
        when(tweetMapper.toResponseDto(tweet)).thenReturn(responseDto);

        List<TweetResponseDto> result = tweetService.getAll();

        assertEquals(1, result.size());
        assertEquals("Hello world", result.get(0).content());
        verify(tweetRepository).findAll();
    }

    // ---------------------------------------------------------------
    @Test
    @DisplayName("getById() — tweet bulunduğunda doğru DTO dönmeli")
    void testGetByIdFound() {
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));
        when(tweetMapper.toResponseDto(tweet)).thenReturn(responseDto);

        TweetResponseDto result = tweetService.getById(10L);

        assertNotNull(result);
        assertEquals(10L, result.tweetId());
        verify(tweetRepository).findById(10L);
    }

    @Test
    @DisplayName("getById() — tweet bulunamadığında TweetNotFoundException fırlatmalı")
    void testGetByIdNotFound() {
        when(tweetRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(TweetNotFoundException.class, () -> tweetService.getById(99L));
    }

    // ---------------------------------------------------------------
    @Test
    @DisplayName("create() — yeni tweet oluşturmalı ve doğru DTO'yu dönmeli")
    void testCreate() {
        when(userService.findByUsername("testuser")).thenReturn(user);
        when(tweetMapper.toEntity(requestDto, user)).thenReturn(tweet);
        when(tweetRepository.save(tweet)).thenReturn(tweet);
        when(tweetMapper.toResponseDto(tweet)).thenReturn(responseDto);

        TweetResponseDto result = tweetService.create(userDetails, requestDto);

        assertNotNull(result);
        assertEquals("Hello world", result.content());
        verify(tweetRepository).save(tweet);
    }

    // ---------------------------------------------------------------
    @Test
    @DisplayName("update() — kullanıcı tweet sahibiyse içeriği güncellemeli")
    void testUpdateAuthorized() {
        when(userService.findByUsername("testuser")).thenReturn(user);
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));
        when(tweetMapper.toResponseDto(tweet)).thenReturn(responseDto);

        TweetResponseDto result = tweetService.update(10L, new TweetRequestDto("Updated"), userDetails);

        assertEquals("Updated", tweet.getContent());
        verify(tweetRepository).save(tweet);
    }

    @Test
    @DisplayName("update() — kullanıcı tweet sahibi değilse AccessDeniedException fırlatmalı")
    void testUpdateUnauthorized() {
        User other = new User();
        other.setId(2L);
        tweet.setUser(other);

        when(userService.findByUsername("testuser")).thenReturn(user);
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));

        assertThrows(AccessDeniedException.class, () ->
                tweetService.update(10L, new TweetRequestDto("Hacked!"), userDetails));
    }

    // ---------------------------------------------------------------
    @Test
    @DisplayName("delete() — kullanıcı tweet sahibiyse silme işlemini yapmalı")
    void testDeleteAuthorized() {
        when(userService.findByUsername("testuser")).thenReturn(user);
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));

        tweetService.delete(10L, userDetails);

        verify(tweetRepository).deleteById(10L);
    }

    @Test
    @DisplayName("delete() — kullanıcı tweet sahibi değilse AccessDeniedException fırlatmalı")
    void testDeleteUnauthorized() {
        User other = new User();
        other.setId(2L);
        tweet.setUser(other);

        when(userService.findByUsername("testuser")).thenReturn(user);
        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));

        assertThrows(AccessDeniedException.class, () -> tweetService.delete(10L, userDetails));
    }

    @Test
    @DisplayName("delete() — tweet bulunamadığında TweetNotFoundException fırlatmalı")
    void testDeleteNotFound() {
        when(userService.findByUsername("testuser")).thenReturn(user);
        when(tweetRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TweetNotFoundException.class, () -> tweetService.delete(99L, userDetails));
    }

    // ---------------------------------------------------------------
    @Test
    @DisplayName("getByUserId() — belirtilen kullanıcıya ait tweetleri dönmeli")
    void testGetByUserId() {
        when(tweetRepository.findByUserId(1L)).thenReturn(List.of(tweet));
        when(tweetMapper.toResponseDto(tweet)).thenReturn(responseDto);

        List<TweetResponseDto> result = tweetService.getByUserId(1L);

        assertEquals(1, result.size());
        verify(tweetRepository).findByUserId(1L);
    }
}
