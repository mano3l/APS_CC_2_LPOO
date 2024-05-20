package unip.aps.services;

import unip.aps.models.Enrollment;
// import unip.aps.models.Enrollment;
import unip.aps.models.Program;
import unip.aps.utils.JSONUtility;

// import java.util.ArrayList;
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
            if (programs == null || p == null) {
                return false;
            }
            String codigoDoPrograma = p.getCodigoDoPrograma();
            if (codigoDoPrograma != null && codigoDoPrograma.equals(programs.getCodigoDoPrograma())) {
                return true;
            }
        }
        return false;
    }

    // public Program getProgramByName(String programName) {
    //     List<Program> programs = this.getPrograms();
    //     for (Program program : programs) {
    //         if (program.getNomeDoPrograma().equals(programName)) {
    //             return program;
    //         }
    //     }
    //     return null;
    // }

    public Program getProgramByCode(String programCOD) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getCodigoDoPrograma().equals(programCOD)) {
                return program;
            }
        }
        return null;
    }

    public void changeProgramName(String programCode, String newName) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getCodigoDoPrograma().equals(programCode)) {
                program.setNomeDoPrograma(newName);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramLevel(String programCode, String newLevel) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getCodigoDoPrograma().equals(programCode)) {
                program.setNivelDoPrograma(newLevel);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramDuration(String programCode, String newDuration) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getCodigoDoPrograma().equals(programCode)) {
                program.setDuracao(newDuration);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramSchedule(String programCode, String newSchedule) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getCodigoDoPrograma().equals(programCode)) {
                program.setHorario(newSchedule);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramDescription(String programCode, String newDescription) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getCodigoDoPrograma().equals(programCode)) {
                program.setDescricao(newDescription);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void changeProgramEnrollment(String programCode, String ra, boolean isEnrolled) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getCodigoDoPrograma().equals(programCode)) {
                if (isEnrolled) {
                    program.addRA(ra);
                } else {
                    program.removeRA(ra);
                }
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public void deleteProgram(String programCode) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getCodigoDoPrograma().equals(programCode)) {
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

    public String genCode() {
        int rN = (int) (Math.random() * 9000) + 1000;
        String randomNumber = Integer.toString(rN);
        String formattedCode = "COD" + randomNumber;

        for (Program existingProgram : getPrograms()) {
            if (existingProgram == null) {
                return formattedCode;
            }
            if (existingProgram.getCodigoDoPrograma().equals(formattedCode)) {
                return genCode();
            }
        }
        return formattedCode;
    }

    public void deleteRA(String ra) {
        List<Program> programs = this.getPrograms();
        for (Program program : programs) {
            if (program.getMatriculados().contains(ra)) {
                program.removeRA(ra);
                this.jsonFile.updateJSON(program, programs.indexOf(program));
            }
        }
    }

    public List<String> getProgramCodes() {
        List<String> programCodes = new ArrayList<>();
        for (Program program : getPrograms()) {
            programCodes.add(program.getCodigoDoPrograma());
        }
        return programCodes;
    }
}
