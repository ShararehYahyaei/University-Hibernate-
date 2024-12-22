package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name="USERS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public User(Name name,String userName, String password, Type type, String phoneNumber, String email, String nationalCode) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationalCode = nationalCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "UserName must not be null")
    private String userName;
    @Embedded
    private Name name;
    @Column(nullable = false)
    @NotNull(message = "Password must not be null")
    private String password;
    @Column(nullable = false)
    @NotNull(message = "Type must not be null")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(nullable = false)
    @NotNull(message = "PhoneNumber must not be null")
    @Pattern(regexp = "\\+98\\d{9,10}", message = "Phone number must start with +98 and contain 9 to 10 digits after it")
    private String phoneNumber;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email must end with @gmail.com")
    @NotNull(message = "Email must not be null")
    private String email;
    @Column(nullable = false)
    @NotNull(message = "NationalCode must not be null")
    private String nationalCode;


}
