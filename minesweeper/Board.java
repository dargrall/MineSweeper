package minesweeper;

import java.util.Random;

public class Board {
    int size = 10;
    int bombs = 23;
    int revealed = 5;
    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    Field[][] fields;;

    public Board() {
        this.fields = new Field[size][size];
        // Initialize Fields
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.fields[x][y] = new EmptyField();
            }
        }
        // Initialize Mined Fields
        Random rand = new Random();
        int b;
        for (b = 0; b < bombs; b++) {
            int bombX = rand.nextInt(size);
            int bombY = rand.nextInt(size);
            while (!this.fields[bombX][bombY].reveal()) {
                bombX = rand.nextInt(size);
                bombY = rand.nextInt(size);
            }
            this.fields[bombX][bombY] = new MinedField();

        }
    }

    /**
     * Prints the current state of the field
     */
    public void print() {
        String output = "     ";
        // Generating the tableheader
        for (int i = 0; i < size; i++) {
            output += "|";
            output += " " + Character.toUpperCase(alphabet[i]) + " ";
        }
        output += "|\n";
        output += "--";
        for (int counter = 0; counter < size + 1; counter++) {
            output += "----";
        }
        output += "\n";
        for (int y = 0; y < size;y++) {
            for (int x = 0; x < size + 1;x++) {
                output += "|";
                if (x == 0) {
                    // Formatting numbers
                    output += ((y + 1) >= 10) ? " ": "  " ;
                    output += (y+1) + " ";
                } else {
                    // Actual Board
                    // Show Mined and Empty fields
                    if (this.fields[x - 1][y].reveal()) {
                        if (this.fields[x - 1][y].isRevealed) {
                            output += "   ";
                        }
                        output += "###";
                    } else {
                        output += " O ";
                    }
                }
                if (x == size) {
                    output += "|";
                }
            }
            output += "\n";
            output += "--";
            for (int counter = 0; counter < size + 1; counter++) {
                output += "----";
            }
            output += "\n";
        }

        System.out.println(output);
    }
}
