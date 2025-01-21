package org.example;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class StudentRepo {
    Student student;
    Set<Student> studentSet = new TreeSet<>(Comparator.comparing(Student::getLastName));


    void add(Student student) throws CheckAgeException, NameEmptyException,  {
        if (actualAge(student.getDateOfBirth()) < 18 || actualAge(student.getDateOfBirth()) > 125) {
            throw new CheckAgeException("Inappropriate age");
        } else if (student.getFirstName().isEmpty() || student.getLastName().isEmpty()) {
            throw new NameEmptyException("First or last name should not be empty");
        }
        studentSet.add(student);
    }

    public int actualAge(String age) {

        return 2025 - Integer.valueOf(age.substring(age.length() - 4, age.length()));
    }
}
