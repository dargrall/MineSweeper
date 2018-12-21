package minesweeper;
import minesweeper.Field;

public class EmptyField extends Field {
    public boolean reveal() {
        return true;
    }
}
