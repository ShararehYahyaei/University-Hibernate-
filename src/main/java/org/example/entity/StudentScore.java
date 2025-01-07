package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@Entity
@Data
@PackagePrivate
@NoArgsConstructor
public class StudentScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long student_id;
    double score;
    Long lesson_id;
    Long teacher_id;

    public StudentScore(Long student_id, Long lesson_id, Long teacher_id, double score) {
        this.student_id = student_id;
        this.score = score;
        this.lesson_id = lesson_id;
        this.teacher_id = teacher_id;
    }
}
