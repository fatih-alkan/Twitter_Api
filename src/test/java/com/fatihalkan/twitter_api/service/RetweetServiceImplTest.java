package com.fatihalkan.twitter_api.service;

import com.fatihalkan.twitter_api.dto.retweet.RetweetRequestDto;
import com.fatihalkan.twitter_api.dto.retweet.RetweetResponseDto;
import com.fatihalkan.twitter_api.entity.Retweet;
import com.fatihalkan.twitter_api.entity.User;
import com.fatihalkan.twitter_api.exception.RetweetNotFoundException;
import com.fatihalkan.twitter_api.mapper.RetweetMapper;
import com.fatihalkan.twitter_api.repository.RetweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RetweetServiceImplTest {

    @Mock
    private RetweetRepository repository;

    @Mock
    private RetweetMapper mapper;

    @Mock
    private UserService userService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private RetweetServiceImpl service;

    private User user;
    private Retweet retweet;
    private RetweetRequestDto requestDto;
    private RetweetResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUserName("testuser");

        retweet = new Retweet();
        retweet.setId(10L);
        retweet.setUser(user);

        requestDto = new RetweetRequestDto(1L); // tweetId örnek

        responseDto = new RetweetResponseDto(
                1L,
                2L,
                3L,
                "testuser",
                "Test",
                "User",
                10L,
                "Example tweet text",
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("getAll metodu tüm retweetleri döndürmeli")
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(retweet));
        when(mapper.toResponse(retweet)).thenReturn(responseDto);

        List<RetweetResponseDto> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals(responseDto, result.get(0));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("getByTweetId metodu tweetId'ye göre retweetleri döndürmeli")
    void testGetByTweetId() {
        when(repository.findByTweetId(1L)).thenReturn(List.of(retweet));
        when(mapper.toResponse(retweet)).thenReturn(responseDto);

        List<RetweetResponseDto> result = service.getByTweetId(1L);

        assertEquals(1, result.size());
        assertEquals(responseDto, result.get(0));
        verify(repository).findByTweetId(1L);
    }

    @Test
    @DisplayName("create metodu yeni retweet oluşturmalı")
    void testCreate() {
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(mapper.toEntity(requestDto, user)).thenReturn(retweet);
        when(repository.save(retweet)).thenReturn(retweet);
        when(mapper.toResponse(retweet)).thenReturn(responseDto);
        when(userDetails.getUsername()).thenReturn("testuser");

        RetweetResponseDto result = service.create(userDetails, requestDto);

        assertEquals(responseDto, result);
        verify(repository).save(retweet);
    }

    @Test
    @DisplayName("delete metodu kendi retweeti silmeli")
    void testDeleteSuccess() {
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(repository.findById(10L)).thenReturn(Optional.of(retweet));
        when(userDetails.getUsername()).thenReturn("testuser");

        assertDoesNotThrow(() -> service.delete(10L, userDetails));
        verify(repository).deleteById(10L);
    }

    @Test
    @DisplayName("delete metodu başka kullanıcının retweetini silmeye çalışınca hata fırlatmalı")
    void testDeleteForbidden() {
        User otherUser = new User();
        otherUser.setId(2L);
        retweet.setUser(otherUser);

        when(userService.findByUsername(anyString())).thenReturn(user);
        when(repository.findById(10L)).thenReturn(Optional.of(retweet));
        when(userDetails.getUsername()).thenReturn("testuser");

        assertThrows(RuntimeException.class, () -> service.delete(10L, userDetails));
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("countRetweets metodu tweetId'ye göre retweet sayısını döndürmeli")
    void testCountRetweets() {
        when(repository.countByTweetId(1L)).thenReturn(5L);

        long count = service.countRetweets(1L);

        assertEquals(5L, count);
        verify(repository).countByTweetId(1L);
    }

    @Test
    @DisplayName("isRetweetsByUser metodu kullanıcının retweet edip etmediğini döndürmeli")
    void testIsRetweetsByUser() {
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(repository.findByUserIdAndTweetId(1L, 1L)).thenReturn(Optional.of(retweet));
        when(userDetails.getUsername()).thenReturn("testuser");

        boolean result = service.isRetweetsByUser(userDetails, 1L);

        assertTrue(result);
        verify(repository).findByUserIdAndTweetId(1L, 1L);
    }

    @Test
    @DisplayName("findByUserId metodu kullanıcının tüm retweetlerini döndürmeli")
    void testFindByUserId() {
        when(repository.findByUserId(1L)).thenReturn(List.of(retweet));
        when(mapper.toResponse(retweet)).thenReturn(responseDto);

        List<RetweetResponseDto> result = service.findByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(responseDto, result.get(0));
        verify(repository).findByUserId(1L);
    }
}
