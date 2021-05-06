package main;

import elements.Piece;
import elements.Tile;

import static main.Globals.*;

public class Move {
    public static void set(Tile[][] board, int x1, int y1, int x2, int y2) {
        for (int i = x1, j = y1; (!(x2 > x1 || y2 > y1) || (i <= x2 && j <= y2)) && (!(x2 < x1 || y2 < y1) || (i >= x2 && j >= y2)); i += Integer.signum(x2 - x1), j += Integer.signum(y2 - y1)) {
            if (board[i][j].hasElement()) {
                if (board[i][j].hasElement() && !(board[i][j].getElement() instanceof Piece))
                    board[i][j].getElement().setVisible(false);

            }
        }

        if (board[x1][y1].getElement() instanceof Piece && ((Piece)board[x1][y1].getElement()).getPieceId() == MY_ID) {
            MOVED = true;
            LIMIT = -1;
            FROM = x1 + "|" + y1;
            TO = x2 + "|" + y2;
        }

        board[x1][y1].getElement().move(x2, y2);
        board[x2][y2].setElement(board[x1][y1].getElement());
        board[x1][y1].setElement(null);
    }
}
