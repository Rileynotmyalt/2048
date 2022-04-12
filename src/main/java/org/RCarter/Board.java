package org.RCarter;

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

//        for (int y = 0; y < cells.length; y++) {
//            if (y != 0) { // from the bottom row up excluding the bottom row
//                for (int w = 0; w < cells[0].length; w++) {
//                    if (cells[y][w].block != null) { // check if there is a block in that row
//
//                        int nextLoc = y - 1;
//
//                        // iterates up until it finds a block or runs out of room
//                        while (true) {
//
//                            if (cells[nextLoc][w].block != null ) {
//                                // if the block in question is able to combine with the block it runs into
//                                if (cells[y][w].block.val == cells[nextLoc][w].block.val) {
//
//                                    cells[nextLoc][w].block.val = cells[y][w].block.val + 1;
//                                    cells[y][w].block = null;
//
//                                } else if (cells[nextLoc+1][w].block != cells[y][w].block) {
//                                    cells[nextLoc+1][w].block = cells[y][w].block;
//                                    cells[y][w].block = null;
//                                }
//                                break;
//                            }
//
//                            if (nextLoc == 0) { // if at the top
//                                cells[nextLoc][w].block = cells[y][w].block;
//                                cells[y][w].block = null;
//                                break;
//                            }
//
//                            nextLoc--;
//                        }
//                    }
//                }
//            }
//        }

        spawnBlock();

        System.out.println(this);
    }

    void down() {

        for (int y = cells.length; y >= 0; y--) {
            if (y < cells.length-1) { // from the bottom row up excluding the bottom row
                for (int w = 0; w < cells[0].length; w++) {
                    if (cells[y][w].block != null) { // check if there is a block in that row

                        int nextLoc = y + 1;

                        // iterates down until it finds a block or runs out of room
                        while (true) {

                            // if PAST bottom
                            if (nextLoc == cells[0].length) {
                                cells[nextLoc-1][w].block = cells[y][w].block;
                                cells[y][w].block = null;
                                break;
                            }

                            if (cells[nextLoc][w].block != null ) {
                                // if the block in question is able to combine with the block it runs into
                                if (cells[y][w].block.val == cells[nextLoc][w].block.val) {

                                    cells[nextLoc][w].block.val = cells[y][w].block.val + 1;
                                    cells[y][w].block = null;

                                } else if (cells[nextLoc-1][w].block != cells[y][w].block) {
                                    // if prev loc is not the current block
                                    cells[nextLoc-1][w].block = cells[y][w].block;
                                    cells[y][w].block = null;
                                }
                                break;
                            }

                            nextLoc++;
                        }

                    }
                }
            }
        }

        spawnBlock();

        System.out.println(this);
    }

    void left() {

        for (int w = 0; w < cells[0].length; w++) {
            if (w != 0) { // left to right, it thru columns skip far left
                for (int y = 0; y < cells.length; y++) { // for each row (start: TOP)
                    if (cells[y][w].block != null) { // check if there is a block in that column

                        int nextLoc = w - 1;

                        // iterates up until it finds a block or runs out of room
                        while (true) {

                            // if find block
                            if (cells[y][nextLoc].block != null ) {

                                // if can combine
                                if (cells[y][w].block.val == cells[y][nextLoc].block.val) {

                                    cells[y][nextLoc].block.val = cells[y][w].block.val + 1;
                                    cells[y][w].block = null;

                                } else if (cells[y][nextLoc+1].block != cells[y][w].block){ // go to the previous block and place it
                                    cells[y][nextLoc+1].block = cells[y][w].block;
                                    cells[y][w].block = null;
                                }
                                break;
                            }

                            // if at the top and has not found block
                            if (nextLoc == 0) {
                                cells[y][nextLoc].block = cells[y][w].block;
                                cells[y][w].block = null;
                                break;
                            }

                            nextLoc--;
                        }

                    }
                }
            }
        }

        spawnBlock();

        System.out.println(this);
    }

    void right() {

        for (int w = cells[0].length; w >= 0; w--) { // from right to left
            if (w < cells.length-1) { // exclude far right
                for (int y = 0; y < cells.length; y++) { // from top to bottom
                    if (cells[y][w].block != null) { // check if there is a block in that row

                        int nextLoc = w + 1;

                        // iterates down until it finds a block or runs out of room
                        while (true) {

                            if (nextLoc == cells.length) { // if no blocks in row
                                cells[y][nextLoc-1].block = cells[y][w].block;
                                cells[y][w].block = null;
                                break;
                            }

                            // if next loc is block
                            if (cells[y][nextLoc].block != null ) {
                                // if the block in question is able to combine with the block it runs into
                                if (cells[y][w].block.val == cells[y][nextLoc].block.val) {

                                    cells[y][nextLoc].block.val = cells[y][w].block.val + 1;
                                    cells[y][w].block = null;

                                } else if (cells[y][nextLoc-1].block != cells[y][w].block){
                                    cells[y][nextLoc-1].block = cells[y][w].block;
                                    cells[y][w].block = null;
                                }
                                break;
                            }

                            nextLoc++;
                        }

                    }
                }
            }
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


    private void list_to_row(Cell[][] cells, Direction dir, Cell[] row, int i) {
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
