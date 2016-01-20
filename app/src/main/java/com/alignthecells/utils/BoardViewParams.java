package com.alignthecells.utils;

/**
 * Created by Sergey on 8/17/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class BoardViewParams {

    public int cellSpace;

    public int sideLength;

    //Number of cells is size^2
    public int size;

    public int cellSideLength;

    //ratio = cellLength/cellSpace
    public BoardViewParams(float ratio, int sideLength, int size) {
        this.cellSpace = (int) (sideLength / ((ratio + 1) * size - 1));
        this.size = size;
        cellSideLength = (int) (ratio * cellSpace);
        this.sideLength = cellSideLength * size + cellSpace * (size - 1);
    }


}
