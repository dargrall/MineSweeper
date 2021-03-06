package minesweeper;

import minesweeper.MinedField;
import minesweeper.EmptyField;
import minesweeper.Board;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Game {
    public static void main(String[] args) {
        Boolean gameOver = false;
        Boolean won = false;
        Board board = new Board();
        int emptyFields = board.getSize() * board.getSize() - board.getBombed();
        Scanner sc = new Scanner(System.in);
        String action = "";
        String coordinates = "";
        Boolean validInput = false;
        Field selectedField;
        String validFormat = "[A-Z][0-9]{1,2}";
        Pattern pattern = Pattern.compile(validFormat);
        Matcher matcher;


        while (!gameOver) {
            board.print();

            validInput = false;
            while (!validInput) {
                System.out.println("Please enter action, type 'reveal' (default) or 'mark':");
                action = sc.next();
                if (action.equals("reveal") || action.equals("mark")) {
                    validInput = true;
                }
            }

            validInput = false;
            while (!validInput) {
                System.out.println("Please enter coordinates, for example 'C4':");
                coordinates = sc.next();
                // Put some validation here
                matcher = pattern.matcher(coordinates);
                if (matcher.matches()) {
                    if (board.getFieldFromCoordinates(coordinates) != null) {
                        validInput = true;
                    }
                }
            }

            if (action.equals("mark")) {
                board.markField(coordinates);
            } else {
                gameOver = board.revealField(coordinates);
            }

            if (emptyFields ==  board.getRevealed()) {
                gameOver = true;
                won = true;
            }
        }

        if (!won) {
            board.revealBombs();
        }
        
        board.print();
        if (won) {
            System.out.println("Congratulations you won!");
        } else {
            System.out.println("Sorry but you lost!");
        }
    }
}
