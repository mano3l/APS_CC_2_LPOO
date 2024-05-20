package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ManageStudentsScene implements Runnable {

    @Override
    public void run() {
        while (true) {
            var menu = new OptionsMenu<>("Gerenciar estudantes", initializeOptionsMap(), Theme.MAGENTA);
            Optional<Runnable> o = menu.init();

            if (o.isEmpty()) break;

            o.get().run();
        }
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Listar estudantes", new ListStudentScene());
        scenes.put("Editar estudante", new EditStudentScene());
        scenes.put("Buscar estudante", new SearchStudentScene());
        scenes.put("Voltar", null);
        return scenes;
    }
}
