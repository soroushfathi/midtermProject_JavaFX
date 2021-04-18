package ai;

import elements.ElementType;
import elements.Piece;
import elements.Tile;
import main.Globals;
import main.Move;


import static main.Config.*;

public class MiniMax {


    public static int miniMax(Tile[][] board,int x1,int y1, int depth, boolean isMax){
        int boardVal = evaluateBoard(board,x1,y1,isMax);
        if (Math.abs(boardVal) == 10 || depth == 0) {
            return boardVal;
        }

        if (isMax) {
            Piece p = (Piece) Globals.players[1].getPiece();

            int highestVal = Integer.MIN_VALUE;

            for (int i = p.getX(); i < WIDTH && (!board[i][p.getY()].hasElement() || board[i][p.getY()].getElement().getType() != ElementType.WALL) && (Globals.LIMIT == -1 || (i - p.getX()) <= Globals.LIMIT); i++)
                highestVal = getHighestValX(board, depth, p, highestVal, i);
            for (int i = p.getX(); i >= 0 && (!board[i][p.getY()].hasElement() || board[i][p.getY()].getElement().getType() != ElementType.WALL) && (Globals.LIMIT == -1 || (p.getX() - i) <= Globals.LIMIT); i--)
                highestVal = getHighestValX(board, depth, p, highestVal, i);


            for (int j = p.getY(); j < HEIGHT && (!board[p.getX()][j].hasElement() || board[p.getX()][j].getElement().getType() != ElementType.WALL) && (Globals.LIMIT == -1 || (j - p.getY()) <= Globals.LIMIT); j++)
                highestVal = getHighestValY(board, depth, p, highestVal, j);
            for (int j = p.getY(); j >= 0 && (!board[p.getX()][j].hasElement() || board[p.getX()][j].getElement().getType() != ElementType.WALL) && (Globals.LIMIT == -1 || (p.getY() - j) <= Globals.LIMIT); j--)
                highestVal = getHighestValY(board, depth, p, highestVal, j);


            return highestVal;
        }
        else {
            Piece p = (Piece) Globals.players[0].getPiece();

            int lowestVal = Integer.MAX_VALUE;

            for (int i = p.getX(); i < WIDTH && (!board[i][p.getY()].hasElement() || board[i][p.getY()].getElement().getType() != ElementType.WALL) && (Globals.LIMIT == -1 || (i - p.getX()) <= Globals.LIMIT); i++)
                lowestVal = getLowestValX(board, depth, p, lowestVal, i);
            for (int i = p.getX(); i >= 0 && (!board[i][p.getY()].hasElement() || board[i][p.getY()].getElement().getType() != ElementType.WALL) && (Globals.LIMIT == -1 || (p.getX() - i) <= Globals.LIMIT); i--)
                lowestVal = getLowestValX(board, depth, p, lowestVal, i);



            for (int j = p.getY(); j < HEIGHT && (!board[p.getX()][j].hasElement() || board[p.getX()][j].getElement().getType() != ElementType.WALL) && (Globals.LIMIT == -1 || (j - p.getY()) <= Globals.LIMIT); j++)
                lowestVal = getLowestValY(board, depth, p, lowestVal, j);
            for (int j = p.getY(); j >= 0 && (!board[p.getX()][j].hasElement() || board[p.getX()][j].getElement().getType() != ElementType.WALL) && (Globals.LIMIT == -1 || (p.getY() - j) <= Globals.LIMIT); j--)
                lowestVal = getLowestValY(board, depth, p, lowestVal, j);

            return lowestVal;
        }
    }

    public static int getHighestValX(Tile[][] board, int depth, Piece p, int highestVal, int i) {
        if (!board[i][p.getY()].hasElement() || board[i][p.getY()].getElement().getType() == ElementType.SLOW || board[i][p.getY()].getElement().getType() == ElementType.STAR) {
            var x1 = p.getX();
            var y1 = p.getY();
            Move.set(board, x1, y1, i, y1);
            highestVal = Math.max(highestVal, miniMax(board, x1, y1, depth - 1, false));
            Move.set(board, i, y1, x1, y1);
        }
        return highestVal;
    }

    public static int getLowestValX(Tile[][] board, int depth, Piece p, int lowestVal, int i) {
        if (!board[i][p.getY()].hasElement() || board[i][p.getY()].getElement().getType() == ElementType.SLOW || board[i][p.getY()].getElement().getType() == ElementType.STAR) {
            var x1 = p.getX();
            var y1 = p.getY();
            Move.set(board, x1, y1, i, y1);
            lowestVal = Math.min(lowestVal, miniMax(board, x1, y1, depth - 1, true));
            Move.set(board, i, y1, x1, y1);
        }
        return lowestVal;
    }

    public static int getLowestValY(Tile[][] board, int depth, Piece p, int lowestVal, int j) {
        if (!board[p.getX()][j].hasElement() || board[p.getX()][j].getElement().getType() == ElementType.SLOW || board[p.getX()][j].getElement().getType() == ElementType.STAR) {
            var x1 = p.getX();
            var y1 = p.getY();
            Move.set(board, x1, y1, x1, j);
            lowestVal = Math.min(lowestVal, miniMax(board, x1, y1, depth - 1, true));
            Move.set(board, x1, j, x1, y1);
        }
        return lowestVal;
    }

    public static int getHighestValY(Tile[][] board, int depth, Piece p, int highestVal, int j) {
        if (!board[p.getX()][j].hasElement() || board[p.getX()][j].getElement().getType() == ElementType.SLOW || board[p.getX()][j].getElement().getType() == ElementType.STAR) {
            var x1 = p.getX();
            var y1 = p.getY();
            Move.set(board, x1, y1, x1, j);
            highestVal = Math.max(highestVal, miniMax(board, x1, y1, depth - 1, false));
            Move.set(board, x1, j, x1, y1);
        }
        return highestVal;
    }


    private static int evaluateBoard(Tile[][] board,int x1,int y1, boolean isMax) {
        Piece p= (Piece) Globals.players[isMax?1:0].getPiece();
        var x2=p.getX();
        var y2= p.getY();
//                for (int i = x1, j = y1; (!(x2 > x1 || y2 > y1) || (i <= x2 && j <= y2)) && (!(x2 < x1 || y2 < y1) || i >= x2 && j >= y2); i += Integer.signum(x2 - x1), j += Integer.signum(y2 - y1)) {
//            if (board[i][j].hasElement()) {
//                if (board[i][j].getElement().getType() == ElementType.STAR) {
//                    board[i][j].getElement().setVisible(false);
//                }
//                if (board[i][j].getElement().getType() == ElementType.SLOW)
//                    board[i][j].getElement().setVisible(false);
//            }
//        }
        return 1;
    }
}
