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

public class DeleteStudentScene implements Runnable {
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
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Deletar estudante \n", Theme.BLACK, Theme.YELLOW)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();


        promptBuilder
                .createInputPrompt()
                .name("cpf")
                .message("Digite o cpf do aluno: ").addPrompt();

        // Recebe os dados inseridos pelo usu�rio
        Map<String, PromptResultItemIF> result;
        try {
            result = prompt.prompt(header, promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var cpf = DataFormatter.formatCpf(result.get("cpf").getResult());

        var sms = new StudentManagementService("Estudantes.json");

        var writer = terminal.writer();

        var studentObj = sms.getStudentByCPF(cpf);

        if(!sms.isStudentRegistered(studentObj)){
            writer.println("Estudante nao cadastrado!");
        }else{
            sms.deleteStudent(cpf);
            writer.println("Estudante deletado com sucesso!");
        }
    }


}
