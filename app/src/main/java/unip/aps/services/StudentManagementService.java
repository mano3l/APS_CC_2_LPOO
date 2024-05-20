package unip.aps.services;

import unip.aps.models.Student;
import unip.aps.utils.DataFormatter;
import unip.aps.utils.JSONUtility;

import java.util.List;

public class StudentManagementService {
    private final JSONUtility<Student> jsonFile;

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
            if (student == null) {
                return false;
            }
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

    public void changeStudentName(String cpf, String newName, String newLastName) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                student.setNome(newName);
                student.setSobrenome(newLastName);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void changeStudentAge(String cpf, int newAge) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                student.setIdade(newAge);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void changeStudentSex(String cpf, String newSex) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                student.setSexo(newSex);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void changeStudentPhone(String cpf, String newPhone) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                DataFormatter df = new DataFormatter();
                String formattedPhone = df.formatPhoneNumber(newPhone);
                student.setTelefone(formattedPhone);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void changeStudentAddress(String cpf, String newAddress) {
        List<Student> students = this.getStudents();
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                student.setEndereco(newAddress);
                this.jsonFile.updateJSON(student, students.indexOf(student));
            }
        }
    }

    public void deleteStudent(String cpf) {
        List<Student> students = this.getStudents();
        var ems = new EnrollmentManagementService("Matriculas.json");
        int i =0;
        for (Student student : students) {
            if (student.getCpf().equals(cpf)) {
                ems.deleteEnrollmentByCPF(cpf);
                this.jsonFile.deleteJSON(student, i);
            }
            i++;
        }
    }
}
