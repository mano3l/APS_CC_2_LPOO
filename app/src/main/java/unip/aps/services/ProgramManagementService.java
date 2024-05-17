package unip.aps.services;

// import unip.aps.models.Enrollment;
import unip.aps.models.Programs;
import unip.aps.utils.JSONUtility;

// import java.util.ArrayList;
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

    public boolean registerProgram(Programs program) {
        if (!isProgramRegistered(program)) {
            this.jsonFile.appendToJSON(program);
            return true;
        }
        return false;
    }

    public boolean isProgramRegistered(Programs program) {
        if (this.getPrograms() == null || this.getPrograms().isEmpty()) {
            return false;
        }

        List<Programs> programs = this.getPrograms();
        for (Programs p : programs) {
            if (p.getNomeDoPrograma().equals(program.getNomeDoPrograma())) {
                return true;
            }
        }
        return false;
    }

    public Programs getProgramByName(String programName) {
        List<Programs> programs = this.getPrograms();
        for (Programs program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                return program;
            }
        }
        return null;
    }

    public void changeProgramName(String programName, String newName) {
        List<Programs> programs = this.getPrograms();
        for (Programs program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setNomeDoPrograma(newName);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramLevel(String programName, String newLevel) {
        List<Programs> programs = this.getPrograms();
        for (Programs program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setNivelDoPrograma(newLevel);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramDuration(String programName, String newDuration) {
        List<Programs> programs = this.getPrograms();
        for (Programs program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setDuracao(newDuration);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramSchedule(String programName, String newSchedule) {
        List<Programs> programs = this.getPrograms();
        for (Programs program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setHorario(newSchedule);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramDescription(String programName, String newDescription) {
        List<Programs> programs = this.getPrograms();
        for (Programs program : programs) {
            if (program.getNomeDoPrograma().equals(programName)) {
                program.setDescricao(newDescription);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramEnrollment(String programName, String ra, boolean isEnrolled) {
        List<Programs> programs = this.getPrograms();
        for (Programs program : programs) {
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
        List<Programs> programs = this.getPrograms();
        for (Programs program : programs) {
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