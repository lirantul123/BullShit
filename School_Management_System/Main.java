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
    public static int messageId = 1;
    public static Scanner in = new Scanner(System.in);
    public static String fname, password;
    public static Map<String, Entry<User, List<Grade>>> userMap = new HashMap<>();
    public static Queue<Message> ms = new Queue<Message>(); // Holding the messages, message queue will hold the waiting messages which have been received

    public static void main(String[] args) {

        File userFile = new File("users.txt");
        if (userFile.exists()) {
            readUserDataFromFile("users.txt");
        } else {
            User admin = new User("admin", "pass", "admin", ms);
            userMap.put(admin.getRole(), new AbstractMap.SimpleEntry<>(admin, new ArrayList<>()));
            User stu = new User("student", "123", "student", ms);
            userMap.put(stu.getRole(), new AbstractMap.SimpleEntry<>(stu, new ArrayList<>()));

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
            System.out.println("Menu:");
            System.out.println("1. Show Grades");

            if (user.getRole().equals("admin")) {
                System.out.println("2. Add Student");
                System.out.println("3. Log Out");
                System.out.println("4. Send Message");
                System.out.println("5. Show Messages");
                System.out.print("Select an option (1, 2, 3, 4, or 5): ");
            } else {
                System.out.println("2. Log Out");
                System.out.println("3. Send Message");
                System.out.println("4. Show Messages");
                System.out.print("Select an option (1, 2, 3, or 4): ");
            }

            int option = in.nextInt();
            in.nextLine();
            System.out.println("----------------------------------");

            int studentsNum = 0;
            switch (option) {
                case 1:
                    if (user.getRole().equals("admin")) {
                        for (Entry<String, Entry<User, List<Grade>>> entry : userMap.entrySet()) {
                            User student = entry.getValue().getKey();
                            if (student.getRole().equals("student")) {
                                System.out.println("----------------------------------");
                                System.out.println(student.getName() + "'s Grades:");
                                for (Grade grade : student.getGrades()) {
                                    System.out.println(grade);
                                    studentsNum++;
                                }
                                System.out.println("----------------------------------");
                            }
                        }
                        System.out.println((studentsNum == 0) ? "No Students yet.\n---------------------------------- " : "[(Having " + studentsNum + ")]");


                    } else if (user.getRole().equals("student")) {
                        System.out.println(user.getName() + "'s Grades:");
                        for (Grade grade : user.getGrades()) {
                            System.out.println(grade);
                        }
                        System.out.println("----------------------------------");
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

                        User newStudent = new User(newStudentName, newStudentPassword, "student", ms);
                        List<Grade> newStudentGrades = new ArrayList<>();

                        String subAgrade;
                        do {
                            String invalidMessage = "Invalid input. Please use the format 'subject: grade'.";
                            System.out.print("Enter subject and grade (e.g., Math: 90.5), or 's' to stop: ");
                            subAgrade = in.nextLine();
                            if (!subAgrade.equals("s")) {
                                String[] parts = subAgrade.split(":");
                                if (parts.length == 2) {
                                    String subject = parts[0].trim();
                                    try {
                                        double grade = Double.parseDouble(parts[1].trim());
                                        newStudentGrades.add(new Grade(subject, grade));
                                    } catch (NumberFormatException e) {
                                        System.out.println(invalidMessage);
                                    }
                                } else {
                                    System.out.println(invalidMessage);
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
                    if (user.getRole().equals("admin")) {
                        System.out.println("Logging out.");
                        return;
                    }
                    else{
                        // Send Message
                        System.out.print("Recipient Name: ");
                        String recipientName = in.nextLine();

                        if (userMap.containsKey(recipientName)) {
                            System.out.print("Content: ");
                            String content = in.nextLine();
                            Message message = new Message(user.getName(), recipientName, content, "0"+messageId);
                            messageId++;
                            User recipient = userMap.get(recipientName).getKey();
                            recipient.getMessages().insert(message);
                            System.out.println("Message sent to " + recipientName);
                        } else {
                            System.out.println("Recipient not found.");
                        }
                        break;
                    }
                case 4:
                    // TODO: Check roles
                    // TODO: Send Message
                    if (user.getRole().equals("admin")) {
                        System.out.print("Recipient Name: ");
                        String recipientName = in.nextLine();

                        if (userMap.containsKey(recipientName)) {
                            System.out.print("Content: ");
                            String content = in.nextLine();
                            Message message = new Message(user.getName(), recipientName, content, "0"+messageId);
                            messageId++;
                            User recipient = userMap.get(recipientName).getKey();
                            recipient.getMessages().insert(message);
                            System.out.println("Message sent to " + recipientName);
                        } else {
                            System.out.println("Recipient not found.");
                        }
                        break;
                    }
                    else {
                        // Show Messages
                        int numOfMessages = 0;
                        System.out.println("Messages for " + user.getName() + ":");
                        user.getMessages().insert(null);

                        Message message = user.getMessages().head();
                        String firstId = message.getMessageId();

                        while (user.getMessages().head() != null) {
                             message = user.getMessages().head();

                            if (numOfMessages != 0){
                                if (message.getMessageId().equals(firstId))
                                    break;
                            }

                            if (message != null) { // Check if message is null
                                System.out.println("From: " + message.getSender());
                                System.out.println("Message: " + message.getContent());
                            }
                            user.getMessages().insert(user.getMessages().remove());
                            numOfMessages++;
                        }

                        user.getMessages().remove();
                        break;
                    }

                case 5:
                    // Show Messages
                    int numOfMessages = 0;
                    System.out.println("Messages for " + user.getName() + ":");
                    user.getMessages().insert(null);

                    Message message = user.getMessages().head();
                    String firstId = message.getMessageId();

                    while (user.getMessages().head() != null) {
                        message = user.getMessages().head();

                        if (numOfMessages != 0){
                            if (message.getMessageId().equals(firstId))
                                break;
                        }
                        if (message != null) { // Check if message is null
                            System.out.println("From: " + message.getSender());
                            System.out.println("Message: " + message.getContent());
                        }
                        user.getMessages().insert(user.getMessages().remove());
                        numOfMessages++;
                    }
                    user.getMessages().remove();
                    break;

                default:
                    // TODO: Check roles
                    System.out.println("Invalid option. Please select 1, 2, or 3 or 4 or maybe 5.");
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

                    User user = new User(name, password, role, ms);

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

    private Queue<Message> ms;

    public User(String name, String password, String role, Queue<Message> ms) {
        this.id = idCounter++;
        this.name = name;
        this.password = password;
        this.role = role;
        this.ms = ms;
        this.grades = new ArrayList<>();
    }

    public Queue<Message> getMessages() {
        return ms;
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
    private String sender;
    private String recipient;
    private String content;
    private String id;

    public Message(String sender, String recipient, String content, String id) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.id = id;
    }


    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public String getMessageId() {
        return id;
    }

}

class Node <T>{
    private T value;
    private Node <T> next;

    public Node(T value)
    {
        this.value=value;
        this.next=null;
    }

    public Node(T value, Node<T> next)
    {
        this.value=value;
        this.next=next;
    }

    public Node<T> getNext()
    {
        return next;
    }

    public T getValue()
    {
        return value;
    }
    public boolean hasNext()
    {
        return this.next != null;
    }

    public void setNext(Node<T> next)
    {
        this.next = next;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public String toString()
    {
        return  this.value.toString();
    }
}

class Queue <T> {
    private Node<T> first;
    private Node<T> last;

    public Queue()
    {
        this.first=null;
        this.last=null;
    }

    public boolean isEmpty()
    {
        return this.first==null;
    }

    public void insert(T x)
    {
        Node<T> temp=new Node<T> (x);
        if(this.last==null)
            this.first=temp;
        else
            this.last.setNext(temp);
        this.last=temp;
    }

    public T remove()
    {
        T x=this.first.getValue();
        this.first=this.first.getNext();
        if(this.first==null)
            this.last=null;
        return x;
    }

    public T head()
    {
        return this.first.getValue();
    }

    public String toString()
    {
        String str="[";
        Node<T> pos=this.first;
        while(pos != null)
        {
            if(pos.hasNext())
                str=str+pos.getValue()+", ";
            else
                str=str+pos.getValue();
            pos=pos.getNext();
        }
        str=str+"]";
        return str;
    }
}
