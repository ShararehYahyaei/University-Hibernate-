package org.example.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    @NotNull(message = "StudentNumber must not be null")
    private String studentNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToMany(cascade = { CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST }, fetch = FetchType.EAGER)
    private List<Lesson> lesson=new ArrayList<>();
    @Override
    public String toString() {
        return "Student{" + "id=" + id +
                ", studentNumber='" + studentNumber + '\'' +
                '}';
    }

    public Student(String studentNumber, User user) {

        this.studentNumber = studentNumber;
        this.user = user;

    }

}
