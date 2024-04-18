package unip.aps.services;

import unip.aps.models.Student;
import unip.aps.utils.JSONUtility;

import java.util.List;

public class StudentManagementService {
    private final JSONUtility<Student> jsonFile;

    public StudentManagementService(String path) {
        this.jsonFile = new JSONUtility<>(path, Student.class);
    }

    public void registerStudent(Student student) {
        this.jsonFile.appendToJSON(student);
    }

    public List<Student> getStudents() {
        return this.jsonFile.parseJSON();
    }

    public void updateStudentName(Integer id, String name) {
        List<Student> studentList = this.getStudents();
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                student.setName(name);
                this.jsonFile.updateJSON(studentList);
                return;
            }
        }
    }
}


