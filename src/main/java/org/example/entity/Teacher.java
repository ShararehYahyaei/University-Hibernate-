package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String specialty;
    @Column(nullable = false)
    @NotNull(message = "Degree must not be null")
    private String degree;
    @Column(nullable = false)
    @NotNull(message = "Employee Code must not be null")
    private String employeeCode;
    @OneToOne
    private User user;

    public Teacher(String specialty, String degree, String employeeCode) {

        this.specialty = specialty;
        this.degree = degree;
        this.employeeCode = employeeCode;
    }

    @ManyToMany
    private List<Lesson> lesson;

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", specialty='" + specialty + '\'' +
                ", degree='" + degree + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                '}';
    }

}
