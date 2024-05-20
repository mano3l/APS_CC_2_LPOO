package unip.aps.ui.scenes.editPrograms;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.services.ProgramManagementService;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class ChangeProgramScheduleScene implements Runnable {
    @Override
    public void run() {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {

            // Cria o cabe�alho da tela
            List<AttributedString> header = new ArrayList<>();
            header.add(new AttributedStringBuilder().append(applyStyleTo(" Editar horario do curso \n", Theme.BLACK, Theme.MAGENTA)).toAttributedString());

            var prompt = new ConsolePrompt(terminal);
            var promptBuilder = prompt.getPromptBuilder();


        promptBuilder
                .createInputPrompt()
                .name("programCode")
                .message("Digite o codigo do curso que deseja alterar: ").addPrompt();

            // Recebe os dados inseridos pelo usu�rio
            Map<String, PromptResultItemIF> result;

            try {
                result = prompt.prompt(header, promptBuilder.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var pms = new ProgramManagementService("Cursos.json");

            var writer = terminal.writer();

        var programObj = pms.getProgramByCode(result.get("programCode").getResult());

            if (!pms.isProgramRegistered(programObj)) {
                writer.println("Curso nao cadastrado!");
                Thread.sleep(2000);
            } else {
                var promptBuilders = prompt.getPromptBuilder();

                Map<String, PromptResultItemIF> results;

                promptBuilders
                        .createInputPrompt()
                        .name("newProgramSchedule")
                        .message("Digite o novo periodo do curso: ").addPrompt();

                try {
                    results = prompt.prompt(promptBuilders.build());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                pms.changeProgramSchedule(result.get("programCode").getResult(), results.get("newProgramSchedule").getResult());
                writer.println("Curso alterado com sucesso!");
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
