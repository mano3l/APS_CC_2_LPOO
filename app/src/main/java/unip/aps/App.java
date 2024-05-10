package unip.aps;

import unip.aps.models.Enrollment;
import unip.aps.models.Programs;
import unip.aps.models.Student;
import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.ProgramManagementService;
import unip.aps.services.StudentManagementService;

public class App {
    public static void main(String[] args) {
        Student students = new Student();
        var sms = new StudentManagementService("./app/src/main/resources/Data/Estudantes.json");
        var ems = new EnrollmentManagementService("./app/src/main/resources/Data/Matriculas.json");
        var pms = new ProgramManagementService("./app/src/main/resources/Data/Cursos.json");

//        sms.getStudents();
        sms.registerStudents(new Student());
//        ems.enrollmentStudent(new Enrollment());
//        System.out.print(ems.getEnrollments());
//        pms.getPrograms();
//          pms.registerProgram(new Programs());

    }
}
