package ru.netology.graphics.image;

public class SelectSymbol implements TextColorSchema{

    @Override
    public char convert(int color) {
        char[] symbols = new char[]{'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};
        int index = Math.round((symbols.length-1) * color / 255);
        return symbols[index];

    }
}
