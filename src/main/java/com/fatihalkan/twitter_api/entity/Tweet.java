package com.fatihalkan.twitter_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tweet")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    @NotBlank
    @NotEmpty
    @NotNull
    private String content;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    @NotNull
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Retweet> retweets = new ArrayList<>();

    //LIKE
    public void addLike(Like like){
        if (like.getTweet().equals(this) && !likes.contains(like)){
            likes.add(like);
        }
    }
    public void removeLike(Like like){
        likes.remove(like);
    }

    //COMMENT
    public void addComment(Comment comment){
        if (comment.getTweet().equals(this) && !comments.contains(comment)){
            comments.add(comment);
        }
    }
    public void removeComment(Comment comment){
        comments.remove(comment);
    }

    //RETWEET
    public void addRetweet(Retweet retweet){
        if (retweet.getTweet().equals(this) && !retweets.contains(retweet)){
            retweets.add(retweet);
        }
    }
    public void removeRetweet(Retweet retweet){
        retweets.remove(retweet);
    }
}
