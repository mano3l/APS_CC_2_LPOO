package unip.aps.ui.scenes;

import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import unip.aps.models.Program;
import unip.aps.models.Student;
import unip.aps.services.ProgramManagementService;
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

public class ListStudentScene implements Runnable {

    @Override
    public void run() {

        var sms = new StudentManagementService("Estudantes.json");

        LinkedHashMap<List<String>, Student> mapOptions = new LinkedHashMap<>();

        if (sms.getStudents().isEmpty()) {
            System.out.println(applyStyleTo("Nenhum estudante cadastrado!", Theme.RED));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        for (Student s : sms.getStudents()) {
            ArrayList<String> progOption = new ArrayList<>();

            String nome = s.getNome() + " " + s.getSobrenome();
            String desc = s.getCpf();

            progOption.add(nome);
            progOption.add(desc);

            mapOptions.put(progOption, s);
        }

        PaginatedListMenu<Student> plm = new PaginatedListMenu<>(" Estudantes cadastrados: ", mapOptions, Theme.YELLOW);

        Optional<Student> selectedStudent = plm.init();

        if (selectedStudent.isPresent()) {
            Popup popup = new Popup(selectedStudent.get().toString(), false);
            popup.init();
        }
    }
}