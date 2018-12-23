package minesweeper;
import minesweeper.Field;

public class MinedField extends Field {
    public MinedField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String print() {
        return " O ";
    }

    public boolean reveal() {
        return true;
    }
}
