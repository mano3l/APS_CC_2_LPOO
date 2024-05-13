package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.consoleui.prompt.builder.PromptBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentsScene implements Runnable {

    @Override
    public void run() {
        var menu = new OptionsMenu<>("Gerenciar estudantes", initializeOptionsMap(), Theme.YELLOW);
        menu.init().ifPresent(Runnable::run);
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Cadastrar estudante", new RegisterStudentScene());
        scenes.put("Listar estudantes", null);
        scenes.put("Excluir estudante", null);
        scenes.put("Voltar", null);
        return scenes;
    }
}
