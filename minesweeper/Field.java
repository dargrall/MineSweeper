package minesweeper;

abstract class Field {
    public boolean isRevealed = false;
    abstract public boolean reveal();
}
