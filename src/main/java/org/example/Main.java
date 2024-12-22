package org.example;

import org.example.entity.*;
import org.example.service.StudentService;
import org.example.service.UserService;
import org.example.util.Validation;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner sc = new Scanner(System.in);
    static StudentService studentService = new StudentService();
    static UserService userService = new UserService();


    public static void main(String[] args) {


    }

    private static void saveStudent(Student student) {
        studentService.save(student);
    }

    public static Student registerStudent() {
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
        User user = new User(name,userName, password, Type.Student, phoneNumber, email, nationalCode);
        return new Student( studentNumber, user);
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
                studentService.save(student);
                break;
            case "2":
                System.out.println("please enter your new Email :");
                student.getUser().setEmail(sc.nextLine());
                studentService.save(student);
                break;
        }


    }

//    public static void getAllStudents() {
//        studentService.findAll().forEach(System.out::println);
//    }
////
//    public static void getStudentById(Long id) {
//        studentService.findById(id).ifPresent(System.out::println);
//    }

    public void deleteStudentBYId(Long id) {
        studentService.deleteStudent(1L);
    }
}