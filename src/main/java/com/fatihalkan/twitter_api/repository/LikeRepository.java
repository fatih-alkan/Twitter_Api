package com.fatihalkan.twitter_api.repository;

import com.fatihalkan.twitter_api.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    @Query("SELECT l FROM Like l WHERE l.user.id = :userId")
    List<Like> findByUserId(@Param("userId") Long userId);

    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.tweet.id = :tweetId")
    Optional<Like> findByUserIdAndTweetId(@Param("userId") Long userId,
                                          @Param("tweetId") Long tweetId);


    @Query("SELECT COUNT(l) FROM Like l WHERE l.tweet.id = :tweetId")
    long countByTweetId(@Param("tweetId") Long tweetId);
}
