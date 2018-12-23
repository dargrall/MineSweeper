package minesweeper;

import java.util.Random;

public class Board {
    private int size = 10;
    private int bombs = 23;
    private int revealed = 0;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private Field[][] fields;

    public Board() {
        this.fields = new Field[this.size][this.size];
        // Initialize Fields
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                this.fields[x][y] = new EmptyField(x, y);
            }
        }
        // Initialize Mined Fields
        Random rand = new Random();
        int b;
        for (b = 0; b < this.bombs; b++) {
            int bombX = rand.nextInt(this.size);
            int bombY = rand.nextInt(this.size);
            while (this.fields[bombX][bombY].reveal()) {
                bombX = rand.nextInt(this.size);
                bombY = rand.nextInt(this.size);
            }
            this.fields[bombX][bombY] = new MinedField(bombX, bombY);
        }
    }

    public Integer getSize() {
        return this.size;
    }

    public Integer getRevealed() {
        return this.revealed;
    }

    public Integer getBombed() {
        return this.bombs;
    }


    /**
     * Prints the current state of the field
     */
    public void print() {
        String output = "     ";
        Field currentField;
        // Generating the tableheader
        for (int i = 0; i < this.size; i++) {
            output += "|";
            output += "-" + this.alphabet.substring(i,i + 1).toUpperCase() + "-";
        }
        output += "|\n";
        output += "--";
        for (int counter = 0; counter < this.size + 1; counter++) {
            output += "----";
        }
        output += "\n";
        for (int y = 0; y < this.size;y++) {
            for (int x = 0; x < this.size + 1;x++) {
                output += "|";
                if (x == 0) {
                    // Formatting numbers
                    output += ((y + 1) >= 10) ? "-": "--" ;
                    output += (y+1) + "-";
                } else {
                    // Actual Board
                    currentField = this.fields[x - 1][y];
                    if (currentField.isRevealed) {
                        if (!currentField.reveal()) {
                            output += " " + this.getBombCount(currentField) + " ";
                        } else {
                            output += currentField.print();
                        }
                    } else if (currentField.isMarked) {
                        output += "#?#";
                    } else {
                        // output += currentField.print();
                        output += "###";
                    }
                }
                if (x == this.size) {
                    output += "|";
                }
            }
            output += "\n";
            output += "--";
            for (int counter = 0; counter < this.size + 1; counter++) {
                output += "----";
            }
            output += "\n";
        }

        System.out.println(output);
    }

    public Field getFieldFromCoordinates (String coordinates) {
        int x = this.alphabet.indexOf(coordinates.substring(0, 1).toLowerCase());
        int y = Integer.parseInt(coordinates.substring(1));

        int xLimit = size - 1;
        if (x > xLimit || y > size) {
            return null;
        }
        return this.fields[x][y - 1];
    }

    public void markField(String coordinates)  {
        Field field = getFieldFromCoordinates(coordinates);
        field.isMarked = true;
    }

    public Boolean revealField(String coordinates)  {
        Field field = getFieldFromCoordinates(coordinates);
        field.isVisited = true;
        if (this.getBombCount(field) == 0) {
            this.revealNeighbours(field);
            this.recursiveReveal(field);
        } else {
            if (!field.isRevealed) {
                field.isRevealed = true;
                this.revealed++;
            }
        }
System.out.println("field[" + field.x + "][" + field.y + "] is revealed = " + field.isRevealed);
        return field.reveal();
    }

    private void recursiveReveal(Field field) {
        Integer[] neighbours = new Integer[] {1, 0, -1};
        Integer neighbourX;
        Integer neighbourY;

        if (!field.isRevealed) {
            field.isRevealed = true;
            this.revealed++;
        }

        for (int i:neighbours) {
            neighbourX = field.x + i;
            for(int j:neighbours) {
                neighbourY = field.y + j;
                if (neighbourX >= 0 && neighbourX < this.size
                    && neighbourY >= 0 && neighbourY < this.size
                    && !this.fields[neighbourX][neighbourY].reveal()) {
                    if (!this.fields[neighbourX][neighbourY].isVisited
                        && this.getBombCount(this.fields[neighbourX][neighbourY]) == 0) {

                        field.isRevealed = true;
                        this.revealNeighbours(field);
                        field.isVisited = true;
                        this.recursiveReveal(this.fields[neighbourX][neighbourY]);
                    }
                }
            }
        }
    }

    private void revealNeighbours(Field field) {
        Integer[] neighbours = new Integer[] {1, 0, -1};
        Integer neighbourX;
        Integer neighbourY;

        for (int i:neighbours) {
            neighbourX = field.x + i;
            for(int j:neighbours) {
                neighbourY = field.y + j;
                if (neighbourX >= 0 && neighbourX < this.size
                    && neighbourY >= 0 && neighbourY < this.size
                    && !this.fields[neighbourX][neighbourY].reveal()) {
                        if (!this.fields[neighbourX][neighbourY].isRevealed) {
                            this.revealed++;
                            this.fields[neighbourX][neighbourY].isRevealed = true;
                        }
                }
            }
        }
    }

    private Integer getBombCount(Field field) {
        Integer[] neighbours = new Integer[] {1, 0, -1};
        Integer neighbourX;
        Integer neighbourY;
        int bombCount = 0;

        for (int i:neighbours) {
            neighbourX = field.x + i;
            for(int j:neighbours) {
                neighbourY = field.y + j;
                if (neighbourX >= 0 && neighbourX < this.size
                    && neighbourY >= 0 && neighbourY < this.size
                    && this.fields[neighbourX][neighbourY].reveal()) {
                    bombCount++;
                }
            }
        }
        return bombCount;
    }

    public void revealBombs() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (this.fields[x][y].reveal()) {
                    this.fields[x][y].isRevealed = true;
                }
            }
        }
    }
}
