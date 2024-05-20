package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Enrollment;
import unip.aps.models.Program;
import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.components.PaginatedListMenu;
import unip.aps.ui.components.Popup;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class ListEnrollmentScene implements Runnable {

    @Override
    public void run() {
        var ems = new EnrollmentManagementService("Matriculas.json");
        LinkedHashMap<List<String>, Enrollment> mapOptions = new LinkedHashMap<>();

        if (ems.getEnrollments().isEmpty()) {
            System.out.println(applyStyleTo("Nenhuma matrícula realizada!", Theme.RED));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        for (Enrollment e : ems.getEnrollments()) {
            List<String> progOption = new ArrayList<>();

            String nome = e.getRa();
            String desc = e.getDataMatricula();

            progOption.add(nome);
            progOption.add(desc);

            mapOptions.put(progOption, e);
        }

        PaginatedListMenu<Enrollment> plm = new PaginatedListMenu<>(" Matrículas: ", mapOptions, Theme.YELLOW);

        Optional<Enrollment> selectedEnrollment = plm.init();

        if (selectedEnrollment.isPresent()) {
            Popup popup = new Popup(selectedEnrollment.get().toString(), false);
            popup.init();
        }
    }
}
