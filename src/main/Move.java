package main;

import elements.ElementType;
import elements.Tile;
import pages.Board;

import static main.Config.*;
public class Move {
    public static void set(Tile[][] board, int x1, int y1, int x2, int y2) {
        FROM=x1+"|"+y1;
        TO=x2+"|"+y2;
        if(board[x1][y1].hasElement() && board[x1][y1].getElement().getType()== ElementType.PIECE) {
            for(int i=x1,j=y1;(!(x2>x1 || y2>y1)||(i<=x2&&j<=y2)) && (!(x2<x1 || y2<y1)||i>=x2&&j>=y2);i+=Integer.signum(x2-x1),j+=Integer.signum(y2-y1)) {
                if (board[i][j].hasElement()) {
                    if (board[i][j].getElement().getType() == ElementType.STAR)
                        board[i][j].getElement().setVisible(false);
                    if (board[i][j].getElement().getType() == ElementType.SLOW)
                        board[i][j].getElement().setVisible(false);
                }
            }

            MOVED=board[x1][y1].getElement().id == MY_ID;
            board[x1][y1].getElement().move(x2, y2);
            board[x2][y2].setElement(board[x1][y1].getElement());
            board[x1][y1].setElement(null);
        }
    }
}
