package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;
import unip.aps.ui.scenes.editPrograms.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class EditProgramScene implements Runnable {
    @Override
    public void run() {
        var menu = new OptionsMenu<>("Editar cursos", initializeOptionsMap(), Theme.MAGENTA);
        menu.init().ifPresent(Runnable::run);
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Trocar nome do curso", new ChangeProgramNameScene());
        scenes.put("Trocar nivel do curso", new ChangeProgramLevelScene());
        scenes.put("Trocar duracao do curso", new ChangeProgramDurationScene());
        scenes.put("Trocar periodo do curso", new ChangeProgramScheduleScene());
        scenes.put("Trocar descricao do curso", new ChangeProgramDescriptionScene());
        scenes.put("Voltar", null);
        return scenes;
    }
}
