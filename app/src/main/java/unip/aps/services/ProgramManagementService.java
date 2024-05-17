package unip.aps.services;

import unip.aps.models.Enrollment;
import unip.aps.models.Program;
import unip.aps.models.Student;
import unip.aps.utils.JSONUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramManagementService {
    private final JSONUtility<Program> jsonFile;
    Scanner sc = new Scanner(System.in);

    public ProgramManagementService(String path) {
        this.jsonFile = new JSONUtility<>(path, Program.class);
    }

    public List<Program> getPrograms() {
        return this.jsonFile.parseJSON();
    }


    public boolean registerPrograms(Program program) {
        if (!isRegistered(program)){
            this.jsonFile.appendToJSON(program);
            return true;
        }
        return false;

    }

    public boolean isRegistered(Program programs) {
        if (this.getPrograms() == null || this.getPrograms().isEmpty()) {
            return false;
        }

        List<Program> programList = this.getPrograms();
        for (Program s : programList) {
            if (s.getNomeDoPrograma().equals(programs.getNomeDoPrograma())) {
                return true;
            }
        }
        return false;
    }

    public void appendStudent(String ra, String programName) {
        int i = 0;
        for (Program program : getPrograms()) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.addRA(ra);
                this.jsonFile.updateJSON(program, i);
            }
            i = i + 1;
        }
    }

}

