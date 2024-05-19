package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Enrollment;
import unip.aps.models.Student;
import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.components.Theme;
import unip.aps.utils.DataFormatter;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class RegisterEnrollmentScene implements Runnable {
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
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Matricular estudante \n", Theme.BLACK, Theme.RED)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();

        promptBuilder
                .createInputPrompt()
                .name("cpf")
                .message("Digite o cpf do estudante que deseja matricular: ").addPrompt()
                .createInputPrompt()
                .name("programName")
                .message("Digite o nome do curso que deseja matricular o estudante: ").addPrompt()
                .createInputPrompt();

        var ems = new EnrollmentManagementService("Matriculas.json");
        var sms = new StudentManagementService("Estudantes.json");

        // Recebe os dados inseridos pelo usu?rio
        Map<String, PromptResultItemIF> result;
        try {
            result = prompt.prompt(header, promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var enrollment = createEnrollment(result);

        var writer = terminal.writer();
        if (ems.isEnrollmentRegistered(enrollment)) {
            writer.println("Estudante ja matriculado!.");
            return;
        }

        if (ems.registerEnrollment(enrollment)) {
            writer.println("Estudante matriculado com sucesso.");
        }

    }

    public Enrollment createEnrollment(Map<String, PromptResultItemIF> result) {
        var ems = new EnrollmentManagementService("Matriculas.json");
        var sms = new StudentManagementService("Estudantes.json");
        Student student = sms.getStudentByCPF(result.get("cpf").getResult());
        return new Enrollment (
            ems.genRA(),
            result.get("cpf").getResult(),
            ems.generateEmail(student),
            result.get("programName").getResult(),
            DataFormatter.setDate()
        );
    }
}
