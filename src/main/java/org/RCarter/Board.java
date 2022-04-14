package org.RCarter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import org.jetbrains.annotations.NotNull;

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

    void up() {
        ArrayList<Callable<Boolean>> moveThreads = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Boolean>> actionsPerformed = null;
        boolean processPerformed = false;

        for (int i = 0; i < cells.length; i++) {
            moveThreads.add(
                    new MoveThread(row_to_list(cells, Direction.UP, i))
            );
        }

        try {
            actionsPerformed = executorService.invokeAll(moveThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // for each row if any process was performed in any row process will be set to true
        for (Future<Boolean> actionDone: actionsPerformed) {
            try {
                if(actionDone.get()) {
                    processPerformed = true;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // if any process was performed in any row, process will be set to true
        if (processPerformed) {
            spawnBlock();
        }

        System.out.println(this);

        executorService.shutdown();
    }

    void down() {
        ArrayList<Callable<Boolean>> moveThreads = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Boolean>> actionsPerformed = null;
        boolean processPerformed = false;

        for (int i = 0; i < cells.length; i++) {
            moveThreads.add(
                    new MoveThread(row_to_list(cells, Direction.DOWN, i))
            );
        }

        try {
            actionsPerformed = executorService.invokeAll(moveThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // for each row if any process was performed in any row process will be set to true
        for (Future<Boolean> actionDone: actionsPerformed) {
            try {
                if(actionDone.get()) {
                    processPerformed = true;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // if any process was performed in any row, process will be set to true
        if (processPerformed) {
            spawnBlock();
        }

        System.out.println(this);

        executorService.shutdown();
    }

    void left() {
        ArrayList<Callable<Boolean>> moveThreads = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Boolean>> actionsPerformed = null;
        boolean processPerformed = false;

        for (int i = 0; i < cells[0].length; i++) {
            moveThreads.add(
                    new MoveThread(row_to_list(cells, Direction.LEFT, i))
            );
        }

        try {
            actionsPerformed = executorService.invokeAll(moveThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // for each row if any process was performed in any row process will be set to true
        assert actionsPerformed != null;
        for (Future<Boolean> actionDone: actionsPerformed) {
            try {
                if(actionDone.get()) {
                    processPerformed = true;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // if any process was performed in any row, process will be set to true
        if (processPerformed) {
            spawnBlock();
        }

        System.out.println(this);

        executorService.shutdown();
    }

    void right() {
        ArrayList<Callable<Boolean>> moveThreads = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Boolean>> actionsPerformed = null;
        boolean processPerformed = false;

        for (int i = 0; i < cells.length; i++) {
            moveThreads.add(
                    new MoveThread(row_to_list(cells, Direction.RIGHT, i))
            );
        }

        try {
            actionsPerformed = executorService.invokeAll(moveThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // for each row if any process was performed in any row process will be set to true
        for (Future<Boolean> actionDone: actionsPerformed) {
            try {
                if(actionDone.get()) {
                    processPerformed = true;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        // if any process was performed in any row, process will be set to true
        if (processPerformed) {
            spawnBlock();
        }

        System.out.println(this);

        executorService.shutdown();
    }



    // utils

    void spawnBlock() {
        Random rand = new Random();
        ArrayList<FindEmptySpaces> emptySpaceFinders = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<ArrayList<int[]>>> emptySpaceLists = null;
        ArrayList<int[]> emptySpaces = new ArrayList<>();

        // for each row
        for (int i = 0; i < cells.length; i++) {
            // create a new callable thread to search that row
            emptySpaceFinders.add(new FindEmptySpaces(cells[i], i));
        }

        // start all parallel callables
        try {
            emptySpaceLists = executorService.invokeAll(emptySpaceFinders);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // get items from threads and append them to one list
        assert emptySpaceLists != null;
        for (Future<ArrayList<int[]>> emptySpaceList : emptySpaceLists) {
            try {
                emptySpaces.addAll(emptySpaceList.get());

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        // now we have the list of empty spaces, now do stuff with it

        // check if the list is empty, if empty end game
        if (emptySpaces.size() == 0) {gameOver();}

        // get random coordinate from given list
        int[] randomCoordinate = emptySpaces.get(rand.nextInt(emptySpaces.size()));
        int x = randomCoordinate[0];
        int y = randomCoordinate[1];

        // set the selected cell coordinate to a value
        int blockValue = rand.nextInt(2)+1;

        cells[y][x].block = new Block(blockValue);

        executorService.shutdown();
    }

    private void gameOver() {

    }

    int[] randomPoint() {
        Random rand = new Random();
        return new int[]{
                rand.nextInt(cells[0].length),
                rand.nextInt(cells.length)
        };
    }

    public Cell[] row_to_list(Cell[][] board, @NotNull Direction dir, int rowIndex) {
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
                for (int i = board.length-1; 0 <= i; i--) {
                    row[(board.length - 1) - i] = board[i][rowIndex];
                }
            }

            case LEFT -> row = board[rowIndex];

            case RIGHT -> {
                row = new Cell[board[0].length];

                // for this row from right to left
                for (int i = board[0].length-1; 0 <= i; i--) {
                    row[(board.length - 1) - i] = board[rowIndex][i];
                }
            }
            default -> throw new IllegalArgumentException("Invalid Direction");
        }

        return row;
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
