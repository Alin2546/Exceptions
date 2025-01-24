package org.example;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Student repo class
 */
public class StudentRepo {

    Set<Student> studentSet = new TreeSet<>(Comparator.comparing(Student::getLastName));

    /**
     * @param student the student to be added to the set
     * @throws CheckAgeException            checks if age is lower than 18 and higher than 125
     * @throws NameEmptyException           checks if student name is empty
     * @throws InappropriateGenderException checks if the gender is M or F (Male or female)
     */
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

    /**
     * @param ID a string which contains a personal ID from a student to be deleted from the set
     * @throws IdentifierEmptyException checking if the id string parameter is null
     * @throws StudentMissingException  checking if the student is missing from the set
     */
    @SuppressWarnings("unused")
    void delete(String ID) throws IdentifierEmptyException, StudentMissingException {
        if (ID.isEmpty()) {
            throw new IdentifierEmptyException("ID field is empty");
        }

        boolean isInSet = studentSet.removeIf(student -> student.getID().equals(ID));


        if (!isInSet) {
            throw new StudentMissingException("Student with ID: " + ID + " does not exist");
        }

    }

    /**
     * @param x the age of students to be displayed
     * @throws NegativeAgeException check if x < 18
     */
    @SuppressWarnings("unused")
    void retrieveAll(int x) throws NegativeAgeException {

        studentSet.forEach(student -> {
            if (x < 18) {
                throw new NegativeAgeException("Age requested cannot be negative or under 18");
            } else if (actualAge(student.getDateOfBirth()) == x) {
                System.out.println(student);
            }
        });
    }

    /**
     * @param age age of student
     * @return year which was student born
     * @throws SpecialCharactersAgeException checking if {@code age} has invalid characters e.g.--> *,a,|,[
     */
    int actualAge(String age) throws SpecialCharactersAgeException {

        int beginIndex = age.length() - 4;
        for (int i = beginIndex; i < age.length(); i++) {
            if (!(Character.isDigit(age.charAt(i)))) {
                throw new SpecialCharactersAgeException("Invalid Character: " + age.charAt(i));
            }
        }

        return 2025 - Integer.parseInt(age.substring(beginIndex));
    }

    /**
     * @throws EmptyAgeException  checking if student dateOfBirth is an empty string
     * @throws NameEmptyException checking if last name of a student is empty
     */
    void displayStudents() throws EmptyAgeException, NameEmptyException {
        studentSet.forEach(student -> {
            if (student.getLastName().isEmpty()) {
                throw new NameEmptyException("Invalid name");
            } else if (student.getDateOfBirth().isEmpty()) {
                throw new EmptyAgeException("Birth date missing");
            }
            System.out.println(student);
        });


    }
}
