package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ManageProgramsScene implements Runnable {

    @Override
    public void run() {
        while (true) {
            var menu = new OptionsMenu<>("Gerenciar cursos", initializeOptionsMap(), Theme.MAGENTA);
            Optional<Runnable> o = menu.init();

            if (o.isEmpty()) break;

            o.get().run();
        }
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Cadastrar Curso", new RegisterProgramForm());
        scenes.put("Listar Cursos", new ListProgramScene());
        scenes.put("Editar Curso", new EditProgramScene());
        scenes.put("Excluir Curso", new DeleteProgramScene());
        scenes.put("Voltar", null);
        return scenes;
    }
}

