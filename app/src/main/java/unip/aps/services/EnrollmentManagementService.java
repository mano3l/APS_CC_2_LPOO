package unip.aps.services;

import unip.aps.models.Enrollment;
import unip.aps.models.Programs;
import unip.aps.models.Student;
import unip.aps.utils.DataFormatter;
import unip.aps.utils.JSONUtility;


import java.util.List;
import java.util.Scanner;

public class EnrollmentManagementService {
    private final JSONUtility<Enrollment> jsonFile;
    Scanner sc = new Scanner(System.in);

    public EnrollmentManagementService(String path) {
        this.jsonFile = new JSONUtility<>(path, Enrollment.class);
    }

    public List<Enrollment> getEnrollments() {
        return this.jsonFile.parseJSON();
    }

    public String generateEmail(Student students) {
        String name = students.getNome().toLowerCase();
        String lastName = students.getSobrenome().toLowerCase();
        String baseMail = name + "." + lastName;

        String formattedEmail = name + "." + lastName + "@unip.br";
        int i = 1;
        for (Enrollment enrollment : getEnrollments()) {
            if (enrollment.getEmail().equals(formattedEmail)) {
                formattedEmail = String.format("%s%d@unip.br", baseMail, i);
                i++;
            }
        }
        return formattedEmail;
    }

    private String genRA() {
        int rN = (int) (Math.random() * 9000) + 1000;
        String randomNumber = Integer.toString(rN);
        String formattedRa = "RA" + randomNumber;
        for (Enrollment existingEnrollment : getEnrollments()) {
            if (existingEnrollment.getRa().equals(formattedRa)) {
                return genRA();
            }
        }
        return formattedRa;
    }

    public void enrollmentStudent(Enrollment enrollment) {
        var pms = new ProgramManagementService("Cursos.json");
        var sms = new StudentManagementService("Estudantes.json");
        var ems = new EnrollmentManagementService("Matriculas.json");
        System.out.println("Digite o CPF do Aluno que deseja matricular: ");
        String cpf = sc.nextLine();
        boolean studentFound = true;
        boolean studentExists = true;
        for (Enrollment e : ems.getEnrollments()) {
            if (e.getCpf().equals(cpf)) {
                studentFound = false;
                System.out.println("Estudante já matriculado");
            }
        }
        if (studentFound) {
            for (Student student : sms.getStudents()) {
                if (student.getCpf().equals(cpf)) {
                    studentExists = false;
                    System.out.println(student);
                    System.out.println("Deseja realmente Cadastrar o estudante acima? (S/N): ");
                    String choice = sc.nextLine().toUpperCase();
                    if (choice.equals("S")) {
                        DataFormatter df = new DataFormatter();
                        System.out.print("Digite o nome do programa que deseja matricular o aluno: ");
                        String programName = sc.nextLine();
                        for (Programs programs : pms.getPrograms()) {
                            if (programs.getNomeDoPrograma().equals(programName)) {
                                enrollment.setNomeDoPrograma(programs.getNomeDoPrograma());
                                enrollment.setCpf(student.getCpf());
                                enrollment.setRa(student.getCpf());
                                enrollment.setEmail(ems.generateEmail(student));
                                enrollment.setDataMatricula(df.setDate());
                                System.out.println("MATRICULA REALIZADA!");
                                System.out.println("Gerando RA.....");
                                enrollment.setRa(genRA());
                                pms.appendStudent(enrollment.getRa(), enrollment.getNomeDoPrograma());
                                this.jsonFile.appendToJSON(enrollment);
                            }

                        }

                    }

                }

            }
            if (studentExists) {
                System.out.println("Estudante não existe!");
            }
        }

    }
    public void deleteEnrollment(String cpf) {
        System.out.println("Digite o ra da matricula que deseja apagar: ");
        String ra = sc.nextLine();
        int i = 0;
        boolean studentFound = false;
        for (Enrollment enrollment : getEnrollments()) {
            i = i + 1;
            if (enrollment.getRa().equals(ra)) {
                studentFound = true;
                System.out.println(enrollment);
                System.out.println("Deseja realmente deletar a matricula acima? (S/N)");
                String choice = sc.nextLine().toUpperCase();
                if (choice.equals("S")) {
                    jsonFile.deleteJSON(enrollment, i - 1);
                }
            }
        }
        if (!studentFound) {
            System.out.println("Estudante não matriculado");
        }
    }
}
