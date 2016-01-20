package com.alignthecells.logic;

import java.util.Random;

/**
 * Created by Sergey on 8/21/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class Shift {

    public enum TYPE {ROW, COLUMN}

    public enum OPTION {SIMPLE, CROSSED, CYCLIC}

    public enum DIRECTION {FORWARD, BACKWARD}

    public TYPE shiftType;
    public OPTION shiftOption;
    public int position1;
    public int position2;
    public DIRECTION direction;

    public Shift(TYPE shiftType, int position1, DIRECTION direction) {
        this.shiftOption = OPTION.SIMPLE;
        this.shiftType = shiftType;
        this.position1 = position1;
        this.direction = direction;
    }

    public Shift(TYPE shiftType, OPTION shiftOption, int position1, int position2, DIRECTION direction) {
        this.shiftOption = shiftOption;
        this.shiftType = shiftType;
        this.position1 = position1;
        this.position2 = position2;
        this.direction = direction;
    }

    public static DIRECTION getOpositeDirection(DIRECTION direction) {
        switch (direction) {
            case FORWARD:
                return DIRECTION.BACKWARD;
            case BACKWARD:
                return DIRECTION.FORWARD;
            default:
                return null;
        }
    }

    public static DIRECTION getRandomDirection(Random r) {
        int t = r.nextInt(2);
        switch (t) {
            case 0:
                return DIRECTION.FORWARD;
            case 1:
                return DIRECTION.BACKWARD;
            default:
                return DIRECTION.FORWARD;
        }
    }

    public static TYPE getRandomType(Random r) {
        int t = r.nextInt(2);
        switch (t) {
            case 0:
                return TYPE.ROW;
            case 1:
                return TYPE.COLUMN;
            default:
                return TYPE.ROW;
        }
    }

    public static Shift getCrossedFromSimple(Shift simpleShift, int p1, int p2) {
        int p;
        if (simpleShift.position1 == p1)
            p = p2;
        else if (simpleShift.position1 == p2)
            p = p1;
        else return null;
        return new Shift(simpleShift.shiftType, OPTION.CROSSED, simpleShift.position1, p, simpleShift.direction);
    }

    public static Shift getCycledFromSimple(Shift simpleShift, int p1, int p2) {
        int p;
        if (simpleShift.position1 == p1)
            p = p2;
        else if (simpleShift.position1 == p2)
            p = p1;
        else return null;
        return new Shift(simpleShift.shiftType, OPTION.CYCLIC, simpleShift.position1, p, simpleShift.direction);
    }


}
