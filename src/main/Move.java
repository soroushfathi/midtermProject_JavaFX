package main;

import elements.ElementType;
import elements.Tile;

import static main.Config.*;
public class Move {
    public static void set(Tile[][] board, int x1, int y1, int x2, int y2) {
        MOVED=true;
        FROM=x1+"|"+y1;
        TO=x2+"|"+y2;
        if(board[x1][y1].hasElement() && board[x1][y1].getElement().getType()== ElementType.PIECE) {
            board[x1][y1].getElement().move(x2, y2);
            board[x2][y2].setElement(board[x1][y1].getElement());
            board[x1][y1].setElement(null);
        }
    }
}
