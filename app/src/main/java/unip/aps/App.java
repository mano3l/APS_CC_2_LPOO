package unip.aps;

import unip.aps.models.Student;
import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.ProgramManagementService;
import unip.aps.services.StudentManagementService;

public class App {
    public static void main(String[] args) {
        Student students = new Student();
        var sms = new StudentManagementService("Estudantes.json");
        var ems = new EnrollmentManagementService("Matriculas.json");
        var pms = new ProgramManagementService("Cursos.json");
//        sms.getStudents();
//        sms.registerStudents(new Student());
//        ems.enrollmentStudent(new Enrollment());
//        System.out.print(ems.getEnrollments());
//        pms.getPrograms();
//        pms.registerProgram(new Programs());
//        sms.deleteStudent(sms);
//        ems.deleteEnrollment(ems);
    }
}
