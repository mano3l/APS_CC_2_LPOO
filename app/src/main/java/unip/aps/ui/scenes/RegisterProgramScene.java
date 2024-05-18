package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Program;
import unip.aps.services.ProgramManagementService;
import unip.aps.ui.components.Theme;
import unip.aps.utils.DataFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class RegisterProgramScene implements  Runnable {

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
        header.add(new AttributedStringBuilder().append(applyStyleTo(" Registro de Programas \n", Theme.BLACK, Theme.MAGENTA)).toAttributedString());

        var prompt = new ConsolePrompt(terminal);
        var promptBuilder = prompt.getPromptBuilder();


        promptBuilder
                .createInputPrompt()
                .name("programName")
                .message("Digite o nome do programa: ").addPrompt()
                .createInputPrompt()
                .name("programLevel")
                .message("Digite o nivel do programa: ").addPrompt()
                .createInputPrompt()
                .name("duracao")
                .message("Digite a duracao do programa: ").addPrompt()
                .createInputPrompt()
                .name("horario")
                .message("Digite o horario do programa: ").addPrompt()
                .createInputPrompt()
                .name("descricao")
                .message("Digite a descricao do curso: ").addPrompt();

        // Recebe os dados inseridos pelo usuario
        Map<String, PromptResultItemIF> result;
        try {
            result = prompt.prompt(header, promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var program = createProgram(result);

        var pms = new ProgramManagementService("Cursos.json");

        var writer = terminal.writer();
        if (pms.isProgramRegistered(program)) {
            writer.println("Programa ja cadastrado!.");
            return;
        }

        if (pms.registerProgram(program)) {
            writer.println("Programa cadastrado com sucesso.");
            return;
        }

        writer.println("Erro ao cadastrar programa.");
    }

    public Program createProgram(Map<String, PromptResultItemIF> result) {
        return new Program(
                result.get("programName").getResult(),
                result.get("programLevel").getResult(),
                result.get("duracao").getResult(),
                result.get("horario").getResult(),
                result.get("descricao").getResult(),
                new ArrayList<>()

        );
    }

}
