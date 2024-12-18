package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Admin_user")
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @NotNull(message = "UserName must not be null")
    private String username;
    @Column(nullable = false)
    @NotNull(message = "Password must not be null")
    private String password;

    public AdminUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
