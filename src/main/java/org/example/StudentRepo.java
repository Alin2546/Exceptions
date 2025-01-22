package org.example;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


public class StudentRepo {

    Set<Student> studentSet = new TreeSet<>(Comparator.comparing(Student::getLastName));

    void add(Student student) throws CheckAgeException, NameEmptyException, InappropriateGenderException {
        if (actualAge(student.getDateOfBirth()) < 18 || actualAge(student.getDateOfBirth()) > 125) {
            throw new CheckAgeException("Inappropriate age: " + actualAge(student.getDateOfBirth()) + " years");
        } else if (student.getFirstName().isEmpty() || student.getLastName().isEmpty()) {
            throw new NameEmptyException("First or last name should not be empty");
        } else if (student.getGender() != Gender.M && student.getGender() != Gender.F) {
            throw new InappropriateGenderException("Please provide an actual gender");
        }
        studentSet.add(student);
    }

    void delete(String ID) throws IdentifierEmptyException {
        if (ID.isEmpty()) {
            throw new IdentifierEmptyException("ID field is empty");
        }

        boolean isInSet = false;

        for (Student student : studentSet) {
            if (student.getID().equals(ID)) {
                studentSet.remove(student);
                isInSet = true;
                break;
            }
        }

        if (!isInSet) {
            throw new StudentMissingException("Student with ID: " + ID + " does not exist");
        }


    }

    void retrieveAll(int x) throws NegativeAgeException {
        for (Student student : studentSet) {
            if (x < 18) {
                throw new NegativeAgeException("Age requested cannot be negative or under 18");
            }
            if (actualAge(student.getDateOfBirth()) == x) {
                System.out.println(student);
            }
        }
    }

    int actualAge(String age) throws SpecialCharactersAgeException {

        int beginIndex = age.length() - 4;
        for (int i = beginIndex; i < age.length(); i++) {
            if (!(Character.isDigit(age.charAt(i)))) {
                throw new SpecialCharactersAgeException("Invalid Character: " + age.charAt(i));
            }
        }

        return 2025 - Integer.parseInt(age.substring(beginIndex));
    }

    void displayStudents() throws EmptyAgeException, NameEmptyException {
        studentSet.forEach(student -> {
            if (student.getLastName().isEmpty()) {
                throw new EmptyAgeException("Last name is empty");
            } else if (student.getDateOfBirth().isEmpty()) {
                throw new NameEmptyException("Birth date missing");
            }
        });

        studentSet.forEach(System.out::println);
    }
}
