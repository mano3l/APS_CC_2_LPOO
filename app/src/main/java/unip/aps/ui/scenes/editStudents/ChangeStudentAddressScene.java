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

public class ChangeStudentAddressScene implements Runnable {
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
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Editar endereço do aluno \n", Theme.BLACK, Theme.WHITE)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();


        promptBuilder
                .createInputPrompt()
                .name("cpf")
                .message("Digite o CPF do aluno que deseja alterar: ").addPrompt();

        // Recebe os dados inseridos pelo usu?rio
        Map<String, PromptResultItemIF> result;

        try {
            result = prompt.prompt(header, promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var sms = new StudentManagementService("Estudantes.json");

        var writer = terminal.writer();

        var studentObj = sms.getStudentByCPF(result.get("cpf").getResult());

        if(!sms.isStudentRegistered(studentObj)){
            writer.println("Estudante nao cadastrado!");
        }else{
            var promptBuilders = prompt.getPromptBuilder();

            Map<String, PromptResultItemIF> results;

            promptBuilders
                .createInputPrompt()
                .name("newStudentPhone")
                .message("Digite o novo telefone do estudante: ").addPrompt();

            try {
                results = prompt.prompt(promptBuilders.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String studentPhone = results.get("newStudentPhone").getResult();
            String cpf = result.get("cpf").getResult();

            sms.changeStudentPhone(cpf, studentPhone);
            writer.println("Curso alterado com sucesso!");
        }
    }
}
