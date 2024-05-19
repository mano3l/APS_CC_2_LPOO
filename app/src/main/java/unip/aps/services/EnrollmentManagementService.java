package unip.aps.services;

import unip.aps.models.Enrollment;
import unip.aps.models.Student;
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

    public boolean registerEnrollment(Enrollment enrollment) {
        if (!isEnrollmentRegistered(enrollment)) {
            this.jsonFile.appendToJSON(enrollment);
            return true;
        }
        return false;
    }

    public boolean isEnrollmentRegistered(Enrollment enrollment) {
        if (this.getEnrollments() == null || this.getEnrollments().isEmpty()) {
            return false;
        }

        List<Enrollment> enrollments = this.getEnrollments();
        for (Enrollment e : enrollments) {
            if (e.getRa().equals(enrollment.getRa())) {
                return true;
            }
        }
        return false;
    }

    public Enrollment getEnrollmentByRA(String ra) {
        List<Enrollment> enrollments = this.getEnrollments();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getRa().equals(ra)) {
                return enrollment;
            }
        }
        return null;
    }

    public void changeStudentProgram(String ra, String newProgram) {
        List<Enrollment> enrollments = this.getEnrollments();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getRa().equals(ra)) {
                enrollment.setCodigoDoPrograma(newProgram);
                // REMOVER O RA DO MATRICULADOS [] DO PROGRAMA ANTIGO E ADICIONAR NO NOVO MATRICULADOS [] DO PROGRAMAS
                this.jsonFile.updateJSON(enrollment, enrollments.indexOf(enrollment));
            }
        }
    }


    public void deleteEnrollmentByRA(String ra) {
        List<Enrollment> enrollments = this.getEnrollments();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getRa().equals(ra)) {
                enrollments.remove(enrollment);
                this.jsonFile.updateJSON(enrollments);
                return;
            }
        }
    }

    public void deleteEnrollmentByCPF(String cpf) {
        List<Enrollment> enrollments = this.getEnrollments();
        int i = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCpf().equals(cpf)) {
                this.jsonFile.deleteJSON(enrollment, i);
            }
            i++;
        }
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

    public String genRA() {
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

}
