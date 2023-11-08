package com.testowanieoprogramowaniaprojekt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Username is mandatory.")
    private String username;

    @NotEmpty(message = "Password is mandatory.")
    private String password;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private Set<Comment> comments;
}
