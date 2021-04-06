package main;

import elements.Tile;

public class Move {
    public static void set(Tile[][] board, int x1, int y1, int x2, int y2) {
        board[x1][y1].getElement().move(x2, y2);
        board[x2][y2].setElement(board[x1][y1].getElement());
        board[x1][y1].setElement(null);
    }
}
