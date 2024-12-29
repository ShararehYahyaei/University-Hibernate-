package org.example.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsScore {
    private Long id;
    private Student student;
    private Lesson lesson;
    private double score;
    private Teacher teacher;

}
