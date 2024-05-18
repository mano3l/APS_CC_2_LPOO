package unip.aps.services;

import unip.aps.models.Enrollment;
import unip.aps.models.Student;
import unip.aps.utils.JSONUtility;

import java.util.List;
import java.util.Scanner;

public class StudentManagementService {
    private final JSONUtility<Student> jsonFile;
    Scanner sc = new Scanner(System.in);

    public StudentManagementService(String path) {
        this.jsonFile = new JSONUtility<>(path, Student.class);
    }

    public List<Student> getStudents() {
        return this.jsonFile.parseJSON();
    }

    public boolean registerStudent(Student student) {
        if (!isStudentRegistered(student)) {
            this.jsonFile.appendToJSON(student);
            return true;
        }
        return false;
    }

    public boolean isStudentRegistered(Student student) {
        if (this.getStudents() == null || this.getStudents().isEmpty()) {
            return false;
        }

        List<Student> students = this.getStudents();
        for (Student s : students) {
            if (s.getCpf().equals(student.getCpf())) {
                return true;
            }
        }
        return false;
    }

    public Student getStudentByCPF(String cpf) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                return student;
            }
        }
        return null;
    }

    public void changeName(String cpf, String newName) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                student.setNome(newName);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void changeAge(String cpf, int newAge) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                student.setIdade(newAge);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void changeSex(String cpf, String newSex) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                student.setSexo(newSex);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void changePhone(String cpf, String newPhone) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                student.setTelefone(newPhone);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void deleteStudent(String cpf) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                for (Enrollment enrollment : new EnrollmentManagementService("enrollments.json").getEnrollments()) {
                    if (enrollment.getCpf().equals(cpf)) {
                        new EnrollmentManagementService("enrollments.json").deleteEnrollmentByCPF(cpf);
                    }
                }
                students.remove(student);
                this.jsonFile.updateJSON(students);
                return;
            }
        }
    }
}
