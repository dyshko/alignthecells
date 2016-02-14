package com.alignthecells.logic;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.arraycopy;

/**
 * Created by Sergey on 09.05.2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class SquareBoard {


    public static final int RANDOM_SHUFFLES_ROUNDS = 5;
    public GAME_MODE mode;
    //The number of elements in a side
    protected int size;
    //the board itself
    protected int[][] board;
    public SquareBoard(int size, GAME_MODE mode) {
        this.size = size;
        trivialFillBoard();
        this.mode = mode;
    }

    public SquareBoard(SquareBoard oldBoard) {
        this.size = oldBoard.size;
        this.mode = oldBoard.mode;
        this.board = new int[size][size];
        for (int i = 0; i < size; i++)
            System.arraycopy(oldBoard.board[i], 0, this.board[i], 0, size);
    }

    public static boolean isOnBoarder(int i, int j, int size) {
        return (i == 0) || (i == size - 1) || (j == 0) || (j == size - 1);
    }

    public static int[] getVectorOut(int i, int j, int size) {
        int dx = 0;
        int dy = 0;
        if (!isOnBoarder(i, j, size)) return null;
        else {
            if (i == 0) {
                dx = -1;
                if (j == 0)
                    dy = -1;
                else if (j == size - 1)
                    dy = 1;
            } else if (i == size - 1) {
                dx = 1;
                if (j == 0)
                    dy = -1;
                else if (j == size - 1)
                    dy = 1;
            } else {
                if (j == 0)
                    dy = -1;
                else if (j == size - 1)
                    dy = +1;
            }
        }
        return new int[]{dx, dy};
    }

    public int getSize() {
        return size;
    }

    public void trivialFillBoard() {
        board = new int[size][size];
        for (int i = 0; i < size * size; i++) {
            board[i / size][i % size] = i;
        }
    }

 /*   public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out.print(board[i][j] + " ");
            }
            out.println();
        }
    }*/

    // +1 power shift is to the left
    public void shiftHorizontalSimple(int row, Shift.DIRECTION direction) {
        int pow = 0;
        switch (direction) {
            case FORWARD:
                pow = 1;
                break;
            case BACKWARD:
                pow = -1;
                break;
            default:
        }
        int[] temp = new int[size];
        arraycopy(board[row], 0, temp, 0, size);
        for (int i = 0; i < size; i++) {
            int val = (i - pow) % size;
            if (val < 0) val += size;
            board[row][i] = temp[val];
        }
    }

    // +1 power shift is to the up
    public void shiftVerticalSimple(int column, Shift.DIRECTION direction) {
        int pow = 0;
        switch (direction) {
            case FORWARD:
                pow = 1;
                break;
            case BACKWARD:
                pow = -1;
                break;
            default:
        }
        int[] temp = new int[size];
        for (int i = 0; i < size; i++) {
            temp[i] = board[i][column];
        }
        for (int i = 0; i < size; i++) {
            int val = (i - pow) % size;
            if (val < 0) val += size;
            board[i][column] = temp[val];
        }
    }

    public void shiftRandom() {
        Random r = new Random();
        for (int j = 0; j < RANDOM_SHUFFLES_ROUNDS * 2 * size; j++) {
            shift(new Shift(Shift.getRandomType(r), r.nextInt(size), Shift.getRandomDirection(r)));
        }
    }

    public int getValue(int row_number, int column_number) {
        return board[(row_number + size) % size][(column_number + size) % size];
    }

    public void shift(Shift initShift) {
        ArrayList<Shift> list = getFullShift(initShift);
        for (Shift s : list) {
            switch (s.shiftOption) {
                case SIMPLE: {
                    switch (s.shiftType) {
                        case COLUMN:
                            shiftVerticalSimple(s.position1, s.direction);
                            break;
                        case ROW:
                            shiftHorizontalSimple(s.position1, s.direction);
                            break;
                        default:
                    }
                }
                break;
                case CROSSED: {
                    shiftCrossed(s.position1, s.position2, s.direction, s.shiftType);
                }
                break;
                case CYCLIC: {
                    shiftCyclic(s.position1, s.position2, s.direction, s.shiftType);
                }
                break;
                default:
            }
        }
    }

    private void shiftCrossedRow(int pos1, int pos2, Shift.DIRECTION direction) {
        int[] t1 = new int[size];
        int[] t2 = new int[size];
        System.arraycopy(board[pos1], 0, t1, 0, size);
        System.arraycopy(board[pos2], 0, t2, 0, size);
        switch (direction) {
            case FORWARD:
                for (int i = 1; i < size; i++) {
                    board[pos1][i] = t1[i - 1];
                    board[pos2][i] = t2[i - 1];
                }
                board[pos1][0] = t2[size - 1];
                board[pos2][0] = t1[size - 1];
                break;
            case BACKWARD:
                for (int i = 0; i < size - 1; i++) {
                    board[pos1][i] = t1[i + 1];
                    board[pos2][i] = t2[i + 1];
                }
                board[pos1][size - 1] = t2[0];
                board[pos2][size - 1] = t1[0];
                break;
            default:
        }
    }

    private void shiftCyclicRowForward(int pos1, int pos2) {
        int[] t1 = new int[size];
        int[] t2 = new int[size];
        System.arraycopy(board[pos1], 0, t1, 0, size);
        System.arraycopy(board[pos2], 0, t2, 0, size);

        System.arraycopy(t1, 0, board[pos1], 1, size - 1);
        System.arraycopy(t2, 1, board[pos2], 0, size - 1);
        board[pos1][0] = t2[0];
        board[pos2][size - 1] = t1[size - 1];
    }

    private void shiftCyclicRow(int pos1, int pos2, Shift.DIRECTION direction) {
        switch (direction) {
            case FORWARD:
                shiftCyclicRowForward(pos1, pos2);
                break;
            case BACKWARD:
                shiftCyclicRowForward(pos2, pos1);
                break;
            default:
        }
    }

    private void shiftCrossed(int pos1, int pos2, Shift.DIRECTION direction, Shift.TYPE type) {
        switch (type) {
            case ROW: {
                shiftCrossedRow(pos1, pos2, direction);
            }
            break;
            case COLUMN: {
                transpose();
                shiftCrossedRow(pos1, pos2, direction);
                transpose();
            }
            break;
            default:
        }
        // boolean b = isOK();
    }

    private void shiftCyclic(int pos1, int pos2, Shift.DIRECTION direction, Shift.TYPE type) {
        switch (type) {
            case ROW: {
                shiftCyclicRow(pos1, pos2, direction);
            }
            break;
            case COLUMN: {
                transpose();
                shiftCyclicRow(pos1, pos2, direction);
                transpose();
            }
            break;
            default:
        }
        // boolean b = isOK();
    }

    /*
        @returns pair (row, col) coordinates
     */
    public int[] getPositionFromValue(int val) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == val)
                    return new int[]{i, j};
        return null;
    }

    public ArrayList<Shift> getFullShift(Shift initShift) {
        Shift.TYPE t = initShift.shiftType;
        int pos = initShift.position1;
        Shift.DIRECTION direction = initShift.direction;
        ArrayList<Shift> res = new ArrayList<>();
        res.add(initShift);
        switch (mode) {
            case TOR:
                break;

            case DOUBLE_DIRECT:
                res.add(new Shift(t, (pos + 1) % size, direction));
                break;

            case DOUBLE_REVERSED:
                res.add(new Shift(t, (pos + 1) % size, Shift.getOpositeDirection(direction)));
                break;
            case TRIPLE_DIRECT: {
                res.add(new Shift(t, (pos + 1) % size, direction));
                res.add(new Shift(t, (pos - 1 + size) % size, direction));
            }
            break;
            case TRIPLE_REVERSED: {
                res.add(new Shift(t, (pos + 1) % size, Shift.getOpositeDirection(direction)));
                res.add(new Shift(t, (pos - 1 + size) % size, Shift.getOpositeDirection(direction)));
            }
            break;
            case SPHERE_NR: {
                switch (t) {
                    case COLUMN:
                        break;
                    case ROW:
                        if (!((size % 2 == 1) && (2 * pos + 1 == size)))
                            res.add(new Shift(t, (size - pos - 1), Shift.getOpositeDirection(direction)));
                        else res.remove(initShift);
                        break;
                    default:
                }
            }
            break;
            case BOTTLE_NR: {
                switch (t) {
                    case COLUMN:
                        break;
                    case ROW:
                        if (!((size % 2 == 1) && (2 * pos + 1 == size)))
                            res.add(new Shift(t, (size - pos - 1), direction));
                        break;
                    default:
                }
            }
            break;
            case BOTTLE: {
                switch (t) {
                    case COLUMN:
                        break;
                    case ROW:
                        if (2 * pos + 1 != size) {
                            res.remove(initShift);
                            int p = initShift.position1;
                            res.add(Shift.getCrossedFromSimple(initShift, p, size - p - 1));
                        }
                        break;
                    default:
                }
            }
            break;
            case SPHERE: {
                switch (t) {
                    case COLUMN:
                        break;
                    case ROW:
                        res.remove(initShift);
                        if (2 * pos + 1 != size) {
                            int p = initShift.position1;
                            res.add(Shift.getCycledFromSimple(initShift, p, size - p - 1));
                        }
                        break;
                    default:
                }
            }
            break;
            case REAL_PROJECTIVE_PLANE: {
                res.remove(initShift);
                if (2 * pos + 1 != size) {
                    int p = initShift.position1;
                    res.add(Shift.getCycledFromSimple(initShift, p, size - p - 1));
                }
            }
            break;
            default:
        }
        return res;
    }

/*    //just for testing
    private boolean isOK() {
        int[] chk = new int[size * size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                chk[board[i][j]] += 1;
        for (int i = 0; i < size * size; i++)
            if (chk[i] != 1) return false;
        return true;
    }*/

    private void transpose() {
        int t;
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++) {
                t = board[i][j];
                board[i][j] = board[j][i];
                board[j][i] = t;
            }
    }

    public int getColumnFromValue(int value) {
        int[] p = getPositionFromValue(value);
        return p[1];
    }

    public int getRowFromValue(int value) {
        int[] p = getPositionFromValue(value);
        return p[0];
    }

    public enum GAME_MODE {
        TOR, BOTTLE_NR, SPHERE_NR, DOUBLE_DIRECT, DOUBLE_REVERSED, TRIPLE_DIRECT,
        TRIPLE_REVERSED, BOTTLE, SPHERE, REAL_PROJECTIVE_PLANE
    }


}
