package org.example;

import org.example.entity.*;
import org.example.service.LessonService;
import org.example.service.StudentService;
import org.example.service.TeacherService;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner sc = new Scanner(System.in);
    static StudentService studentService = new StudentService();
    static LessonService lessonService = new LessonService();
    static TeacherService teacherService = new TeacherService();


    public static void main(String[] args) {


    }


    public static void saveStudent() {
        System.out.println("Add new Student");
        Name name = new Name();
        System.out.println("please enter firstname");
        name.setFirstName(sc.nextLine());
        System.out.println("please enter lastname");
        name.setLastName(sc.nextLine());
        System.out.println("please enter password");
        String password = sc.nextLine();
        System.out.println("please enter phoneNumber");
        String phoneNumber = sc.nextLine();
        System.out.println("please enter email");
        String email = sc.nextLine();
        System.out.println("please enter userName");
        String userName = sc.nextLine();
        System.out.println("please enter nationalCode");
        String nationalCode = sc.nextLine();
        System.out.println("please enter StudentNumber");
        String studentNumber = sc.nextLine();
        User user = new User(name, userName, password, Type.Student, phoneNumber, email, nationalCode);
        Student student = new Student(studentNumber, user);
        studentService.saveStudent(student);
    }

    public static void updateStudent() {
        List<Student> students = studentService.findAll();
        for (Student student : students) {
            System.out.println(student.getId());
            System.out.println(student.getUser().getPhoneNumber());
            System.out.println(student.getUser().getEmail());
        }
        System.out.println("please enter your Id :");
        Long id = sc.nextLong();
        Student student = studentService.findById(id);
        System.out.println(student.getUser().getPhoneNumber());
        System.out.println(student.getUser().getEmail());
        System.out.println("which one do you like to change : ");
        System.out.println(" 1- PhoneNumber");
        System.out.println(" 2- Email");
        String result = sc.nextLine();
        switch (result) {
            case "1":
                System.out.println("please enter your new Phone Number :");
                student.getUser().setPhoneNumber(sc.nextLine());
                studentService.saveStudent(student);
                break;
            case "2":
                System.out.println("please enter your new Email :");
                student.getUser().setEmail(sc.nextLine());
                studentService.saveStudent(student);
                break;
        }


    }


    public void createNewLesson() {
        System.out.println("please enter your Course Name :");
        String courseName = sc.nextLine();
        System.out.println("please enter your Credit:");
        int credit = sc.nextInt();
        System.out.println("please enter your capacity");
        int capacity = sc.nextInt();
        System.out.println("please enter your StartDate");
        String startDate = sc.nextLine();
        Lesson lesson = new Lesson(courseName, credit, capacity, startDate);
        lessonService.saveLesson(lesson);
        System.out.println("Add new lesson successfully");


    }

    public static void saveTeacher() {
        System.out.println("please add information");
        Name name = new Name();
        System.out.println("please enter firstname");
        name.setFirstName(sc.nextLine());
        System.out.println("please enter lastname");
        name.setLastName(sc.nextLine());
        System.out.println("please enter password");
        String password = sc.nextLine();
        System.out.println("please enter phoneNumber");
        String phoneNumber = sc.nextLine();
        System.out.println("please enter email");
        String email = sc.nextLine();
        System.out.println("please enter userName");
        String userName = sc.nextLine();
        System.out.println("please enter nationalCode");
        String nationalCode = sc.nextLine();
        System.out.println("please enter Degree");
        String degree = sc.nextLine();
        System.out.println("please enter speciality");
        String speciality = sc.nextLine();
        System.out.println("please enter employee code ");
        String employeeCode = sc.nextLine();
        User user = new User(name, userName, password, Type.Teacher, phoneNumber, email, nationalCode);
        Teacher teacher = new Teacher(speciality, degree, employeeCode, user);
        teacherService.saveTeacher(teacher);
    }

    public void getAllTeachers() {
        List<Teacher> teachers = teacherService.findAll();
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getUser().getId());
            System.out.println(teacher.getSpecialty());
            System.out.println(teacher.getDegree());
        }

    }

    private static void addLessonForTeacher(List<Teacher> teachers) {
        System.out.println("please enter your Id For Teacher :");
        Long id = sc.nextLong();
        Teacher teacherForLesson = teacherService.findById(id);
        for (Teacher teacher : teachers) {
            if (teacher.getUser().getId().equals(id)) {
                teacherForLesson = teacher;
                System.out.println(teacher.getUser().getUserName());
                System.out.println(teacher.getSpecialty());
            } else {
                System.out.println("Invalid Id");
            }
        }
        System.out.println("These are lessons");
        getAllLessons();
        System.out.println("please enter your Id For Lesson : ");
        System.out.println("Press 0 for Exit");
        List<Lesson> lessonForTeachers = new ArrayList<>();
        while (true) {
            System.out.print("Enter Lesson ID: ");
            Long lessonId = sc.nextLong();
            if (lessonId == 0) {
                break;
            }
            Lesson lesson = lessonService.findById(lessonId);
            if (lesson != null) {
                lessonForTeachers.add(lesson);
                teacherForLesson.setLesson(lessonForTeachers);
                teacherService.teacherUpdate(teacherForLesson);
                System.out.println("Lesson added successfully!");
            } else {
                System.out.println("Invalid Lesson ID. Please try again.");
            }
        }

    }

    private static void getAllLessons() {
        List<Lesson> lessons = lessonService.findAll();
        for (Lesson lesson : lessons) {
            System.out.println(lesson.getId());
            System.out.println(lesson.getCredit());
            System.out.println(lesson.getCapacity());

        }
    }
}