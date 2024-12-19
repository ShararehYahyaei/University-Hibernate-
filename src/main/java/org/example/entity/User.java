package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "UserName must not be null")
    private String userName;
    @Column(nullable = false)
    @NotNull(message = "Password must not be null")
    private String password;
    @Column(nullable = false)
    @NotNull(message = "Type must not be null")
    private String type;


}
