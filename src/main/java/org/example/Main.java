package org.example;

import org.example.entity.*;
import org.example.entity.dto.StudentDto;
import org.example.entity.dtoLesson.LessonStudentDto;
import org.example.service.LessonService;
import org.example.service.StudentService;
import org.example.service.TeacherService;
import org.example.service.UserService;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner sc = new Scanner(System.in);
    static StudentService studentService = new StudentService();
    static LessonService lessonService = new LessonService();
    static TeacherService teacherService = new TeacherService();
    static UserService userService = new UserService();


    public static void saveAdmin() {
        Name name = new Name("Admin", "Admin");
        User user = new User(name, "Admin", "Admin", Type.Admin,
                "Admin", "Admin", "Admin");
        userService.saveAdmin(user);

        User user1 = new User(new Name("Admin1", "Admin1"), "Admin1", "Admin1", Type.Admin,
                "Admin1", "Admin1", "Admin1");
        userService.saveAdmin(user1);

        User user2 = new User(new Name("Admin2", "Admin2"), "Admin2", "Admin2", Type.Admin,
                "Admin2", "Admin2", "Admin2");
        userService.saveAdmin(user2);

    }

    public static void main(String[] args) {
        saveAdmin();
        while (true) {
            User user = loginUser();
            if (user == null) {
                System.out.println("user not found");
                break;
            }
            switch (user.getType()) {
                case Admin:
                    menuForAdmin();
                    break;
                case Teacher:
                    Teacher teacher = teacherService.fetchByUserId(user);
                    if (teacher == null) {
                        System.out.println("teacher not found");
                    } else {
                        showMenuForTeacher(teacher);
                    }
                    break;
                case Student:
                    Student student = studentService.fetchByUserId(user);
                    if (student == null) {
                        System.out.println("student not found");
                    } else {
                        showMenuForStudent(student);
                    }
                    break;
            }

        }


    }

    private static void showMenuForTeacher(Teacher teacher) {
        System.out.println("Welcome To Teacher Portal");
        System.out.println("1-show All lessons with students");
        System.out.println("2-exit");
        String result = new Scanner(System.in).nextLine();
        switch (result) {
            case "1":
                showAllLessonsWithStudents(teacher);
                break;
            case "2":
                System.exit(0);
                break;


        }


    }

    private static void showAllLessonsWithStudents(Teacher teacher) {

        Teacher teacherForLesson = teacherService.findById(teacher.getId());
        if (teacherForLesson == null) {
            System.out.println("teacher not found");
        } else {
            System.out.println("List of lessons : ");
            List<LessonStudentDto> lessonStudentDtos = teacherService.getLessonsStudentsDto(teacherForLesson);
            for (LessonStudentDto lsd : lessonStudentDtos) {
                System.out.println(lsd.getCourseName() + " " + lsd.getCapacity());
                lsd.getStudents().forEach(student -> {
                    System.out.println(student.getStudentNumber()+" "+student.getFirstName()+" "+student.getLastName());
                });
            }

        }

    }


    public static User loginUser() {
        System.out.println("please enter your userName:");
        String userName = new Scanner(System.in).nextLine();
        System.out.println("please enter your password:");
        String password = new Scanner(System.in).nextLine();
        return userService.checkUsernameAndPassword(userName, password);
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

    public static void menuForAdmin() {
        System.out.println("Welcome to Admin Portal");
        System.out.println("1-Show all students");
        System.out.println("2-Show all lessons");
        System.out.println("3-Show all Teachers");
        System.out.println("4-Add new Teacher");
        System.out.println("5-Add new Lesson");
        System.out.println("6-Remove a lesson");
        System.out.println("7-Update a lesson");
        System.out.println("8-Add lessons for Teacher");
        System.out.println("9-Add New  Student");
        System.out.println("10-Exit");

        System.out.println("please enter your Request :");
        String result = new Scanner(System.in).nextLine();
        switch (result) {
            case "1":
                List<StudentDto> dtoListStudent = studentService.getStudentSimpleWithSomeProperties();
                if (dtoListStudent.isEmpty()) {
                    System.out.println("There is no student");
                } else {
                    for (StudentDto dto : dtoListStudent) {
                        System.out.println(dto);
                    }
                }
                break;
            case "2":
                List<Lesson> lessonsWithSOmeProperties = lessonService.findAll();
                if (lessonsWithSOmeProperties.isEmpty()) {
                    System.out.println("There is no lesson");
                } else {
                    for (Lesson lesson : lessonsWithSOmeProperties) {
                        System.out.println(lesson.getCapacity());
                        System.out.println(lesson.getCourseName());
                        System.out.println(lesson.getStartDate());
                    }
                }
                break;

            case "3":
                List<org.example.entity.dtoTeacher.TeacherDto> dtoTeachers = teacherService.getAllTeachersWithSOmeProperties();
                if (dtoTeachers.isEmpty()) {
                    System.out.println("There is no teacher");
                } else {
                    for (org.example.entity.dtoTeacher.TeacherDto dto : dtoTeachers) {
                        System.out.println(dto.getFirstName());
                        System.out.println(dto.getLastName());
                        System.out.println(dto.getEmployeeCode());
                    }
                }

                break;
            case "4":
                Teacher teacher = teacherService.teacherUpdate(saveTeacher());
                if (teacher == null) {
                    System.out.println("Invalid date and unsaved Teacher");
                } else {
                    System.out.println("Teacher successfully saved");
                }
                break;
            case "5":
                Lesson les = lessonService.lessonUpdate(saveNewLesson());
                if (les == null) {
                    System.out.println("Invalid date and unsaved Lesson");
                } else {
                    System.out.println("Lesson successfully saved");
                }
                break;

            case "6":
                List<Lesson> lessonsWithSOmePropertiesForDelete = lessonService.findAll();
                if (lessonsWithSOmePropertiesForDelete.isEmpty()) {
                    System.out.println("There is no lesson");
                } else {
                    for (Lesson lesson : lessonsWithSOmePropertiesForDelete) {
                        System.out.println(lesson.getId());
                        System.out.println(lesson.getCourseName());
                        System.out.println(lesson.getStartDate());
                    }
                }
                System.out.println("please enter your Id :");
                Long id = new Scanner(System.in).nextLong();
                Lesson lesson = lessonService.findById(id);
                if (lesson == null) {
                    System.out.println("Lesson not found");
                } else {
                    lessonService.deleteLesson(id);
                    System.out.println("Lesson successfully deleted");
                }
                break;
            case "7":
                lessonService.lessonUpdate(updateLesson());
                System.out.println("update successfully...");
                break;
            case "8":
                boolean keepGoing = true;
                while (keepGoing) {

                    List<Teacher> teachers = teacherService.findAll();
                    System.out.println("List of Teachers:");
                    for (Teacher t : teachers) {
                        System.out.println("ID: " + t.getId() +
                                ", Name: " + t.getUser().getName().getFirstName() + " " + t.getUser().getName().getLastName() +
                                ", Specialty: " + t.getSpecialty());
                    }


                    System.out.println("Please enter the Teacher ID to add lessons (or 0 to cancel):");
                    Long idForAddLessonForTeacher = sc.nextLong();
                    if (idForAddLessonForTeacher == 0) {
                        System.out.println("Operation cancelled.");
                        break;
                    }

                    Teacher teacherForUpdate = teacherService.findById(idForAddLessonForTeacher);
                    if (teacherForUpdate == null) {
                        System.out.println("Teacher not found. Please try again.");
                        continue;
                    }

                    List<Lesson> lessons = lessonService.findAll();
                    System.out.println("List of Lessons:");
                    for (Lesson l : lessons) {
                        System.out.println("ID: " + l.getId() +
                                ", Course Name: " + l.getCourseName() +
                                ", Start Date: " + l.getStartDate());
                    }

                    System.out.println("Please enter the Lesson ID to add to the teacherForUpdate (or 0 to cancel):");
                    Long idLesson = sc.nextLong();
                    if (idLesson == 0) {
                        System.out.println("Returning to teacherForUpdate selection...");
                        continue;
                    }

                    Lesson lessonForAddToTeacher = lessonService.findById(idLesson);
                    if (lessonForAddToTeacher == null) {
                        System.out.println("Lesson not found. Please try again.");
                        continue;
                    }

                    teacherForUpdate.getLesson().add(lessonForAddToTeacher);
                    teacherService.teacherUpdate(teacherForUpdate);

                    System.out.println("Lesson added successfully to teacherForUpdate.");

                    System.out.println("Do you want to add another lessonForAddToTeacher? (1/2):");
                    String response = sc.next().trim().toLowerCase();
                    if (response.equals("1")) {
                        keepGoing = false;
                    }
                }

                System.out.println("Program finished.");
                break;

            case "9":
                Student student = studentService.studentUpdate(savestudent());
                if (student == null) {
                    System.out.println("Invalid date and unsaved student");
                } else {
                    System.out.println("student successfully saved");
                }
                break;
            case "10":
                System.exit(0);
        }


    }


    public static Teacher saveTeacher() {
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
        return teacherService.saveTeacher(teacher);

    }

    public static Lesson saveNewLesson() {
        System.out.println("please enter course Name:");
        String courseName = new Scanner(System.in).nextLine();
        System.out.println("please enter Credit:");
        int credit = new Scanner(System.in).nextInt();
        System.out.println("please enter Capacity:");
        int capacity = new Scanner(System.in).nextInt();
        System.out.println("StartDate must follow the format yyyy-MM-dd:");
        String startDate = new Scanner(System.in).nextLine();
        Lesson lesson = new Lesson(courseName, credit, capacity, startDate);
        return lessonService.saveLesson(lesson);
    }

    public static Lesson updateLesson() {
        List<Lesson> lessons = lessonService.findAll();
        for (Lesson lesson : lessons) {
            System.out.println(lesson.getId());
            System.out.println(lesson.getCredit());
            System.out.println(lesson.getCapacity());
            System.out.println(lesson.getStartDate());
        }
        System.out.println("please enter Id for lesson:");
        Long id = sc.nextLong();
        Lesson les = lessonService.findById(id);
        System.out.println(les.getCredit());
        System.out.println(les.getCapacity());
        System.out.println(les.getStartDate());

        System.out.println("which one do you like to change it");
        System.out.println("1-credit");
        System.out.println("2-capacity");
        System.out.println("3-startDate");
        System.out.println("4-Exit");
        String result = new Scanner(System.in).nextLine();
        switch (result) {
            case "1":
                System.out.println("please enter Credit:");
                int credit = new Scanner(System.in).nextInt();
                les.setCredit(credit);
                lessonService.lessonUpdate(les);
                break;
            case "2":
                System.out.println("please enter Capacity:");
                int capacity = new Scanner(System.in).nextInt();
                les.setCapacity(capacity);
                lessonService.lessonUpdate(les);
                break;
            case "3":
                System.out.println("please enter startDate,,StartDate must follow the format yyyy-MM-dd:");
                String startDate = new Scanner(System.in).nextLine();
                les.setStartDate(startDate);
                lessonService.lessonUpdate(les);
                break;

        }
        return les;
    }

    public static Student savestudent() {
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
        System.out.println("please enter your student number:");
        String studentNumber = new Scanner(System.in).nextLine();
        User user = new User(name, userName, password, Type.Student, phoneNumber, email, nationalCode);
        Student student = new Student(studentNumber, user);
        return studentService.saveStudent(student);

    }


    public static void showMenuForStudent(Student student) {
        System.out.println("Welcome To Student Portal");
        System.out.println("1-show All lessons and take lessons");
        System.out.println("2-exit");
        String result = new Scanner(System.in).nextLine();
        switch (result) {
            case "1":
                takeNewLesStudent(student);
                break;
            case "2":
                System.exit(0);
                break;


        }

    }

    public static void takeNewLesStudent(Student student) {
        List<Lesson> lessonAvailable = lessonService.getAvailableLessons();
        if (lessonAvailable.isEmpty()) {
            System.out.println("There is no any available lesson");
            return;
        }

        System.out.println("List of Lessons:");
        for (Lesson lesson : lessonAvailable) {
            System.out.println("ID: " + lesson.getId() + ", Credit: " + lesson.getCredit() + ", Capacity: " + lesson.getCapacity());
        }

        Scanner scanner = new Scanner(System.in);
        List<Lesson> lessonsForTake = new ArrayList<>();

        while (true) {
            System.out.println("Please enter the ID of the lesson you want to add (or type 0 to finish):");
            Long id = scanner.nextLong();

            if (id == 0) {
                break;
            }

            Lesson lesson = lessonService.findById(id);
            if (lesson == null) {
                System.out.println("Invalid ID, please try again.");
                continue;
            }

            if (lesson.getCapacity() <= 0) {
                System.out.println("This lesson is full and cannot be added.");
                continue;
            }

            lessonsForTake.add(lesson);
            System.out.println("Lesson with ID " + id + " added successfully.");
        }

        if (!lessonsForTake.isEmpty()) {
            student.getLesson().addAll(lessonsForTake);
            studentService.studentUpdate(student);
            System.out.println("All selected lessons added successfully.");
        } else {
            System.out.println("No lessons were added.");
        }
    }

}