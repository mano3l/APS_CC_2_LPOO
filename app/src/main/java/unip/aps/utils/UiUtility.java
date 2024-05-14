package unip.aps.utils;

import unip.aps.ui.components.Theme;

import static org.jline.jansi.Ansi.ansi;

public class UiUtility {
    public static String applyStyleTo(String str, Theme color) {
        return ansi().render("@|" + color.getColor() + " " + str + "|@").toString();
    }

    public static String applyStyleTo(String str, Theme fgColor, Theme bgColor) {
        return ansi().render("@|fg_" + fgColor.getColor() + "," + "bg_" + bgColor.getColor() + " " + str + "|@").toString();
    }
}
