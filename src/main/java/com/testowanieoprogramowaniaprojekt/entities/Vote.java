package com.testowanieoprogramowaniaprojekt.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;

    @ManyToOne
    @NotEmpty(message = "Comment is mandatory.")
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;

    @ManyToOne
    @NotEmpty(message = "User is mandatory.")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;




}
