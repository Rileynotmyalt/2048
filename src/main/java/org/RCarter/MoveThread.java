package org.RCarter;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public class MoveThread implements Callable<Boolean> {
    private Thread thread;
    private final Cell[] row;
    private boolean processPerformed;

    public MoveThread(@NotNull Cell[] row) {
        this.row = row;
    }

    @Override
    public Boolean call() {
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

                            // sets processPerformed to true because an action was performed
                            processPerformed = true;

                        // if not combinable and the previous location is not the current block
                        } else if (row[nextLoc +1].block != row[i].block) {
                            // move to the previous location
                            row[nextLoc+1].block = row[i].block;
                            row[i].block = null;

                            // sets processPerformed to true because an action was performed
                            processPerformed = true;

                        // if the previous location is the current block nothing changes
                        }
                        break;
                    }

                    if (nextLoc == 0) {
                        row[nextLoc].block = row[i].block;
                        row[i].block = null;

                        // sets processPerformed to true because an action was performed
                        processPerformed = true;

                        break;
                    }

                    nextLoc--;
                }
            }
        }

        // processPerformed will be set to true if any movement occurs any time
        // if no actions are performed, processPerformed will be false
        return processPerformed;
    }
}
