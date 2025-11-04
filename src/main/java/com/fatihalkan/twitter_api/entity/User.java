package com.fatihalkan.twitter_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NotBlank
    private String userName;

    @Column(name = "first_name")
    @Size(max = 100)
    @NotNull
    @NotEmpty
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NotBlank
    private String lastName;

    @Column(name = "email")
    @Size(max = 100)
    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    @ToString.Exclude
    private String email;

    @Column(name = "phone_number")
    @Size(max = 30)
    @ToString.Exclude
    private String phoneNumber;

    @Column(name = "password")
    @NotNull
    @NotEmpty
    @NotBlank
    @ToString.Exclude
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Tweet> tweets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Retweet> retweets = new HashSet<>();

    //TWEET
    public void addTweet(Tweet tweet){
        if (tweet.getUser().equals(this) && !tweets.contains(tweet)){
            tweets.add(tweet);
        }
    }
    public void removeTweet(Tweet tweet){
        tweets.remove(tweet);
    }

    //LIKE
    public void addLike(Like like){
        if (like.getUser().equals(this)){
            likes.add(like);
        }
    }
    public void removeLike(Like like){
        likes.remove(like);
    }

    //COMMENT
    public void addComment(Comment comment){
        if (comment.getUser().equals(this) && !comments.contains(comment)){
            comments.add(comment);
        }
    }
    public void removeComment(Comment comment){
        comments.remove(comment);
    }

    //RETWEET
    public void addRetweet(Retweet retweet){
        if (retweet.getUser().equals(this)){
            retweets.add(retweet);
        }
    }
    public void removeRetweet(Retweet retweet){
        retweets.remove(retweet);
    }

}
