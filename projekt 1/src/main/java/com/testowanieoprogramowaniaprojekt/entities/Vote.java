package com.testowanieoprogramowaniaprojekt.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
