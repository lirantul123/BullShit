package School_Management_System;

import java.io.*;
import java.util.Scanner;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static String fname, password;
    public static Map<String, Entry<User, List<Grade>>> userMap = new HashMap<>();

    public static void main(String[] args) {
        File userFile = new File("users.txt");
        if (userFile.exists()) {
            readUserDataFromFile("users.txt");
        } else {
            User admin = new User("admin", "pass", "admin");
            userMap.put(admin.getRole(), new AbstractMap.SimpleEntry<>(admin, new ArrayList<>()));
        }

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Welcome to the School Management System");
            System.out.println("1. Log in");
            System.out.println("2. Exit");
            System.out.print("Select an option (1 or 2): ");
            int option = in.nextInt();
            in.nextLine(); 

            if (option == 1) {
                login();
            } else if (option == 2) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please select 1 or 2.");
            }
        }
    }

    public static void login() {
        System.out.println("----------------------------------");

        do {
            System.out.println("Enter your full name: ");
            fname = in.nextLine();
            System.out.println("Enter your password: ");
            password = in.nextLine();
            System.out.println();

            if (userMap.containsKey(fname) && userMap.get(fname).getKey().getPassword().equals(password)) {
                User user = userMap.get(fname).getKey();
                System.out.printf("Welcome, %s. You are logged in as a %s.\n", fname, user.getRole());
                showUserMenu(user);
                break;
            } else {
                System.out.println("Invalid username or password. Try again.");
            }
        } while (true);

        System.out.println("----------------------------------");
    }

    public static void showUserMenu(User user) {
        while (true) {
            System.out.println("User Menu:");
            System.out.println("1. Show Grades");
            if (user.getRole().equals("admin")) {
                System.out.println("2. Add Student");
                System.out.println("3. Log Out");
                System.out.print("Select an option (1, 2, or 3): ");

            } else {
                System.out.println("2. Log Out");
                System.out.print("Select an option (1 or 2): ");
            }
    
            int option = in.nextInt();
            in.nextLine(); 
            System.out.println("----------------------------------");

            switch (option) {
                case 1:
                if (user.getRole().equals("admin")) {
                    for (Entry<String, Entry<User, List<Grade>>> entry : userMap.entrySet()) {
                        User student = entry.getValue().getKey();
                        if (student.getRole().equals("student")) { // Change "student" to "student" (lowercase)
                            System.out.println("----------------------------------");
                            System.out.println(student.getName() + "'s Grades:");
                            for (Grade grade : student.getGrades()) {
                                System.out.println(grade);
                            }
                            System.out.println("----------------------------------");
                        }
                    }
                } else if (user.getRole().equals("student")) { // Change "student" to "student" (lowercase)
                    System.out.println(user.getName() + "'s Grades:");
                    for (Grade grade : user.getGrades()) {
                        System.out.println(grade);
                    }
                }
                break;
                
                case 2:
                    if (user.getRole().equals("student")) {
                        System.out.println("Logging out.");
                        return;
                    }
                    
                    if (user.getRole().equals("admin")) {
                        System.out.print("Enter new student's name: ");
                        String newStudentName = in.nextLine();
                        System.out.print("Enter new student's password: ");
                        String newStudentPassword = in.nextLine();
                    
                        User newStudent = new User(newStudentName, newStudentPassword, "student");
                        List<Grade> newStudentGrades = new ArrayList<>();
                    
                        String subAgrade;
                        do {
                            System.out.print("Enter subject and grade (e.g., Math: 90.5), or 's' to stop: ");
                            subAgrade = in.nextLine();
                            if (!subAgrade.equals("s")) {
                                String[] parts = subAgrade.split(":");
                                if (parts.length == 2) {
                                    String subject = parts[0].trim();
                                    double grade = Double.parseDouble(parts[1].trim());
                                    newStudentGrades.add(new Grade(subject, grade));
                                } else {
                                    System.out.println("Invalid input. Please use the format 'subject: grade'.");
                                }
                            }
                        } while (!subAgrade.equals("s"));
                    
                        userMap.put(newStudentName, new AbstractMap.SimpleEntry<>(newStudent, newStudentGrades));
                        System.out.println("New student '" + newStudentName + "' added and grades same.");
                        System.out.println("----------------------------------");
                    
                        System.out.println(newStudentName + "'s Grades:");
                        for (Grade grade : newStudentGrades) {
                            System.out.println(grade);
                        }
                        
                        userMap.get(newStudentName).getKey().getGrades().addAll(newStudentGrades);
                    }
                    break;
                                
                case 3:
                    System.out.println("Logging out.");
                    return;
    
                default:
                    System.out.println("Invalid option. Please select 1, 2, or 3.");
            }
        }
    }
    
    public static void readUserDataFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String name = parts[0];
                    String password = parts[1];
                    String role = parts[2];

                    User user = new User(name, password, role);

                    for (int i = 3; i < parts.length; i++) {
                        String[] gradeParts = parts[i].split(":");
                        if (gradeParts.length == 2) {
                            String subject = gradeParts[0];
                            double subGrade = Double.parseDouble(gradeParts[1]);
                            user.addOrUpdateGrade(subject, subGrade);
                        }
                    }

                    userMap.put(name, new AbstractMap.SimpleEntry<>(user, user.getGrades()));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class User {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String password;
    private String role;
    private List<Grade> grades;

    public User(String name, String password, String role) {
        this.id = idCounter++;
        this.name = name;
        this.password = password;
        this.role = role;
        this.grades = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void addOrUpdateGrade(String subject, double subGrade) {
        for (Grade grade : grades) {
            if (grade.getSubject().equalsIgnoreCase(subject)) {
                grade.setSubGrade(subGrade);
                return;
            }
        }
        Grade newGrade = new Grade(subject, subGrade);
        grades.add(newGrade);
    }
}

class Grade {
    private String subject;
    private double subGrade;

    public Grade(String subject, double subGrade) {
        this.subject = subject;
        this.subGrade = subGrade;
    }

    public double getSubGrade() {
        return subGrade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubGrade(double subGrade) {
        this.subGrade = subGrade;
    }

    @Override
    public String toString() {
        return subject + ": " + subGrade;
    }
}

class Message {
    private User sender;
    private User recipient;
    private String content;

    public Message(User sender, User recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }
}
