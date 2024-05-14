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
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class RegisterStudentScene implements Runnable {
    @Override
    public void run() {
        Terminal terminal;
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Cria o cabeçalho da tela
        List<AttributedString> header = new ArrayList<>();
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Registro de estudante \n", Theme.BLACK, Theme.YELLOW)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();


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

        // Recebe os dados inseridos pelo usuário
        Map<String, PromptResultItemIF> result;
        try {
            result = prompt.prompt(header, promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var student = createStudent(result);

        var sms = new StudentManagementService("Estudantes.json");

        var writer = terminal.writer();
        if (sms.isRegistered(student)) {
            writer.println("Estudante já cadastrado.");
            return;
        }

        if (sms.registerStudent(student)) {
            writer.println("Estudante cadastrado com sucesso.");
            return;
        }

        writer.println("Erro ao cadastrar estudante.");
    }

    public Student createStudent(Map<String, PromptResultItemIF> result) {
        return new Student(
                result.get("cpf").getResult(),
                result.get("firstName").getResult(),
                result.get("lastName").getResult(),
                result.get("address").getResult(),
                Integer.parseInt(result.get("age").getResult()),
                result.get("sex").getResult(),
                result.get("cellphone").getResult()
        );
    }

}
