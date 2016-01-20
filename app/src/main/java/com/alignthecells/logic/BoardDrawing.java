package com.alignthecells.logic;

import android.content.Context;
import android.graphics.Canvas;

import com.alignthecells.utils.BoardViewParams;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sergey on 9/1/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class BoardDrawing {

    Context context;

    BoardViewParams params;

    private ArrayList<Cell> cells;
    private ArrayList<Cell> dummyCells;



    public BoardDrawing(Context context, BoardViewParams params) {

        this.params = params;


        cells = new ArrayList<>();
        dummyCells = new ArrayList<>();

        this.context = context;

        fillBoard();
    }

    private void fillBoard() {
        for (int i = 0; i < params.size; i++)
            for (int j = 0; j < params.size; j++) {
                int val = j + params.size*i;
                Cell cell = new Cell(context, boardIndexToColor(val), val, params.cellSideLength);
                setCellPosition(cell, -1, -1);
                setCellDestination(cell, -1, -1);
                cells.add(cell);

         /*       if (GamePreferences.animationType2) {
                    CellView dummy = new CellView(getContext(), boardIndexToColor(val), val);
                    this.addView(dummy);

                    setCellPosition(dummy, -1, -1);

                    dummyCells.add(dummy);
                }*/
            }
    }



    public int getCellLeftMargin(int col) {
        return col * (params.cellSideLength + params.cellSpace);
    }

    public int getCellTopMargin(int row) {
        return row * (params.cellSideLength + params.cellSpace);
    }

    private Cell getCellFromValue(int value) {
        for (Cell cell : cells)
            if (cell.value == value) return cell;
        return null;
    }

   /* public CellView getDummyCellFromValue(int value) {
        for (CellView cell : dummyCells)
            if (cell.value == value) return cell;
        return null;
    }*/

    public ArrayList<Cell> getCells()
    {
        return cells;
    }

    public int boardIndexToColor(int val) {
        return val % params.size;
    }

    public void setCellPosition(Cell cell, int row, int col) {
        cell.setPosition(getCellLeftMargin(col), getCellTopMargin(row));
    }

    private void setCellDestination(Cell cell, int row, int col) {
        cell.setDestination(getCellLeftMargin(col), getCellTopMargin(row));
    }

    public void setupShift(SquareBoard newBoard, long currentTime, long duration) {
        for (Cell cell : cells) {
            int i = newBoard.getRowFromValue(cell.getValue());
            int j = newBoard.getColumnFromValue(cell.getValue());
            //assign animation here
         /*   if (SquareBoard.isOnBoarder(i, j, params.size)
                        && SquareBoard.isOnBoarder(iOld, jOld, size)
                        && (abs(i - iOld) + abs(j - jOld) > 1)
                        && GamePreferences.animationType2) {
                    int[] d = SquareBoard.getVectorOut(i, j, size);
                    int dx = d[0];
                    int dy = d[1];

                    Animation a1 = new TranslateAnimation(
                            boardView.getCellLeftMargin(j + dy) - cell.getLeft(),
                            boardView.getCellLeftMargin(j) - cell.getLeft(),
                            boardView.getCellTopMargin(i + dx) - cell.getTop(),
                            boardView.getCellTopMargin(i) - cell.getTop()
                    );

                    int[] d1 = SquareBoard.getVectorOut(iOld, jOld, size);
                    int dx1 = d1[0];
                    int dy1 = d1[1];

                    CellView dummy = boardView.getDummyCellFromValue(cell.getValue());
                    //dummy.setVisibility(VISIBLE);
                    Animation a2 = new TranslateAnimation(
                            boardView.getCellLeftMargin(jOld) - dummy.getLeft(),
                            boardView.getCellLeftMargin(jOld + dy1) - dummy.getLeft(),
                            boardView.getCellTopMargin(iOld) - dummy.getTop(),
                            boardView.getCellTopMargin(iOld + dx1) - dummy.getTop()
                    );

                    addAnimation(a1, cell);
                    addAnimation(a2, dummy);
            }
            else */
            cell.setMovement(getCellLeftMargin(j), getCellTopMargin(i), currentTime, duration);
        }
    }

    public void setRandomPosMovementOutside(Random r, boolean pos_true, long currentTime, long duration)
    {
        for (Cell cell: cells) {
            int rrow = 0, rcol = 0;
            while ((rrow >= 0)&&(rrow < params.size) && (rcol >= 0)&&(rcol < params.size)) {
                rrow = -params.size + r.nextInt(3*params.size);
                rcol = -params.size + r.nextInt(3*params.size);
            }
            if (pos_true) setCellPosition(cell, rrow, rcol);
            else cell.setMovement(getCellLeftMargin(rcol), getCellTopMargin(rrow), currentTime, duration);
        }
    }

    public void draw(Canvas canvas, long currentTime)
    {
        for (Cell cell : cells)
            cell.draw(canvas, currentTime);
    }
}