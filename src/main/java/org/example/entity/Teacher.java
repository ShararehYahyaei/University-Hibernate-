package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Name name;
    @Column(nullable = false)
    @NotNull(message = "UserName must not be null")
    private String userName;
    @Column(nullable = false)
    @NotNull(message = "Password must not be null")
    private String password;
    @NotNull(message = "PhoneNumber must not be null")
    @Pattern(regexp = "\\+98\\d{9,10}", message = "Phone number must start with +98 and contain 9 to 10 digits after it")
    private String phoneNumber;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email must end with @gmail.com")
    @NotNull(message = "Email must not be null")
    private String email;
    @Column(nullable = false)
    @NotNull(message = "NationalCode must not be null")
    private String nationalCode;
    @Column(nullable = false)
    @NotNull(message = "Specialty must not be null")
    private String specialty;
    @Column(nullable = false)
    @NotNull(message = "Degree must not be null")
    private String degree;
    @Column(nullable = false)
    @NotNull(message = "Employee Code must not be null")
    private String employeeCode;

    public Teacher(Name name, String userName, String password, String phoneNumber, String email, String nationalCode, String specialty, String degree, String employeeCode) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationalCode = nationalCode;
        this.specialty = specialty;
        this.degree = degree;
        this.employeeCode = employeeCode;
    }
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", specialty='" + specialty + '\'' +
                ", degree='" + degree + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                '}';
    }

}
