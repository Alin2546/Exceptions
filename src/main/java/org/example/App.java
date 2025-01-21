package org.example;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Student student = new Student("Alin", "Anghel", "16.07.1800", Gender.M, 5000716);
        StudentRepo studentRepo = new StudentRepo();
        studentRepo.add(student);
    }
}
