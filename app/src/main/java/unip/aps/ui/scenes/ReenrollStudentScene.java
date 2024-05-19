package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class ReenrollStudentScene implements Runnable{
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
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Rematricular aluno \n", Theme.BLACK, Theme.YELLOW)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();


        promptBuilder
                .createInputPrompt()
                .name("ra")
                .message("Digite o RA do aluno que deseja rematricular: ").addPrompt();

        // Recebe os dados inseridos pelo usu�rio
        Map<String, PromptResultItemIF> result;

        try {
            result = prompt.prompt(header, promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var ems = new EnrollmentManagementService("Matriculas.json");

        var writer = terminal.writer();

        var enrollmentObj = ems.getEnrollmentByRA(result.get("ra").getResult());

        if(!ems.isEnrollmentRegistered(enrollmentObj)){
            writer.println("Estudante nao cadastrado!");
        }else{
            var promptBuilders = prompt.getPromptBuilder();

            Map<String, PromptResultItemIF> results;

            promptBuilders
                    .createInputPrompt()
                    .name("newEnrollment")
                    .message("Digite o curso que deseja rematricular o aluno: ").addPrompt();
//              COLOCAR TRATAMENTO DE ERRO PARA CHECAR SE O CURSO PASSADO ESTA NO JSON

            try {
                results = prompt.prompt(promptBuilders.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String newEnrollment = results.get("newEnrollment").getResult();
            String ra = result.get("ra").getResult();

            ems.changeStudentProgram(ra, newEnrollment);
            writer.println("Matricula alterada com sucesso!");
        }
    }
}
