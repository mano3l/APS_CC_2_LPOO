package unip.aps;

import unip.aps.models.Student;
import unip.aps.services.StudentManagementService;

public class App {
    public static void main(String[] args) {
        Student students = new Student();
        students.setRa();
        var sms = new StudentManagementService("./app/src/main/resources/Data/Estudantes.json");
        sms.deleteStudent(sms);
    }
}
