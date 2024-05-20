package unip.aps.ui.scenes.editStudents;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class ChangeStudentAgeScene implements Runnable {
    @Override
    public void run() {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {

            // Cria o cabe�alho da tela
            List<AttributedString> header = new ArrayList<>();
            header.add(new AttributedStringBuilder().append(applyStyleTo(" Editar idade do aluno \n", Theme.BLACK, Theme.WHITE)).toAttributedString());

            var prompt = new ConsolePrompt(terminal);
            var promptBuilder = prompt.getPromptBuilder();


            promptBuilder
                    .createInputPrompt()
                    .name("cpf")
                    .message("Digite o CPF do aluno que deseja alterar: ").addPrompt();

            // Recebe os dados inseridos pelo usu�rio
            Map<String, PromptResultItemIF> result;

            try {
                result = prompt.prompt(header, promptBuilder.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var sms = new StudentManagementService("Estudantes.json");

            var writer = terminal.writer();

            var studentObj = sms.getStudentByCPF(result.get("cpf").getResult());

            if (!sms.isStudentRegistered(studentObj)) {
                writer.println("Estudante nao cadastrado!");
                Thread.sleep(2000);
            } else {
                var promptBuilders = prompt.getPromptBuilder();

                Map<String, PromptResultItemIF> results;

                promptBuilders
                        .createInputPrompt()
                        .name("newStudentAge")
                        .message("Digite a nova idade do estudante: ").addPrompt();

                try {
                    results = prompt.prompt(promptBuilders.build());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String studentPhoneStr = results.get("newStudentAge").getResult();
                int studentPhone;
                try {
                    studentPhone = Integer.parseInt(studentPhoneStr);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("A Idade deve ser um numero inteiro! " + studentPhoneStr, e);
                }
                String cpf = result.get("cpf").getResult();

                sms.changeStudentAge(cpf, studentPhone);
                writer.println("Curso alterado com sucesso!");
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
