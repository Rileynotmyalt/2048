package org.RCarter;

/*
* TODO:
*  - make MoveThread callable, return bool (moved)
*  - make no new block spawn if no "moved" returned
*  - end game, simulate all moves on a copied board
*  - then gui stuff... that'll be fun
 */

public class Main {
    public static Board gameBoard;

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
