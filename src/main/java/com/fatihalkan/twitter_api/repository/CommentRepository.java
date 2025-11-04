package com.fatihalkan.twitter_api.repository;

import com.fatihalkan.twitter_api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId")
    List<Comment> findByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Comment c WHERE c.tweet.id = :tweetId")
    List<Comment> findByTweetId(@Param("tweetId") Long tweetId);
}
