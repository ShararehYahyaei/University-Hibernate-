package org.example.entity.dtoTeacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {
   private String  EmployeeCode;
   private String  Specialty;
   private String  FirstName;
   private String  LastName;
}
