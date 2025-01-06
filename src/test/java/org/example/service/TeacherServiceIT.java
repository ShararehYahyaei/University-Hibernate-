package org.example.service;

import org.example.entity.Name;
import org.example.entity.Teacher;
import org.example.entity.Type;
import org.example.entity.User;
import org.example.repository.TeacherRepo;
import org.junit.jupiter.api.Test;


public class TeacherServiceIT {
    private final TeacherRepo teacherRepo = new TeacherRepo();
    private final TeacherService teacherService = new TeacherService(teacherRepo);
    @Test
    void deleteTeacher() {
        Teacher teacher = new Teacher("engineer", "Bachelor", "4546", new User(new Name("T1", "T1Family"), "T1", "5699", Type.Teacher,
                "+989124444444", "T1@gmail.com", "1111111111"));
        Teacher result = teacherService.saveTeacher(teacher);
//        teacherService.deleteTeacher(result.getId());
//        assertNull(teacherService.findById(result.getId()));
    }

}
