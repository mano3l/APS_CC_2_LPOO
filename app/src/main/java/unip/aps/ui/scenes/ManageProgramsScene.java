package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;

import java.util.LinkedHashMap;
import java.util.Map;

public class ManageProgramsScene implements Runnable {

    @Override
    public void run() {
        var menu = new OptionsMenu<>("Gerenciar cursos", initializeOptionsMap(), Theme.MAGENTA);
        menu.init().ifPresent(Runnable::run);
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Cadastrar Programa", new RegisterProgramForm());
        scenes.put("Listar Programas", new ListProgramScene());
        scenes.put("Editar programa", new EditProgramScene());
        scenes.put("Excluir Programa", new DeleteProgramScene());
        scenes.put("Voltar", null);
        return scenes;
    }
}

