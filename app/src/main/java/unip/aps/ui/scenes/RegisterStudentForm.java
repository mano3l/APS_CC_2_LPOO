package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Student;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.components.Theme;
import unip.aps.utils.DataFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class RegisterStudentForm implements Runnable {
    @Override
    public void run() {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {

            // Cria o cabe?alho da tela
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

            // Recebe os dados inseridos pelo usu?rio
            Map<String, PromptResultItemIF> result;
            try {
                result = prompt.prompt(header, promptBuilder.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var student = createStudent(result);

            var sms = new StudentManagementService("Estudantes.json");

            var writer = terminal.writer();
            if (sms.isStudentRegistered(student)) {
                writer.println("Estudante já cadastrado.");
                Thread.sleep(2000);
                return;
            }

            if (sms.registerStudent(student)) {
                writer.println("Estudante cadastrado com sucesso.");
                Thread.sleep(2000);
                return;
            }

            writer.println("Erro ao cadastrar estudante.");
            Thread.sleep(2000);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Student createStudent(Map<String, PromptResultItemIF> result) {
        return new Student(
                DataFormatter.formatCpf(result.get("cpf").getResult()),
                result.get("firstName").getResult(),
                result.get("lastName").getResult(),
                result.get("address").getResult(),
                Integer.parseInt(result.get("age").getResult()),
                result.get("sex").getResult(),
                DataFormatter.formatPhoneNumber(result.get("cellphone").getResult())
        );
    }

}
