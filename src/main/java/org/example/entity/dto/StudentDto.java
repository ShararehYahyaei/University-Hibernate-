package org.example.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor

public class StudentDto {
    private String studentNumber;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "StudentDto{" +
                "studentNumber='" + studentNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    //todo create constructor
}
