package unip.aps.ui.components;

import org.jline.jansi.Ansi;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.*;

import static org.jline.jansi.Ansi.ansi;
import static org.jline.keymap.KeyMap.key;
import static unip.aps.utils.UiUtility.applyStyleTo;

/**
 * This class represents a selectable option component in a terminal-based user interface.
 * It provides a way to navigate through a list of options and select one.
 *
 * @param <E> the type of the options
 */
public class OptionsMenu<E> {

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

    /**
     * Constructs a new OptionsMenu with the given title, options, and theme.
     *
     * @param title      the title of the SelectOption
     * @param optionsMap the options to be displayed
     * @param theme      the theme to be used
     */
    public OptionsMenu(String title, Map<String, E> optionsMap, Theme theme) {
        this.title = title;
        this.optionsMap = optionsMap;
        this.optionsList = new ArrayList<>();
        this.picker = applyStyleTo(">", theme);
        this.selectedOption = "";
        this.theme = theme;
        this.terminal = null;
        this.currentPickerIndex = 0;
        initializeKeyHandlers();
    }

    /**
     * This method is responsible for rendering the OptionsMenu component on the terminal.
     * It first adds all the keys from the optionsMap to the optionsList.
     * Then, it builds a terminal and sets up a BindingReader and a Writer for the terminal.
     * It also creates a KeyMap for handling key presses.
     * The method then enters a loop where it clears the terminal screen, prints the title and options,
     * and waits for a key press. The loop continues until a valid option is selected.
     * Finally, it returns an Optional containing the selected option, if any.
     *
     * @return an Optional containing the selected option, if any
     * @throws RuntimeException if there is an IOException when building the terminal
     */
    public Optional<E> init() {
        // Add all keys from the optionsMap to the optionsList
        optionsList.addAll(optionsMap.keySet());

        try {
            // Build the terminal
            terminal = TerminalBuilder.builder().build();


            // Set up a BindingReader and a Writer for the terminal
            var bindingReader = new BindingReader(terminal.reader());
            var writer = terminal.writer();
            writer.print("\033c");
            terminal.puts(InfoCmp.Capability.cursor_invisible);

            // Create a KeyMap for handling key presses
            KeyMap<Key> keyMap = generateKeyMap(terminal);

            // Attach the picker to the first option
            attachPickerTo(0);

            do {
                // Clear the terminal screen
                terminal.puts(InfoCmp.Capability.clear_screen);
                // Print the title and options
                writer.println(ansi().a("\n" + setTitleTheme(theme).indent(INDENTATION_SPACING)));
                optionsList.stream().map(option -> " ".repeat(3) + option).forEach(writer::println);

                // Print the navigation guide
                writer.print(generateNavigationGuide(terminal.getHeight(), terminal.getWidth()));

                // Wait for a key press
                Key keyPressed = bindingReader.readBinding(keyMap);

                if (keyPressed == Key.Q) break;

                // Handle the key press
                handleKeyPress(keyPressed);

                // Continue the loop until a valid option is selected
            } while (selectedOption.isEmpty());

            // Return an Optional containing the selected option, if any
            return Optional.ofNullable(optionsMap.get(selectedOption));
        } catch (IOException e) {
            // Throw a RuntimeException if there is an IOException when building the terminal
            throw new RuntimeException(e);
        } finally {
            closeTerminal(terminal);
        }
    }

    private String setTitleTheme(Theme theme) {
        if (theme == Theme.BLACK) {
            return applyStyleTo(title, Theme.DEFAULT, Theme.BLACK);
        }
        return applyStyleTo(title, Theme.BLACK, theme);
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
                .set(newIndex, picker + applyStyleTo(optionsList.get(newIndex), theme));
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

}
