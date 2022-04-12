package org.RCarter.Move;

import org.RCarter.Cell;

public class MoveThread implements Runnable{
    private Thread thread;
    private Cell[] row;

    public MoveThread(Cell[] row) {
        this.row = row;
    }

    @Override
    public void run() {
        // from top to bottom, skipping the first row
        for (int i = 1; i < row.length; i++) {
            // if cell has a block
            if (row[i].block != null) {

                int nextLoc = i - 1;

                // iterate back down the list check for combinable
                while (true) {

                    // if the cell has a block
                    if (row[nextLoc].block != null) {
                        // if combinable
                        if (row[i].block.val == row[nextLoc].block.val) {
                            row[nextLoc].block.val = row[nextLoc].block.val +1;
                            row[i].block = null;

                        // if not combinable and the previous location is not the current block
                        } else if (row[nextLoc +1].block != row[i].block) {
                            // move to the previous location
                            row[nextLoc+1].block = row[i].block;
                            row[i].block = null;
                        }
                        // if the previous location is the current block nothing changes
                        break;
                    }

                    // TODO if at the top
                    if (nextLoc == 0) {
                        row[nextLoc].block = row[i].block;
                        row[i].block = null;
                        break;
                    }

                    nextLoc--;
                }
            }
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public Cell[] getRow() {
        return row;
    }
}
