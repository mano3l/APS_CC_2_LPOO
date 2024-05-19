package unip.aps.ui.scenes.editEnrollments;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.ProgramManagementService;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class ChangeStudentProgram implements Runnable {
    @Override
    public void run() {
        Terminal terminal;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Cria o cabe?alho da tela
        List<AttributedString> header = new ArrayList<>();
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Editar nome do curso \n", Theme.BLACK, Theme.YELLOW)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();


        promptBuilder
                .createInputPrompt()
                .name("ra")
                .message("Digite o ra da matricula que deseja alterar: ").addPrompt();

        // Recebe os dados inseridos pelo usu?rio
        Map<String, PromptResultItemIF> result;

        try {
            result = prompt.prompt(header, promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var ems = new EnrollmentManagementService("Matriculas.json");
        var pms = new ProgramManagementService("Cursos.json");

        var writer = terminal.writer();

        var enrollmentObj = ems.getEnrollmentByRA(result.get("ra").getResult());

        if(enrollmentObj == null){
            writer.println("Estudante não matriculado!");
        }else{
            var promptBuilders = prompt.getPromptBuilder();

            Map<String, PromptResultItemIF> results;

            promptBuilders
                    .createInputPrompt()
                    .name("newProgramName")
                    .message("Digite o nome do novo curso: ").addPrompt();

            try {
                results = prompt.prompt(promptBuilders.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            var programObj = pms.getProgramByName(results.get("newProgramName").getResult());
            if (!pms.isProgramRegistered(programObj)) {
                writer.println("Curso não disponível!");
            } else {
                ems.changeStudentProgram(result.get("ra").getResult(), results.get("newProgramName").getResult());
                writer.println("Estudante mudou de curso!");
            }
        }
    }
}
