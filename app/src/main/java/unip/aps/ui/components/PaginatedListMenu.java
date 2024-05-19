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

public class PaginatedListMenu<E> {

    private static final int INDENTATION_SPACING = 3;
    private static final int OPTIONS_PER_PAGE = 3;

    private final String title;
    private final Map<List<String>, E> optionsMap;
    private final List<String> optionsList;
    private final List<String> descriptionsList;
    private final String picker;
    private String selectedOption;
    private final Theme theme;
    private Integer pageIndex;
    private List<List<String>> pages;
    private Map<Key, Runnable> keyHandlers;
    private Terminal terminal;
    private int currentPickerIndex;

    public PaginatedListMenu(String title, Map<List<String>, E> optionsMap, Theme theme) {
        this.title = title;
        this.optionsMap = optionsMap;
        this.optionsList = new ArrayList<>();
        this.descriptionsList = new ArrayList<>();
        this.picker = applyStyleTo("•", theme);
        this.selectedOption = "";
        this.theme = theme;
        this.pageIndex = 0;
        this.pages = new ArrayList<>();
        this.terminal = null;
        this.currentPickerIndex = 0;
        initializeKeyHandlers();
    }

    public Optional<E> init() {
        // Clear the system terminal before executing the code
        System.out.println("\033c");

        // Add all keys from the optionsMap to the optionsList
        optionsMap.forEach((key, value) -> {
            optionsList.add(key.get(0));
            descriptionsList.add(key.get(1));
        });

        // Initialize the terminal
        try {
            // Initial terminal config
            terminal = TerminalBuilder.builder().build();
            terminal.puts(InfoCmp.Capability.cursor_invisible);
            var bindingReader = new BindingReader(terminal.reader());
            var writer = terminal.writer();

            KeyMap<Key> keyMap = generateKeyMap(terminal);
            pages = generatePages();
            // Set the picker position for the first option
            attachPickerTo(0);
            // Enter a loop to handle user input
            do {
                // Clear the screen and flush the terminal
                terminal.puts(InfoCmp.Capability.clear_screen);
                // Prints title
                writer.println(ansi().a("\n" + " ".repeat(INDENTATION_SPACING) + setTitleTheme(theme) + "\n"));
                // Prints options
                for (int i = 0; i < activePage().size(); i++) {

                    if (activePage().get(i).startsWith(picker)) {
                        writer.println(" ".repeat(INDENTATION_SPACING - 1) + activePage().get(i));
                    } else {
                        writer.println(" ".repeat(INDENTATION_SPACING) + activePage().get(i));
                    }

                    var description = descriptionsList.get(pageIndex * OPTIONS_PER_PAGE + i);
                    // Print the description related to the current option
                    writer.println(ansi().fgBrightBlack().a(" ".repeat(INDENTATION_SPACING) + description + "\n").reset());
                }

                writer.println(generatePaginator(terminal.getHeight(), terminal.getWidth()));

                writer.print(generateNavigationGuide(terminal.getHeight(), terminal.getWidth()));
                // Listen for keypress
                Key keyPressed = bindingReader.readBinding(keyMap);

                if (keyPressed == Key.Q) break;

                handleKeyPress(keyPressed);

            } while (selectedOption.isEmpty());
            // Return an Optional with the value to witch the specified option is mapped
            return getValueFromPartialKey(selectedOption, optionsMap);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeTerminal(terminal);
        }
    }

    public Optional<E> getValueFromPartialKey(String partialKey, Map<List<String>, E> map) {
        for (Map.Entry<List<String>, E> entry : map.entrySet()) {
            if (entry.getKey().get(0).equals(partialKey)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    private void initializeKeyHandlers() {
        keyHandlers = new EnumMap<>(Key.class);
        keyHandlers.put(Key.DOWN, this::handleDownKeyPress);
        keyHandlers.put(Key.UP, this::handleUpKeyPress);
        keyHandlers.put(Key.RIGHT, this::handleRightKeyPress);
        keyHandlers.put(Key.LEFT, this::handleLeftKeyPress);
        keyHandlers.put(Key.ENTER, this::handleEnterKeyPress);
    }

    private void handleKeyPress(Key keyPressed) {
        Runnable handler = keyHandlers.get(keyPressed);
        if (handler != null) {
            handler.run();
        }
    }

    private void handleDownKeyPress() {
        if (currentPickerIndex < activePage().size() - 1) {
            detachPickerFrom(currentPickerIndex);
            attachPickerTo(++currentPickerIndex);
        }
    }

    private void handleUpKeyPress() {
        if (currentPickerIndex > 0) {
            detachPickerFrom(currentPickerIndex);
            attachPickerTo(--currentPickerIndex);
        }
    }

    private void handleRightKeyPress() {
        if (pageIndex < pages.size() - 1) {
            detachPickerFrom(currentPickerIndex);
            pageIndex++;
            currentPickerIndex = 0;
            attachPickerTo(currentPickerIndex);
        }
    }

    private void handleLeftKeyPress() {
        if (pageIndex > 0) {
            detachPickerFrom(currentPickerIndex);
            pageIndex--;
            currentPickerIndex = 0;
            attachPickerTo(currentPickerIndex);
        }
    }

    private void handleEnterKeyPress() {
        selectedOption = removeAnsiAttributes(activePage().get(currentPickerIndex).replace(picker, ""));
        closeTerminal(terminal);
    }

    private String setTitleTheme(Theme theme) {
        if (theme == Theme.BLACK) {
            return applyStyleTo(title, Theme.DEFAULT, Theme.BLACK);
        }
        return applyStyleTo(title, Theme.BLACK, theme);
    }

    private List<List<String>> generatePages() {
        List<List<String>> newlist = new ArrayList<>();
        int optionIndex = 0;
        int numberPages = optionsList.size() % OPTIONS_PER_PAGE != 0
                ? optionsList.size() / OPTIONS_PER_PAGE + 1
                : optionsList.size() / OPTIONS_PER_PAGE;

        for (int j = 0; j < numberPages; j++) {
            List<String> nestedList = new ArrayList<>();
            for (int i = 0; i < OPTIONS_PER_PAGE; i++) {
                if (optionIndex < optionsList.size()) {
                    nestedList.add(optionsList.get(optionIndex));
                    optionIndex++;
                }
            }
            newlist.add(nestedList);
        }
        return newlist;
    }

    private KeyMap<Key> generateKeyMap(Terminal terminal) {
        KeyMap<Key> keyMap = new KeyMap<>();
        keyMap.bind(Key.UP, key(terminal, InfoCmp.Capability.key_up));
        keyMap.bind(Key.DOWN, key(terminal, InfoCmp.Capability.key_down));
        keyMap.bind(Key.LEFT, key(terminal, InfoCmp.Capability.key_left));
        keyMap.bind(Key.RIGHT, key(terminal, InfoCmp.Capability.key_right));
        keyMap.bind(Key.ENTER, key(terminal, InfoCmp.Capability.carriage_return));
        keyMap.bind(Key.Q, "q");
        return keyMap;
    }

    private Ansi generatePaginator(int terminalHeight, int terminalWidth) {
        int headerHeight = (int) Math.ceil((double) title.length() / terminalWidth);
        int optionsHeight = activePage().size() * 3;
        int navigationGuideHeight = 1;
        int emptySpaceHeight = 5;

        int yMeasure = headerHeight + optionsHeight + navigationGuideHeight + emptySpaceHeight;

        String placementString = "\n".repeat(terminalHeight - yMeasure);

        return ansi()
                .a(placementString)
                .a(" ".repeat(INDENTATION_SPACING))
                .fg(theme.ordinal())
                .a(pageIndex + 1)
                .fgBrightBlack()
                .a("/")
                .reset()
                .a(pages.size())
                .a("\n");
    }

    private Ansi generateNavigationGuide(int terminalHeight, int terminalWidth) {
        return ansi()
                .a(" ".repeat(INDENTATION_SPACING))
                .fgBrightBlack()
                .a("Use as SETAS para navegar | ENTER para escolher | Q cancelar")
                .reset();
    }

    // HELPER METHODS

    private void attachPickerTo(int newIndex) {
        activePage()
                .set(newIndex, picker + applyStyleTo(activePage().get(newIndex), theme));

        var description = descriptionsList.get(pageIndex * OPTIONS_PER_PAGE + newIndex);
        descriptionsList.set(pageIndex * OPTIONS_PER_PAGE + newIndex, applyStyleTo(description, theme));
    }

    private void detachPickerFrom(int previousIndex) {
        activePage()
                .set(previousIndex, removeAnsiAttributes(activePage().get(previousIndex).replace(picker, "")));

        var description = descriptionsList.get(pageIndex * OPTIONS_PER_PAGE + previousIndex);
        descriptionsList.set(pageIndex * OPTIONS_PER_PAGE + previousIndex, removeAnsiAttributes(description));
    }

    private List<String> activePage() {
        return pages.get(pageIndex);
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

