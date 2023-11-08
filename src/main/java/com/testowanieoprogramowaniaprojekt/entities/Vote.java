package com.testowanieoprogramowaniaprojekt.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Entity
@Getter
@Setter
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
