package unip.aps;

import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.scenes.MainMenu;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import unip.aps.models.Student;

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

        // var sortStudentList = new StudentManagementService("Estudantes.json");

        // List<Student> listaSexo = new ArrayList<>();
        // List<Student> listaOrdenadaAlfabeto = new ArrayList<>();
        // List<Student> listaOrdenadaIdade = new ArrayList<>();

        // listaOrdenadaAlfabeto = sortStudentList.sortStudentsByAlphabeticalOrder();
        // listaOrdenadaIdade = sortStudentList.sortStudentsByAge();
        // listaSexo = sortStudentList.searchStudentsBySex("Feminino");

        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("===================== SEXO     ===============================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println(listaSexo);
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("======================= Ordenada Alfabeto  ===================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println(listaOrdenadaAlfabeto);
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("======================= Ordenada Idade     ===================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println("==============================================================");
        // System.out.println(listaOrdenadaIdade);
    }
}
