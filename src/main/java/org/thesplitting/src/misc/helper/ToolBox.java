package org.thesplitting.src.misc.helper;

public class ToolBox {
    public static String formatEnumToWord(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }
}
