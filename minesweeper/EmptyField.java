package minesweeper;
import minesweeper.Field;

public class EmptyField extends Field {
    public EmptyField(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String print() {
        return "   ";
    }

    public boolean reveal() {
        return false;
    }
}
