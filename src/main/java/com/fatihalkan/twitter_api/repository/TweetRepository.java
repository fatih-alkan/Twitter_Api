package com.fatihalkan.twitter_api.repository;

import com.fatihalkan.twitter_api.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet,Long> {
    @Query("SELECT t FROM Tweet t WHERE t.user.id = :userId ORDER BY t.createdAt DESC")
    List<Tweet> findByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Tweet t ORDER BY t.createdAt DESC")
    List<Tweet> findAll();
}
