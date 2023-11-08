package com.testowanieoprogramowaniaprojekt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name="posts")
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subreddit_id", nullable = false)
    private Subreddit subreddit;

    @OneToMany
    @JsonIgnore
    private Set<Comment> comments;

    @NotEmpty(message = "Title is mandatory.")
    private String title;

    @NotEmpty(message = "Description is mandatory.")
    private String description;
}
