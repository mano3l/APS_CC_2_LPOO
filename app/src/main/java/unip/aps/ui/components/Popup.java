package unip.aps.ui.components;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

import static org.jline.keymap.KeyMap.key;

public class Popup {

    private final Terminal terminal;
    private final Boolean cursorWasInvisible;
    private final String message;

    public Popup(String message, Boolean cursorWasInvisible) {
        try {
            this.terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.cursorWasInvisible = cursorWasInvisible;
        this.message = message;
    }

    public Boolean init() {
        terminal.puts(InfoCmp.Capability.enter_ca_mode);
        terminal.puts(InfoCmp.Capability.cursor_invisible);
        terminal.flush();

        while (true) {
            draw();

            var bindingReader = new BindingReader(terminal.reader());
            KeyMap<Key> keyMap = generateKeyMap();

            Key keyPressed = bindingReader.readBinding(keyMap);

            if (keyPressed == Key.ENTER) {
                close();
                return true;
            }
        }
    }

    private void draw() {
        terminal.writer().write(message);
        terminal.writer().write("Pressione ENTER para continuar");
        terminal.flush();
    }

    private KeyMap<Key> generateKeyMap() {
        KeyMap<Key> keyMap = new KeyMap<>();
        keyMap.bind(Key.ENTER, key(terminal, InfoCmp.Capability.carriage_return));
        return keyMap;
    }

    private void close() {
        terminal.puts(InfoCmp.Capability.exit_ca_mode);
        if (cursorWasInvisible) {
            terminal.puts(InfoCmp.Capability.cursor_visible);
        }
        terminal.flush();
        try {
            terminal.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private enum Key {
        ENTER
    }
}
