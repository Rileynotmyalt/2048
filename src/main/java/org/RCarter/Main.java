package org.RCarter;

import java.util.Scanner;

/*
* TODO:
*  - Multi-Threading each row for movement detection
 */

public class Main {
    public static Board gameBoard;
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public static void main(String[] args) {
        Board gameBoard = new Board(4, 4);
        Gui gui = new Gui(gameBoard);

        System.out.println(gameBoard);

//        while (true) {
//            Scanner scn = new Scanner(System.in);
//            String input = scn.nextLine();
//
//            switch (input.toLowerCase()) {
//                case "q" -> System.exit(0);
//                case "w" -> gameBoard.up();
//                case "a" -> gameBoard.left();
//                case "s" -> gameBoard.down();
//                case "d" -> gameBoard.right();
//            }
//        }

    }

}
