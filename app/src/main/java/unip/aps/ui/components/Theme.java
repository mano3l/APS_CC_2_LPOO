package unip.aps.ui.components;

public enum Theme {
    BLACK("black"),
    RED("red"),
    GREEN("green"),
    YELLOW("yellow"),
    BLUE("blue"),
    MAGENTA("magenta"),
    CYAN("cyan"),
    WHITE("white"),
    DEFAULT("default");

    private final String color;

    Theme(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}

