package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class StudentRepoTest {

    Student student;
    Student secondStudent;
    StudentRepo studentRepo;

    @BeforeEach
    void init() {
        student = new Student("Bogdan", "Alex", "18.02.1998", Gender.M, "200032100");
        secondStudent = new Student("Irina", "Lefter", "22.01.2002", Gender.F, "110002001");
        studentRepo = new StudentRepo();
    }

    @Nested
    class additionTests {
        @Test
        void addNoInput() {
            assertEquals(0, studentRepo.studentSet.size(), "The initial set should be empty");
        }

        @Test
        void addOneInput() {
            studentRepo.add(student);
            assertEquals(1, studentRepo.studentSet.size());
        }

        @Test
        void addManyInputs() {
            studentRepo.add(student);
            studentRepo.add(secondStudent);
            assertEquals(2, studentRepo.studentSet.size());
        }

        @Test
        void addDuplicateInputs() {
            studentRepo.add(student);
            studentRepo.add(student);
            assertEquals(1, studentRepo.studentSet.size(), "There should not be 2 similar students in the set");
        }

        @Test
        void addAgeException() {
            student.setDateOfBirth("12.01.1890");
            assertThrows(CheckAgeException.class, () -> studentRepo.add(student), "student age must be in the range [18, 126)");
        }

        @Test
        void addLastNameEmptyException() {
            student.setLastName("");
            assertThrows(NameEmptyException.class, () -> studentRepo.add(student), "Student last name must not be empty");
        }

        @Test
        void addFirstNameEmptyException() {
            student.setFirstName("");
            assertThrows(NameEmptyException.class, () -> studentRepo.add(student), "Student First name must not be empty");
        }

        @Test
        void addSpecialCharactersException() {
            student.setDateOfBirth("12.01.****");
            assertThrows(SpecialCharactersAgeException.class, () -> studentRepo.add(student), "Date of birth must contain only digits");
        }
    }

    @Nested
    class deletionTests {
        @Test
        void deleteOneInput() {
            studentRepo.add(student);
            try {
                studentRepo.delete(student.getID());
            } catch (IdentifierEmptyException | StudentMissingException e) {
                System.out.println(e.getMessage());
            }

            assertEquals(0, studentRepo.studentSet.size());

        }

        @Test
        void deleteNoInput() {
            try {
                studentRepo.delete("");
            } catch (IdentifierEmptyException | StudentMissingException e) {
                assertEquals("ID field is empty", e.getMessage());
            }
        }

        @Test
        void deleteManyInputs() {
            studentRepo.add(student);
            studentRepo.add(secondStudent);
            try {
                studentRepo.delete(student.getID());
                studentRepo.delete(secondStudent.getID());
            } catch (IdentifierEmptyException | StudentMissingException e) {
                System.out.println(e.getMessage());
            }
            assertEquals(0, studentRepo.studentSet.size());
        }

        @Test
        void deleteStudentMissingInput() {
            try {
                studentRepo.delete("932181293192318238283128388");
            } catch (IdentifierEmptyException | StudentMissingException e) {
                assertEquals("Student with ID: 932181293192318238283128388 does not exist", e.getMessage());
            }

        }
    }


    @Test
    void retrieveAllNegativeAgeException() {
        studentRepo.add(student);
        assertThrows(NegativeAgeException.class, () -> studentRepo.retrieveAll(-1), "Invalid Age");

    }

    @Nested
    class actualAgeTests {
        @Test
        void actualAgeNoInput() {
            assertThrows(IndexOutOfBoundsException.class, () -> studentRepo.actualAge(""), "Birth date is invalid");
        }

        @Test
        void actualAgeOneInput() {
            assertEquals(27, studentRepo.actualAge(student.getDateOfBirth()));
        }

        @Test
        void actualAgeSpecialCharactersException() {
            assertThrows(SpecialCharactersAgeException.class, () -> studentRepo.actualAge("*23ac!@#$#%^"), "Invalid Characters");
        }

        @ParameterizedTest
        @CsvSource({
                "16.07.2000, 25",
                "01.01.1999, 26",
                "05.12.2012, 13",
                "12.12.1900, 125",
                "04.06.2012, 13",
                "12.12.1955, 70",
                "31.11.1231, 794",
                "23.08.1111, 914",
        })
        void actualAgeManyInputs(String dateOfBirth, int expected) {
            assertEquals(expected, studentRepo.actualAge(dateOfBirth));
        }
    }


    @Test
    void displayStudentsEmptyNameException() {
        student.setLastName("");
        assertThrows(NameEmptyException.class, () -> studentRepo.add(student), "Invalid name");

    }
}
