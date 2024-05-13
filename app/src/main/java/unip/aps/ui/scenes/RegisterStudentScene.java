package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.consoleui.prompt.builder.PromptBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Student;
import unip.aps.services.StudentManagementService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.jline.jansi.Ansi.ansi;

public class RegisterStudentScene implements Runnable {
    @Override
    public void run() {
        Terminal terminal = null;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ConsolePrompt prompt = new ConsolePrompt(terminal);
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        List<AttributedString> header = new ArrayList<>();
        header.add(new AttributedStringBuilder().append(ansi().bgBrightYellow().fgBlack().a(" Registro de estudante \n").reset().toString()).toAttributedString());

        promptBuilder
                .createInputPrompt()
                .name("firstName")
                .message("Digite o primeiro nome: ").addPrompt()
                .createInputPrompt()
                .name("lastName")
                .message("Digite o sobrenome: ").addPrompt()
                .createInputPrompt()
                .name("address")
                .message("Digite o endereço: ").addPrompt()
                .createInputPrompt()
                .name("cpf")
                .message("Digite o cpf: ").addPrompt()
                .createInputPrompt()
                .name("sex")
                .message("Digite o sexo: ").addPrompt()
                .createInputPrompt()
                .name("cellphone")
                .message("Digite o telefone: ").addPrompt()
                .createInputPrompt()
                .name("age")
                .message("Digite a idade: ").addPrompt();

        Map<String, PromptResultItemIF> result = null;
        try {
            result = prompt.prompt(header, promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Student student = new Student(
                result.get("cpf").getResult(),
                result.get("firstName").getResult(),
                result.get("lastName").getResult(),
                result.get("address").getResult(),
                Integer.parseInt(result.get("age").getResult()),
                result.get("sex").getResult(),
                result.get("cellphone").getResult()
        );

        StudentManagementService sms = new StudentManagementService("Estudantes.json");

        if (sms.isRegistered(student)) {
            terminal.writer().println("Estudante já cadastrado.");
            return;
        }

        if(sms.registerStudent(student)) {
            terminal.writer().println("Estudante cadastrado com sucesso.");
            return;
        }

        terminal.writer().println("Erro ao cadastrar estudante.");
    }
}
