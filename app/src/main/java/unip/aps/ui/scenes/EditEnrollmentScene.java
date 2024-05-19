package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;
import unip.aps.ui.scenes.editEnrollments.ChangeStudentProgram;
import unip.aps.ui.scenes.editPrograms.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class EditEnrollmentScene implements Runnable {
    @Override
    public void run() {
        var menu = new OptionsMenu<>("Editar matriculas ", initializeOptionsMap(), Theme.MAGENTA);
        menu.init().ifPresent(Runnable::run);
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Trocar curso do aluno", new ChangeStudentProgram());
        scenes.put("Voltar", null);
        return scenes;
    }
}