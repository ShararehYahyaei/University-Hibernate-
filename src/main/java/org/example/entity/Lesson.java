package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {
    public Lesson(String courseName, int credit, int capacity, String startDate) {
        this.courseName = courseName;
        this.credit = credit;
        this.capacity = capacity;
        this.startDate = startDate;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "CourseName must not be null")
    private String courseName;
    @Column(nullable = false)
    @NotNull(message = "Credit must not be null")
    private int credit;
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
    @ManyToMany(mappedBy = "lesson",fetch = FetchType.EAGER)
    private List<Teacher> teacher = new ArrayList<>();
    @ManyToMany(mappedBy = "lesson",fetch = FetchType.EAGER)
    private List<Student> students =new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER,mappedBy = "lesson",cascade = { jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.REFRESH,
            CascadeType.PERSIST })
    private Score score ;


    public LocalDate getStartDateAsLocalDate() {
        return LocalDate.parse(this.startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


}
