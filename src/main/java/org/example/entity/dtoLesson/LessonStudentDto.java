package org.example.entity.dtoLesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.dto.StudentDto;

import java.util.Arrays;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonStudentDto {

    private String courseName;
    private int capacity;
    private List<StudentDto> students;

    @Override
    public String toString() {
        return "LessonStudentDto{" +
                "courseName='" + courseName + '\'' +
                ", capacity=" + capacity +
                ", students=" +students.toString() +
                '}';
    }
}
