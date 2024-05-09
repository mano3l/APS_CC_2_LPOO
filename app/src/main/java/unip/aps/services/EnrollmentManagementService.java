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

    public List<Enrollment> getEnrollments() {return this.jsonFile.parseJSON();}

    public String generateEmail(Student students) {
        String name = students.getNome();
        String[] nameParts = name.split(" ");
        String formattedEmail = nameParts[0].toLowerCase() + "." + nameParts[1].toLowerCase() + "%s"+ "@unip.br" ;
        int i = 1;
        for (Enrollment enrollment : getEnrollments()) {
            if (enrollment.getEmail().equals(formattedEmail.formatted(""))) {
                i++;
                String newI = String.valueOf(i);
                System.out.println(formattedEmail);
                return formattedEmail.formatted(newI);
            }

        }
        return formattedEmail.formatted("");

    }


    public void enrollmentStudent(Enrollment enrollment) {
        var pms = new ProgramManagementService("./app/src/main/resources/Data/Cursos.json");
        var sms = new StudentManagementService("./app/src/main/resources/Data/Estudantes.json");
//        var ems = new EnrollmentManagementService("./app/src/main/resources/Data/Matriculas.json");
        System.out.println("Digite o RA do Aluno que deseja matricular: ");
        String ra = sc.nextLine();

//        boolean studentFound = false;
        for (Student student : sms.getStudents()) {
            if (student.getRa().equals(ra)) {
//                studentFound = true;
                System.out.println(student);
                System.out.println("Deseja realmente Cadastrar o estudante acima? (S/N): ");
                String choice = sc.nextLine();
                if (choice.equals("S")) {
                        DataFormatter df = new DataFormatter();
                        enrollment.setNome(student.getNome());
                        enrollment.setIdade(student.getIdade());
                        enrollment.setRa(student.getRa());
                        enrollment.setSexo(student.getSexo());
                        enrollment.setEndereco("Rua tal de tal, Campinas-SP");
                        enrollment.setEmail(generateEmail(student));
                        enrollment.setTelefone(student.getTelefone());
                        enrollment.setDataMatricula(df.setDate());
                        System.out.print("Digite o nome do programa que deseja matricular o aluno: ");
                        String programName = sc.nextLine();
                        for (Programs programs : pms.getPrograms()) {
                            if (programs.getNomeDoPrograma().equals(programName)) {
                                enrollment.setNomeDoPrograma(programs.getNomeDoPrograma());
                                this.jsonFile.appendToJSON(enrollment);

                            }


                        }

                    }

                }
            }
        }


    }