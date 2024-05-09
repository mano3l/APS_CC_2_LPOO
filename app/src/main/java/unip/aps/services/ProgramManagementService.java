package unip.aps.services;

import unip.aps.models.Programs;
import unip.aps.utils.JSONUtility;

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
        System.out.println("Please enter the name of the program you would like to register.");
        program.setNomeDoPrograma(sc.nextLine());
        program.setDescricao(sc.nextLine());
        program.setDuracao(sc.nextLine());
        program.setHorario(sc.nextLine());
        program.setNiveldoCurso(sc.nextLine());
        this.jsonFile.appendToJSON(program);
    }
}
