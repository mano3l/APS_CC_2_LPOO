package unip.aps.ui.scenes;

import unip.aps.ui.components.OptionsMenu;
import unip.aps.ui.components.Theme;
import unip.aps.ui.scenes.editPrograms.*;
import unip.aps.ui.scenes.editStudents.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class EditStudentScene implements Runnable{
    @Override
    public void run() {
        var menu = new OptionsMenu<>(" Editar estudantes: ", initializeOptionsMap(), Theme.WHITE);
        menu.init().ifPresent(Runnable::run);
    }

    public Map<String, Runnable> initializeOptionsMap() {
        Map<String, Runnable> scenes = new LinkedHashMap<>();
        scenes.put("Trocar nome do estudante", new ChangeStudentNameScene());
        scenes.put("Trocar idade do estudante", new ChangeStudentAgeScene());
        scenes.put("Trocar endereco do estudante", new ChangeStudentAddressScene());
        scenes.put("Trocar telefone do estudante", new ChangeStudentPhoneScene());
        scenes.put("Trocar sexo do estudante", new ChangeStudentSexScene());
        scenes.put("Voltar", null);
        return scenes;
    }
}
