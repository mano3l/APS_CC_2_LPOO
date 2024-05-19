package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Student;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class ListStudentScene implements  Runnable {

    @Override
    public void run() {
        Terminal terminal;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
            // Cria o cabecalho da tela
            List<AttributedString> header = new ArrayList<>();
            header.add(new AttributedStringBuilder().append(applyStyleTo(" Lista de Estudantes \n", Theme.BLACK, Theme.MAGENTA)).toAttributedString());

            var prompt = new ConsolePrompt(terminal);
            var promptBuilder = prompt.getPromptBuilder();
            var writer = terminal.writer();
            var sms = new StudentManagementService("Estudantes.json");

            for (Student s : sms.getStudents()) {
                writer.println(s.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}