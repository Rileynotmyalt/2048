package org.RCarter;

import java.awt.*;
import java.awt.event.*;

/*
* TODO:
*  - menu, settings, credits, different color profiles
*  - different colors for different numbered squares
*  - square moving animations?
 */

public class Gui extends Frame {
    public Label[][] gridLabels;
    public final int BOARD_HEIGHT;
    public final int BOARD_WIDTH;

    // constructor
    Gui(Board gameBoard) {
        BOARD_HEIGHT = gameBoard.cells.length;
        BOARD_WIDTH = gameBoard.cells[0].length;


        setLayout(new GridLayout(1,1));

        // grid tab init
        gridLabels = new Label[BOARD_HEIGHT][BOARD_WIDTH];
        Panel gridTab = new Panel(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH));
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_HEIGHT; x++) {
                Label tempLabel = new Label(
                        (gameBoard.cells[y][x].block != null) ? String.valueOf((int)Math.pow(2, gameBoard.cells[y][x].block.val)) : null
                );
                gridLabels[y][x] = tempLabel;
                gridTab.add(tempLabel);
            }
        }

        gridTab.setVisible(true);
        add(gridTab);

        // listener
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> {
                        gameBoard.up();
                        updateBoard(gridTab, gameBoard, gridLabels);
                    }
                    case KeyEvent.VK_DOWN -> {
                        gameBoard.down();
                        updateBoard(gridTab, gameBoard, gridLabels);
                    }
                    case KeyEvent.VK_LEFT -> {
                        gameBoard.left();
                        updateBoard(gridTab, gameBoard, gridLabels);
                    }
                    case KeyEvent.VK_RIGHT -> {
                        gameBoard.right();
                        updateBoard(gridTab, gameBoard, gridLabels);
                    }
                    default -> {}
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    gameBoard.up();
                    updateBoard(gridTab, gameBoard, gridLabels);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        setTitle("2048");
        setSize(400,400);
        setVisible(true);
    }

    private void updateBoard(Panel gridTab, Board gameBoard, Label[][] gridLabels ) {
        for (int y = 0; y < gameBoard.cells.length ; y++) {
            for (int x = 0; x < gameBoard.cells.length ; x++) {
                gridLabels[y][x].setText(
                        (gameBoard.cells[y][x].block != null) ? String.valueOf((int)Math.pow(2, gameBoard.cells[y][x].block.val)) : null
                );
            }
        }
    }

    public static void main(String[] args) {
        new Gui(Main.gameBoard);
    }
}
