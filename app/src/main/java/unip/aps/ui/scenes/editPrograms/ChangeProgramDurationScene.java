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

public class ChangeProgramDurationScene implements Runnable{
    @Override
    public void run() {
        Terminal terminal;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Cria o cabe�alho da tela
        List<AttributedString> header = new ArrayList<>();
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Editar duracao do curso \n", Theme.BLACK, Theme.MAGENTA)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();


        promptBuilder
                .createInputPrompt()
                .name("programCode")
                .message("Digite o código do curso que deseja alterar: ").addPrompt();

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

        if(!pms.isProgramRegistered(programObj)){
            writer.println("Curso nao cadastrado!");
        }else{
            var promptBuilders = prompt.getPromptBuilder();

            Map<String, PromptResultItemIF> results;

            promptBuilders
                    .createInputPrompt()
                    .name("newProgramDuration")
                    .message("Digite a nova duracao do curso: ").addPrompt();

            try {
                results = prompt.prompt(promptBuilders.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            pms.changeProgramDuration(result.get("programCode").getResult(), results.get("newProgramDuration").getResult());
            writer.println("Curso alterado com sucesso!");
        }
    }
}
