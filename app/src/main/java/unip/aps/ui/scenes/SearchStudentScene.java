package unip.aps.ui.scenes;

import org.checkerframework.checker.units.qual.s;
import org.jline.consoleui.prompt.ConsolePrompt;
import org.jline.consoleui.prompt.PromptResultItemIF;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;

import com.google.common.collect.Streams.IntFunctionWithIndex;

import unip.aps.models.Program;
import unip.aps.models.Student;
import unip.aps.services.EnrollmentManagementService;
import unip.aps.services.ProgramManagementService;
import unip.aps.services.StudentManagementService;
import unip.aps.ui.components.PaginatedListMenu;
import unip.aps.ui.components.Popup;
import unip.aps.ui.components.Theme;

import java.io.IOException;
import java.util.*;

import static unip.aps.utils.UiUtility.applyStyleTo;

public class SearchStudentScene implements Runnable {

    @Override
    public void run() {
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
            // Cria o cabe?alho da tela
            List<AttributedString> header = new ArrayList<>();
            header.add(new AttributedStringBuilder().append(applyStyleTo(" Listar Estudantes de Forma Ordenada e Filtrada \n", Theme.WHITE, Theme.YELLOW)).toAttributedString());

            var prompt = new ConsolePrompt(terminal);
            var promptBuilder = prompt.getPromptBuilder();

            promptBuilder.createListPrompt()
                    .name("sortType")
                    .message("Como voce quer ordenar os Estudantes?")
                    .newItem("nome").text("Ordem Alfabetica").add()
                    .newItem("idade").text("Ordem crescente por idade").add()
                    .newItem("nome-idade").text("Ordem Alfabetica e por idade").add()
                    .newItem("idade-nome").text("Ordem por idade e Alfabetica").add()
                    .addPrompt()
                    .createListPrompt()
                    .name("filterTypeSex")
                    .message("Como voce quer filtrar a lista dos Estudantes?")
                    .newItem().text("Masculino").add()
                    .newItem().text("Feminino").add()
                    .newItem().text("Todos").add()
                    .addPrompt();

            // Recebe os dados inseridos pelo usu?rio
            Map<String, PromptResultItemIF> result;
            try {
                result = prompt.prompt(header, promptBuilder.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var sms = new StudentManagementService("Estudantes.json");
            List<Student> students = sms.getStudents();

            var sortType = result.get("sortType").getResult();
            if (sortType.equals("nome")) {
                students = sortStudentsByAlphabeticalOrder(students);

            } else if (sortType.equals("idade")) {
                students = sortStudentsByAge(students);

            } else if (sortType.equals("nome-idade")) {
                students = sortStudentsByAge(students);
                students = sortStudentsByAlphabeticalOrder(students);

            } else if (sortType.equals("idade-nome")) {
                students = sortStudentsByAlphabeticalOrder(students);
                students = sortStudentsByAge(students);
            } else {
                throw new RuntimeException("Tipo de ordenacao invalido");
            }

            var sortSex = result.get("filterTypeSex").getResult();
            if (sortSex.equals("Masculino")) {
                students = searchStudentsBySex("Masculino", students);

            } else if (sortSex.equals("Feminino")) {
                students = searchStudentsBySex("Feminino", students);

            } else if (sortSex.equals("Todos")) {
                // Nao faz nada
            } else {
                throw new RuntimeException("Sexo invalido");
            }

            //Printar lista de estudantes
            LinkedHashMap<List<String>, Student> mapOptions = new LinkedHashMap<>();

            if (sms.getStudents().isEmpty()) {
                System.out.println(applyStyleTo("Nenhum curso cadastrado!", Theme.RED));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            for (Student p : students) {
                ArrayList<String> studOption = new ArrayList<>();

                String nome = p.getNome();
                String desc = p.getCpf();

                studOption.add(nome);
                studOption.add(desc);

                mapOptions.put(studOption, p);
            }

            PaginatedListMenu<Student> plm = new PaginatedListMenu<>(" Cursos disponíveis: ", mapOptions, Theme.YELLOW, terminal);

            Optional<Student> selectedProgram = plm.init();

            if (selectedProgram.isPresent()) {
                Popup popup = new Popup(selectedProgram.get().toString(), false);
                popup.init();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Student> sortStudentsByAlphabeticalOrder(List<Student> students) {
        students.sort((s1, s2) -> s1.getNome().compareTo(s2.getNome()));
        return students;
    }

    public List<Student> sortStudentsByAge(List<Student> students) {
        students.sort((s1, s2) -> s1.getIdade() - s2.getIdade());
        return students;
    }

    public List<Student> searchStudentsBySex(String sexo, List<Student> students) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getSexo().equals(sexo)) {
                result.add(student);
            }
        }
        return result;
    }
}