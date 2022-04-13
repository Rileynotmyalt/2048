package org.RCarter;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class FindEmptySpaces implements Callable<ArrayList<int[]>> {

    private final Cell[] row;
    private final int rowIndex;
    ArrayList<int[]> emptyCells = new ArrayList<>();

    /*
    * Finds and Returns a list of coordinates spaces in the given row that are empty
    * you DO NOT need to translate the rows like you do in movement,
    * the coordinates apply directly to the board
     */
    FindEmptySpaces(Cell[] row, int rowIndex) {
        this.row = row;
        this.rowIndex = rowIndex;
    }

    @Override
    public ArrayList<int[]> call() {
        for (int i = 0; i < row.length; i++) {
            if (row[i].block == null){
                emptyCells.add(new int[]{i, rowIndex});
            }
        }
        return emptyCells;
    }
}
