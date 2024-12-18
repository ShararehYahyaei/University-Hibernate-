package org.example.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Embedded
    private Name name;
    @Column(nullable = false)
    @NotNull(message = "UserName must not be null")
    private String userName;
    @Column(nullable = false)
    @NotNull(message = "Password must not be null")
    private String password;
    @Column(nullable = false)
    @NotNull(message = "PhoneNumber must not be null")
    @Pattern(regexp = "\\+98\\d{9,10}", message = "Phone number must start with +98 and contain 9 to 10 digits after it")
    private String phoneNumber;
    @NotNull(message = "Last name must not be null")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email must end with @gmail.com")
    @NotNull(message = "Email must not be null")
    private String email;
    @Column(nullable = false)
    @NotNull(message = "NationalCode must not be null")
    private String nationalCode;
    @Column(nullable = false)
    @NotNull(message = "StudentNumber must not be null")
    private String studentNumber;

    public Student(Name name, String userName, String password, String phoneNumber, String email, String nationalCode, String studentNumber) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationalCode = nationalCode;
        this.studentNumber = studentNumber;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Student{");
        sb.append("id=").append(id)
                .append(", name=").append(name)
                .append(", userName='").append(userName).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", phoneNumber='").append(phoneNumber).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", nationalCode='").append(nationalCode).append('\'')
                .append(", studentNumber='").append(studentNumber).append('\'')
                .append('}');
        return sb.toString();
    }

}
