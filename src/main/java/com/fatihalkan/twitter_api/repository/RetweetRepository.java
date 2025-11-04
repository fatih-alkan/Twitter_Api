package com.fatihalkan.twitter_api.repository;

import com.fatihalkan.twitter_api.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    @Query("SELECT r FROM Retweet r WHERE r.user.id = :userId")
    List<Retweet> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Retweet r WHERE r.tweet.id = :tweetId")
    List<Retweet> findByTweetId(@Param("tweetId") Long tweetId);
}
