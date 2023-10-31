package com.testowanieoprogramowaniaprojekt.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotEmpty(message = "Author is mandatory.")
    private User author;

    @ManyToOne
    @NotEmpty(message = "Subreddit is mandatory.")
    private Subreddit subreddit;

    /*TODO: add this after comments and votes are added
    @OneToMany
    private Set<Comment> comments;

    @OneToMany
    private Set<Votes> votes;
    */

    @NotEmpty(message = "Title is mandatory.")
    private String title;

    @NotEmpty(message = "Description is mandatory.")
    private String description;
}
