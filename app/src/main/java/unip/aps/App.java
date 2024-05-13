package unip.aps;

import unip.aps.ui.scenes.MainMenu;

public class App {
    public static void main(String[] args) {
//        Student students = new Student();
//        var sms = new StudentManagementService("Estudantes.json");
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
