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

    public void registerStudents(Student student){
        System.out.println("Registro de novo estudante: ");
//
        System.out.print("Digite o nome completo do Estudante: ");
        student.setNome(sc.nextLine());
        System.out.print("Digite seu sexo: ");
        student.setSexo(sc.nextLine());
        System.out.print("Digite seu telefone: ");
        student.setTelefone(sc.nextLine());
        System.out.print("Digite a idade: ");
        student.setIdade(sc.nextInt());
        System.out.println("Gerando seu RA........");
        student.setRa();
        this.jsonFile.appendToJSON(student);
    }

}



