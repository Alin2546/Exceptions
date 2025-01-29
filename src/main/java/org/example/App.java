package org.example;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Student student = new Student("Claudiu", "Darius", "16.07.2000", Gender.M, "5000716226730");
        Student student2 = new Student("Bogdan", "Ilie", "16.07.1950", Gender.M, "500071622612");
        Student student3 = new Student("Cristian", "Castan", "16.07.2006", Gender.F, "5000716321730");

        StudentRepo studentRepo = new StudentRepo();

        studentRepo.add(student2);
        studentRepo.add(student3);
        studentRepo.add(student);
        try {
            studentRepo.delete("5000716226730");
        } catch (IdentifierEmptyException | StudentMissingException e) {
            throw new RuntimeException(e);
        }
        studentRepo.displayStudents();

    }


}




