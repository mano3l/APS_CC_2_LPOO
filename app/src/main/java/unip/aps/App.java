package unip.aps;

import unip.aps.models.Student;
import unip.aps.services.StudentManagementService;

public class App {
    public static void main(String[] args) {
        Student students = new Student();
        var sms = new StudentManagementService("./app/src/main/resources/Data/Estudantes.json");

        sms.registerStudents(new Student());

//        System.out.print(sms.getStudents());


    }

}
