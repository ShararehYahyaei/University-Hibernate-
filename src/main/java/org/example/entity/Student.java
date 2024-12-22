package org.example.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Embedded
    private Name name;
    @Column(nullable = false)
    @NotNull(message = "StudentNumber must not be null")
    private String studentNumber;
    @OneToOne
    @Cascade({CascadeType.REMOVE, CascadeType.PERSIST})
    private User user;
    @ManyToMany(mappedBy = "students" )
    List<Lesson> lessons;
    @Override
    public String toString() {
        return "Student{" + "id=" + id +
                ", name=" + name +
                ", studentNumber='" + studentNumber + '\'' +
                '}';
    }

    public Student(Name name, String studentNumber, User user) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.user = user;

    }
}
