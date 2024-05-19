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

public class ChangeProgramNameScene implements Runnable {
    @Override
    public void run() {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {

            // Cria o cabe�alho da tela
            List<AttributedString> header = new ArrayList<>();
            header.add(new AttributedStringBuilder().append(applyStyleTo(" Editar nome do curso \n", Theme.BLACK, Theme.YELLOW)).toAttributedString());

            var prompt = new ConsolePrompt(terminal);
            var promptBuilder = prompt.getPromptBuilder();


            promptBuilder
                    .createInputPrompt()
                    .name("programName")
                    .message("Digite o nome do curso que deseja alterar: ").addPrompt();

            // Recebe os dados inseridos pelo usu�rio
            Map<String, PromptResultItemIF> result;

            try {
                result = prompt.prompt(header, promptBuilder.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var pms = new ProgramManagementService("Cursos.json");

            var writer = terminal.writer();

            var programObj = pms.getProgramByName(result.get("programName").getResult());

            if (!pms.isProgramRegistered(programObj)) {
                writer.println("Curso nao cadastrado!");
                Thread.sleep(2000);
            } else {
                var promptBuilders = prompt.getPromptBuilder();

                Map<String, PromptResultItemIF> results;

                promptBuilders
                        .createInputPrompt()
                        .name("newProgramName")
                        .message("Digite o novo nome do curso: ").addPrompt();

                try {
                    results = prompt.prompt(promptBuilders.build());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                pms.changeProgramName(result.get("programName").getResult(), results.get("newProgramName").getResult());
                writer.println("Curso alterado com sucesso!");
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
