package unip.aps.services;

// import unip.aps.models.Enrollment;
import unip.aps.models.Program;
import unip.aps.utils.JSONUtility;

// import java.util.ArrayList;
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

    public boolean registerProgram(Program programs) {
        if (!isProgramRegistered(programs)) {
            this.jsonFile.appendToJSON(programs);
            return true;
        }
        return false;
    }

    public boolean isProgramRegistered(Program programs) {
        if (this.getPrograms() == null || this.getPrograms().isEmpty()) {
            return false;
        }

        List<Program> program = this.getPrograms();
            for (Program p : program) {
                if (programs == null) {
                    return false;
                }
                if (p.getNomeDoPrograma().equals(programs.getNomeDoPrograma())) {
                    return true;
                }
            }
        return false;
    }

    public Program getProgramByName(String programName) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                return program;
            }
        }
        return null;
    }

    public void changeProgramName(String programName, String newName) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setNomeDoPrograma(newName);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramLevel(String programName, String newLevel) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setNivelDoPrograma(newLevel);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramDuration(String programName, String newDuration) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setDuracao(newDuration);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramSchedule(String programName, String newSchedule) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setHorario(newSchedule);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramDescription(String programName, String newDescription) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setDescricao(newDescription);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramEnrollment(String programName, String ra, boolean isEnrolled) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                if (isEnrolled) {
                    program.addRA(ra);
                } else {
                    program.removeRA(ra);
                }
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void deleteProgram(String programName) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                for (String ra : program.getMatriculados()) {
                    EnrollmentManagementService enrollmentManagementService = new EnrollmentManagementService("enrollments.json");
                    enrollmentManagementService.deleteEnrollmentByRA(ra);
                }
                programs.remove(program);
                this.jsonFile.updateJSON(programs);
                return;
            }
        }
    }
}
