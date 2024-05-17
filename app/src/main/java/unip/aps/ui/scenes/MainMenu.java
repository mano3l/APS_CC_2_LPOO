package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;

import java.util.*;

public class MainMenu {
    private final Map<String, Runnable> scenesMap;

    public MainMenu() {
        this.scenesMap = initializeOptionsMap();
    }

    public void init() {
        var menu = new OptionsMenu<>("Selecione uma opcao: ", scenesMap, Theme.GREEN);
        menu.init().ifPresent(Runnable::run);
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Gerenciar cursos", new ManageProgramsScene());
        scenes.put("Gerenciar estudantes", new ManageStudentsScene());
        scenes.put("Gerenciar matriculas", new ManageEnrollmentsScene());
        scenes.put("Sair", new ExitScreen());

        return scenes;
    }

}