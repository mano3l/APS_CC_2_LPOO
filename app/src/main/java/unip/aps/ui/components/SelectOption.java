package unip.aps.ui.components;

import org.jline.jansi.Ansi;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import unip.aps.ui.Theme;

import java.io.IOException;
import java.util.*;

import static org.jline.jansi.Ansi.ansi;
import static org.jline.keymap.KeyMap.key;

public class SelectOption<E> {

    private static final int NAVIGATION_GUIDE_SPACING = 5;
    private static final int INDENTATION_SPACING = 3;

    private final String title;
    private final Map<String, E> optionsMap;
    private final List<String> optionsList;
    private final String picker;
    private String selectedOption;
    private final Theme theme;
    private Map<Key, Runnable> keyHandlers;
    private Terminal terminal;
    private int currentPickerIndex;

    public SelectOption(String title, Map<String, E> optionsMap, Theme theme) {
        this.title = title;
        this.optionsMap = optionsMap;
        this.optionsList = new ArrayList<>();
        this.picker = styleString(">", theme);
        this.selectedOption = "";
        this.theme = theme;
        this.terminal = null;
        this.currentPickerIndex = 0;
        initializeKeyHandlers();
    }

    public Optional<E> render() {
        // Add all keys from the optionsMap to the optionsList
        optionsList.addAll(optionsMap.keySet());

        // Initialize the terminal
        try {
            terminal = TerminalBuilder.builder().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var bindingReader = new BindingReader(terminal.reader());
        var writer = terminal.writer();
        // Clear the system terminal before executing the code
        writer.print("\033c");
        terminal.puts(InfoCmp.Capability.cursor_invisible);

        KeyMap<Key> keyMap = generateKeyMap(terminal);

        // Set the picker position for the first option
        attachPickerTo(0);

        // Enter a loop to handle user input
        do {
            // Clear the screen and flush the terminal
            terminal.puts(InfoCmp.Capability.clear_screen);

            // Print the title and the options
            writer.println(ansi().a("\n   " + setTitleTheme(theme) + "\n"));
            optionsList.stream().map(option -> " ".repeat(3) + option).forEach(writer::println);

            writer.print(generateNavigationGuide(terminal.getHeight(), terminal.getWidth()));

            // Listen for key press
            Key keyPressed = bindingReader.readBinding(keyMap);

            handleKeyPress(keyPressed);

        } while (selectedOption.isEmpty());
        // Return an Optional with the Enum to witch the specified key is mapped
        return Optional.ofNullable(optionsMap.get(selectedOption));
    }

    private String setTitleTheme(Theme theme) {
        if (theme == Theme.BLACK) {
            return styleString(title, Theme.DEFAULT, Theme.BLACK);
        }
        return styleString(title, Theme.BLACK, theme);
    }

    private KeyMap<Key> generateKeyMap(Terminal terminal) {
        KeyMap<Key> keyMap = new KeyMap<>();
        keyMap.bind(Key.UP, key(terminal, InfoCmp.Capability.key_up));
        keyMap.bind(Key.DOWN, key(terminal, InfoCmp.Capability.key_down));
        keyMap.bind(Key.ENTER, key(terminal, InfoCmp.Capability.carriage_return));
        keyMap.bind(Key.Q, "q");
        return keyMap;
    }

    private void initializeKeyHandlers() {
        keyHandlers = new EnumMap<>(Key.class);
        keyHandlers.put(Key.DOWN, this::handleDownKeyPress);
        keyHandlers.put(Key.UP, this::handleUpKeyPress);
        keyHandlers.put(Key.ENTER, this::handleEnterKeyPress);
        keyHandlers.put(Key.Q, this::handleQKeyPress);
    }

    private void handleKeyPress(Key keyPressed) {
        Runnable handler = keyHandlers.get(keyPressed);
        if (handler != null) {
            handler.run();
        }
    }

    private void handleUpKeyPress() {
        if (currentPickerIndex > 0) {
            detachPickerFrom(currentPickerIndex);
            attachPickerTo(--currentPickerIndex);
        }
    }

    private void handleDownKeyPress() {
        if (currentPickerIndex < optionsList.size() - 1) {
            detachPickerFrom(currentPickerIndex);
            attachPickerTo(++currentPickerIndex);
        }
    }

    private void handleEnterKeyPress() {
        selectedOption = removeAnsiAttributes(optionsList.get(currentPickerIndex).replace(picker, ""));
        closeTerminal(terminal);
    }

    private void handleQKeyPress() {
        closeTerminal(terminal);
    }

    private Ansi generateNavigationGuide(int terminalHeight, int terminalWidth) {
        int titleLineCount = title.length() / terminalWidth;
        int spaceAboveGuide = titleLineCount + optionsList.size();

        String placementString =
                "\n".repeat((terminalHeight - spaceAboveGuide) - NAVIGATION_GUIDE_SPACING) +
                        " ".repeat(INDENTATION_SPACING);

        return ansi()
                .a(placementString)
                .fgBrightBlack()
                .a("Use as SETAS para navegar | ENTER para escolher | Q cancelar")
                .reset();
    }

    // HELPER METHODS

    private void attachPickerTo(int newIndex) {
        optionsList
                .set(newIndex, picker + styleString(optionsList.get(newIndex), theme));
    }

    private void detachPickerFrom(int oldIndex) {
        optionsList
                .set(oldIndex, removeAnsiAttributes(optionsList.get(oldIndex).replace(picker, "")));
    }

    private void closeTerminal(Terminal terminal) {
        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.puts(InfoCmp.Capability.cursor_visible);
        terminal.writer().close();
        terminal.flush();
        try {
            terminal.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String removeAnsiAttributes(String str) {
        return str.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private String styleString(String str, Theme color) {
        return ansi().render("@|" + color.getColor() + " " + str + "|@").toString();
    }

    private String styleString(String str, Theme fgColor, Theme bgColor) {
        return ansi().render("@|fg_" + fgColor.getColor() + "," + "bg_" + bgColor.getColor() + " " + str + "|@").toString();
    }

    private enum Key {
        UP,
        DOWN,
        ENTER,
        Q
    }
}
