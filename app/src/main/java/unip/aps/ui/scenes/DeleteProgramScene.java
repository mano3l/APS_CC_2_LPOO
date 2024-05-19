package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.services.ProgramManagementService;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.components.Theme;
import unip.aps.utils.DataFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class DeleteProgramScene implements Runnable {
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
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Deletar curso \n", Theme.BLACK, Theme.YELLOW)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();


        promptBuilder
                .createInputPrompt()
                .name("programName")
                .message("Digite o curso: ").addPrompt();

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

        if(!pms.isProgramRegistered(programObj)){
            writer.println("Curso nao cadastrado!");
        }else{
            pms.deleteProgram(result.get("programName").getResult());
            writer.println("Curso deletado com sucesso!");
        }
    }

}
