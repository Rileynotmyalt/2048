package org.RCarter;

/*
* TODO:
*  - end game, simulate all moves on a copied board
*  - then gui stuff... that'll be fun
 */

public class Main {
    public static Board gameBoard;

    public static void main(String[] args) {
        Board gameBoard = new Board(4, 4);
        Gui gui = new Gui(gameBoard);

        System.out.println(gameBoard);
    }

}
