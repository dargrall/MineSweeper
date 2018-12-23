package minesweeper;

abstract class Field {
    public boolean isRevealed = false;
    // Needed for the recursive Reveal of EmptyFields
    public Boolean isVisited = false;
    public boolean isMarked = false;
    public int x;
    public int y;
    abstract public String print();
    abstract public boolean reveal();
}
