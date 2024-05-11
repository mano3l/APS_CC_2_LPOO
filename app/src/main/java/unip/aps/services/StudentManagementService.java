package unip.aps.services;

import unip.aps.models.Enrollment;
import unip.aps.models.Student;
import unip.aps.utils.DataFormatter;
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

    public void registerStudents(Student student) {
        Boolean flag = true;
        DataFormatter dF = new DataFormatter();
        System.out.print("Digite o CPF para iniciar o registro: ");
        String cpf = sc.nextLine();
        for (Student s : this.getStudents()) {
            student.setCpf(dF.formatCpf(cpf));
            if(s.getCpf().equals(student.getCpf())) {
                flag = false;
                System.out.println("\tALUNO JA CADASTRADO");
                break;
            }
        }
        if (flag.equals(true)) {
            System.out.println("\t||| Registro de novo estudante ||| ");
            System.out.print("Digite o primeiro nome: ");
            student.setNome(sc.nextLine());
            System.out.print("Digite o  sobrenome: ");
            student.setSobrenome(sc.nextLine());
            System.out.print("Digite o seu endereço: ");
            student.setEndereco(sc.nextLine());
            student.setCpf(dF.formatCpf(cpf));
            System.out.print("Digite seu sexo: ");
            student.setSexo(sc.nextLine());
            System.out.print("Digite seu telefone: ");
            String telefone = sc.nextLine();
            student.setTelefone(dF.formatPhoneNumber(telefone));
            System.out.print("Digite a idade: ");
            student.setIdade(sc.nextInt());
            this.jsonFile.appendToJSON(student);
        }

    }

    public void changeStudent(StudentManagementService sms) {
        System.out.println("Digite o nome do estudante que deseja realizar alterações: ");
        String nome = sc.nextLine();
        int i = 0;
        boolean studentFound = false;
        for (Student student : sms.getStudents() ) {
            i = i + 1;
            if (student.getNome().equals(nome)) {
                studentFound = true;
                System.out.println(i);
                System.out.println(student);
                System.out.println("Qual das informações você deseja alterar?");
                String choice = sc.nextLine();
                switch(choice) {
                    case "1":
                        System.out.print("O RA não pode ser alterado!");
                        break;
                    case "2":
                        System.out.print("Qual o novo nome? ");
                        String newNome = sc.nextLine();
                        student.setNome(newNome);
                        jsonFile.updateJSON(student,i - 1);
                        break;
                    case "3":
                        System.out.print("Digite a idade: ");
                        int newIdade = sc.nextInt();
                        student.setIdade(newIdade);
                        jsonFile.updateJSON(student,i - 1);
                        break;
                    case "4":
                        System.out.print("Qual o novo sexo? ");
                        String newSexo = sc.nextLine();
                        student.setSexo(newSexo);
                        jsonFile.updateJSON(student,i - 1);
                        break;
                    case "5":
                        System.out.print("Qual o novo telefone? ");
                        String newTelefone = sc.nextLine();
                        student.setTelefone(newTelefone);
                        jsonFile.updateJSON(student,i - 1);
                        break;
                    default:
                        System.out.println("Digite 1-5 !");
                }
            }
        }
        if (!studentFound) {
            System.out.println("Estudante não matriculado");
        }
    }

    public void deleteStudent(StudentManagementService sms) {
        var enrollments = new EnrollmentManagementService("./app/src/main/resources/Data/Matriculas.json");
        System.out.println("Digite o cpf do estudante que deseja apagar: ");
        String cpf = sc.nextLine();
        int i = 0;
        boolean studentFound = false;
        for (Student student : sms.getStudents() ) {
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



