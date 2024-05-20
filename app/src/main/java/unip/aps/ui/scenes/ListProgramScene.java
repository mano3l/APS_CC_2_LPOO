package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Program;
import unip.aps.models.Student;
import unip.aps.services.ProgramManagementService;
import unip.aps.ui.components.PaginatedListMenu;
import unip.aps.ui.components.Popup;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class ListProgramScene implements Runnable {

    @Override
    public void run() {
        var pms = new ProgramManagementService("Cursos.json");
        LinkedHashMap<List<String>, Program> mapOptions = new LinkedHashMap<>();

        if (pms.getPrograms().isEmpty()) {
            System.out.println(applyStyleTo("Nenhum curso cadastrado!", Theme.RED));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        for (Program p : pms.getPrograms()) {
            ArrayList<String> progOption = new ArrayList<>();

            String nome = p.getNomeDoPrograma();
            String desc = p.getDescricao();

            progOption.add(nome);
            progOption.add(desc);

            mapOptions.put(progOption, p);
        }

        PaginatedListMenu<Program> plm = new PaginatedListMenu<>(" Cursos disponíveis: ", mapOptions, Theme.YELLOW);

        Optional<Program> selectedProgram = plm.init();

        if (selectedProgram.isPresent()) {
            Popup popup = new Popup(selectedProgram.get().toString(), false);
            popup.init();
        }
    }
}