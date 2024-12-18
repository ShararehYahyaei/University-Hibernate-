package org.example;

import org.example.entity.Name;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.example.util.Validation;

import java.util.Optional;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner sc = new Scanner(System.in);
    static StudentService studentService = new StudentService();


    public static void main(String[] args) {
        //todo all of these methods and test are for student{
        //  System.out.println("welcome to the university portal");
        // registerStudent (getting data from admin for student )
        // saveStudent(new Name("hamideh", "yay"), "7880", "5689", "+989125697478", "hamideh@gmail.com", "1111111111", "1154");
        //  System.out.println(studentService.findById(1L));
        //todo studentService.findAll().forEach(System.out::println);
        // updateStudent();
        // getStudentById(1L);}
        //todo studentService.deleteStudent(1L);


    }

    private static void saveStudent(Name name, String userName, String password,
                                    String phoneNumber, String email, String nationalCode, String StudentNumber) {
        Student student = new Student(name, userName, password, phoneNumber, email, nationalCode, StudentNumber);
        studentService.save(student);
    }

//    public static Student registerStudent() {
//        System.out.println("Add new Student");
//        Name name = new Name();
//        System.out.println("please enter firstname");
//        name.setFirstName(sc.nextLine());
//        System.out.println("please enter lastname");
//        name.setLastName(sc.nextLine());
//        System.out.println("please enter password");
//        String password = sc.nextLine();
//        System.out.println("please enter phoneNumber");
//        String phoneNumber = sc.nextLine();
//        System.out.println("please enter email");
//        String email = sc.nextLine();
//        System.out.println("please enter userName");
//        String userName = sc.nextLine();
//        System.out.println("please enter nationalCode");
//        String nationalCode = sc.nextLine();
//        System.out.println("please enter StudentNumber");
//        String StudentNumber = sc.nextLine();
//        return new Student(name, userName, password, phoneNumber, email, nationalCode, StudentNumber);
//    }

    public static void updateStudent() {
        Validation<Student> studentValidation = new Validation<>();
        studentService.findAll().forEach(System.out::println);
        System.out.println("Enter student id: ");
        Long studentId = sc.nextLong();
        sc.nextLine();
        Optional<Student> student = studentService.findById(studentId);
        System.out.println("choose which so you want to update: phone number, email, password");
        System.out.println("1. phone number");
        System.out.println("2. email");
        System.out.println("3. password");
        String res = sc.nextLine();
        switch (res) {
            case "1":
                System.out.println("please enter phoneNumber");
                String phoneNumber = sc.nextLine();
                student.get().setPhoneNumber(phoneNumber);
                if (studentValidation.valid(student.get()).isEmpty()) {
                    studentService.update(student.get());
                } else {
                    studentValidation.valid(student.get()).forEach(System.out::println);
                }
                break;
            case "2":
                System.out.println("please enter email");
                String email = sc.nextLine();
                student.get().setEmail(email);
                if (studentValidation.valid(student.get()).isEmpty()) {
                    studentService.update(student.get());
                } else {
                    studentValidation.valid(student.get()).forEach(System.out::println);
                }
                break;
            case "3":
                System.out.println("please enter password");
                String password = sc.nextLine();
                student.get().setPassword(password);
                if (studentValidation.valid(student.get()).isEmpty()) {
                    studentService.update(student.get());
                } else {
                    studentValidation.valid(student.get()).forEach(System.out::println);
                }
                break;

        }
        student.get();
    }

    public static void getAllStudents() {
        studentService.findAll().forEach(System.out::println);
    }

    public static void getStudentById(Long id) {
        studentService.findById(id).ifPresent(System.out::println);
    }

    public void deleteStudentBYId(Long id) {
        studentService.deleteStudent(1L);
    }
}