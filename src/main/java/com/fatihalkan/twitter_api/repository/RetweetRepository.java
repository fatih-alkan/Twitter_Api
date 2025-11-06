package com.fatihalkan.twitter_api.repository;

import com.fatihalkan.twitter_api.entity.Like;
import com.fatihalkan.twitter_api.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    @Query("SELECT r FROM Retweet r WHERE r.user.id = :userId")
    List<Retweet> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Retweet r WHERE r.tweet.id = :tweetId")
    List<Retweet> findByTweetId(@Param("tweetId") Long tweetId);

    @Query("SELECT r FROM Retweet r WHERE r.user.id = :userId AND r.tweet.id = :tweetId")
    Optional<Retweet> findByUserIdAndTweetId(@Param("userId") Long userId,
                                          @Param("tweetId") Long tweetId);

    @Query("SELECT COUNT(r) FROM Retweet r WHERE r.tweet.id = :tweetId")
    long countByTweetId(@Param("tweetId") Long tweetId);
}
