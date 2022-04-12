package org.RCarter;

import java.util.Arrays;
import java.util.Random;
import org.RCarter.Main.Direction;
import org.RCarter.Move.MoveThread;

public class Board {
    public Cell[][] cells;
    Board(int height, int width) {
        this.cells = new Cell[height][width];

        for (Cell[] row : cells) {
            for (int w = 0; w < width; w++) {
                Cell tempCell = new Cell();
                tempCell.block = null;
                row[w] = tempCell;
            }
        }

        spawnBlock();
    }

    void spawnBlock() {
        Random rand = new Random();

        while(true) {
            int[] point = randomPoint();

            if (cells[point[1]][point[0]].block == null) {
                cells[point[1]][point[0]].block = new Block(rand.nextInt(2)+1);
                break;
            }

        }
    }

    void up() {
        for (int i = 0; i < cells[0].length; i++) {
            MoveThread thread = new MoveThread(row_to_list(cells, Direction.UP, i));
            thread.start();
            list_to_row(cells, Direction.UP, thread.getRow(), i);
        }

        spawnBlock();

        System.out.println(this);
    }

    void down() {

        for (int i = 0; i < cells[0].length; i++) {
            MoveThread thread = new MoveThread(row_to_list(cells, Direction.DOWN, i));
            thread.start();
            list_to_row(cells, Direction.DOWN, thread.getRow(), i);
        }

        spawnBlock();

        System.out.println(this);
    }

    void left() {

        for (int i = 0; i < cells[0].length; i++) {
            MoveThread thread = new MoveThread(row_to_list(cells, Direction.LEFT, i));
            thread.start();
            list_to_row(cells, Direction.LEFT, thread.getRow(), i);
        }

        spawnBlock();

        System.out.println(this);
    }

    void right() {

        for (int i = 0; i < cells[0].length; i++) {
            MoveThread thread = new MoveThread(row_to_list(cells, Direction.RIGHT, i));
            thread.start();
            list_to_row(cells, Direction.RIGHT, thread.getRow(), i);
        }

        spawnBlock();

        System.out.println(this);
    }


    // utils

    int[] randomPoint() {
        Random rand = new Random();
        return new int[]{
                rand.nextInt(cells[0].length),
                rand.nextInt(cells.length)
        };
    }
    // TODO row to list (one func use enums)

    public Cell[] row_to_list(Cell[][] board, Direction dir, int rowIndex) {
        Cell[] row;
        switch (dir){
            case UP -> {
                row = new Cell[board.length];

                // for this column from top to bottom
                for (int i = 0; i < board.length; i++) {
                    row[i] = board[i][rowIndex];
                }
            }
            case DOWN -> {
                row = new Cell[board.length];

                // for each column from top to bottom
                for (int i = board.length-1; 0 < i; i--) {
                    row[(board.length - 1) - i] = board[i][rowIndex];
                }
            }
            case LEFT -> {
                row = board[rowIndex];
            }
            case RIGHT -> {
                row = new Cell[board[0].length];

                // for this row from right to left
                for (int i = board[0].length-1; 0 < i; i--) {
                    row[(board.length - 1) - i] = board[rowIndex][i];
                }
            }
            default -> throw new IllegalArgumentException("Invalid Direction");
        }

        return row;
    }


    private void list_to_row(Cell[][] cells, Direction dir, Cell[] row, int rowIndex) {
        switch (dir) {
            case UP -> {
                // if up, input is a list from top to bottom in the given column
                for (int i = 0; i < row.length; i++) {
                    cells[i][rowIndex] = row[i];
                }
            }
            case DOWN -> {
                // if down, input is a list from bottom to top in the given column
                for (int i = 0; i < row.length; i++) {
                    cells[(row.length - 1) - i][rowIndex] = row[i];
                }
            }
            case LEFT -> {
                cells[rowIndex] = row;
            }
            case RIGHT -> {
                // if right, input is a list from right to left in the given row
                for (int i = 0; i < row.length; i++) {
                    cells[rowIndex][(row.length - 1) - i] = row[i];
                }

            }
            default -> throw new IllegalArgumentException("Invalid Direction");
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (Cell[] row : this.cells) {
            for (Cell cell : row) {
                str.append(String.format("|%6s",
                        (cell.block != null) ? (int)Math.pow(2, cell.block.val) : ""
                ));
            }
            str.append("|\n");
        }

        return str.toString();
    }
}
