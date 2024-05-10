package unip.aps.services;

import unip.aps.models.Enrollment;
import unip.aps.models.Programs;
import unip.aps.utils.JSONUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramManagementService {
    private final JSONUtility<Programs> jsonFile;
    Scanner sc = new Scanner(System.in);

    public ProgramManagementService(String path) {
        this.jsonFile = new JSONUtility<>(path, Programs.class);
    }

    public List<Programs> getPrograms() {
        return this.jsonFile.parseJSON();
    }

    public void registerProgram(Programs program) {
        System.out.println("\t||| Registro de Programa |||");
        System.out.print("Digite o nome do programa: ");
        program.setNomeDoPrograma(sc.nextLine());
        System.out.print("Digite uma Descriçāo para o programa: ");
        program.setDescricao(sc.nextLine());
        System.out.print("Digite a Duraçāo do programa: ");
        program.setDuracao(sc.nextLine());
        System.out.print("Digite o horario do programa: ");
        program.setHorario(sc.nextLine());
        System.out.print("Digite o nivel do programa: ");
        program.setNivelDoPrograma(sc.nextLine());
        this.jsonFile.appendToJSON(program);
    }

    public void appendStudent(String ra, String programName) {
            int i = 0;
            for (Programs program : getPrograms()) {
                if (program.getNomeDoPrograma().equals(programName)) {
                    program.addRA(ra);
                    this.jsonFile.updateJSON(program, i);
                }
                i = i + 1;
                }
            }

        }

