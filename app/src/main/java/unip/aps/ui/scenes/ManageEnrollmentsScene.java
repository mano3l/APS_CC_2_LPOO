package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;

import java.util.LinkedHashMap;
import java.util.Map;

public class ManageEnrollmentsScene implements Runnable {
    @Override
    public void run() {
        var menu = new OptionsMenu<>("Gerenciar matriculas", initializeOptionsMap(), Theme.CYAN);
        menu.init().ifPresent(Runnable::run);
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Cadastrar matricula", new RegisterEnrollmentForm());
        scenes.put("Listar matriculas", new ListEnrollmentScene());
        scenes.put("Editar matricula", new EditEnrollmentScene());
        scenes.put("Excluir matricula", new DeleteEnrollmentScene());
        scenes.put("Voltar", null);
        return scenes;
    }
}
