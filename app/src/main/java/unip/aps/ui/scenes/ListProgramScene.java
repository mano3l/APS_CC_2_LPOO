package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Program;
import unip.aps.services.ProgramManagementService;
import unip.aps.ui.components.Theme;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class ListProgramScene implements  Runnable {

    @Override
    public void run() {
        Terminal terminal;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Cria o cabecalho da tela
        List<AttributedString> header = new ArrayList<>();
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Lista de Programas \n", Theme.BLACK, Theme.MAGENTA)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();
        var writer = terminal.writer();
        var pms = new ProgramManagementService("Cursos.json");

        for (Program p : pms.getPrograms()) {
            writer.println(p.toString());
        }

    }
}