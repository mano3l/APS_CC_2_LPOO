package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;

import java.util.LinkedHashMap;
import java.util.Map;

public class ManageStudentsScene implements Runnable {

    @Override
    public void run() {
        var menu = new OptionsMenu<>("Gerenciar estudantes", initializeOptionsMap(), Theme.WHITE);
        menu.init().ifPresent(Runnable::run);
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
