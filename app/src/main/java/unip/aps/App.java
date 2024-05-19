package unip.aps;

import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.scenes.MainMenu;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
//        Student students = new Student();
//        var ems = new EnrollmentManagementService("Matriculas.json");
//        var pms = new ProgramManagementService("Cursos.json");
//        sms.getStudents();
//        sms.registerStudents(new Student());
//        ems.enrollmentStudent(new Enrollment());
//        System.out.print(ems.getEnrollments());
//        pms.getPrograms();
//        pms.registerProgram(new Programs());
//        sms.deleteStudent(sms);
//        ems.deleteEnrollment(ems);

        // ========================= TESTE UI ==========================

        var menu = new MainMenu();
        menu.init();


    }
}
