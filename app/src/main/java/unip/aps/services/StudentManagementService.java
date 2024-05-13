package unip.aps.services;

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

    public boolean registerStudents(Student student) {
        if (!isRegistered(student)) {
            this.jsonFile.appendToJSON(student);
            return true;
        }
        return false;
    }

    public boolean isRegistered(Student student) {
        List<Student> students = this.getStudents();
        if (!students.isEmpty()) {
            for (Student s : students) {
                if (s.getCpf().equals(student.getCpf())) {
                    return true;
                }
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

    public void deleteStudent(StudentManagementService sms) {
        var enrollments = new EnrollmentManagementService("Matriculas.json");
        System.out.println("Digite o cpf do estudante que deseja apagar: ");
        String cpf = sc.nextLine();
        int i = 0;
        boolean studentFound = false;
        for (Student student : sms.getStudents()) {
            i = i + 1;
            if (student.getCpf().equals(cpf)) {
                studentFound = true;
                System.out.println(student);
                System.out.println("Deseja realmente deletar o estudante acima? (S/N)");
                String choice = sc.nextLine().toUpperCase();
                if (choice.equals("S")) {
                    jsonFile.deleteJSON(student, i - 1);
                }
//                for (Enrollment ems : enrollments.getEnrollments()) {
//                    if (ems.getCpf().equals(student.getCpf())) {
//
//                    }
//                }
            }
        }
        if (!studentFound) {
            System.out.println("Estudante não cadastrado");
        }
    }
}
