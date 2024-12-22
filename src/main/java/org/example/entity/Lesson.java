package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "CourseName must not be null")
    private String courseName;
    @Column(nullable = false)
    @NotNull(message = "Credit must not be null")
    private int credit;

    public Lesson(String courseName, int credit, int capacity, String startDate) {
        this.courseName = courseName;
        this.credit = credit;
        this.capacity = capacity;
        this.startDate = startDate;
    }

    @Column(nullable = false)
    @NotNull(message = "Capacity must not be null")
    private int capacity;
    @Column(nullable = false)
    @NotNull(message = "StartDate must not be null.")
    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "StartDate must follow the format yyyy-MM-dd."
    )
    private String startDate;
    public LocalDate getStartDateAsLocalDate() {
        return LocalDate.parse(this.startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    @ManyToMany(mappedBy = "lesson")
    private List<Teacher> teacher;
    @ManyToMany
    private List<Student> students ;
}
