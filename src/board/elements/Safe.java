package board.elements;

import board.Board;
import board.ElementType;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import static board.Config.*;

public class Safe extends StackPane {
    public Safe(int x,int y){
        Rectangle r=new Rectangle(TILE_SIZE,TILE_SIZE);
        r.setOpacity(0.5);
        r.setFill( Color.valueOf("#34a77a"));
        move(x,y);
        getChildren().addAll(r);
        setVisible(false);

        setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));

        setOnDragDropped(e -> {
            var data = e.getDragboard().getString();
            var _x = Integer.parseInt(data.substring(0, data.indexOf("|")));
            var _y = Integer.parseInt(data.substring(data.indexOf("|") + 1));
            System.out.println(_x+" "+_y);
            int changedX = (int) e.getSceneX() / TILE_SIZE;
            int changedY = (int) e.getSceneY() / TILE_SIZE;
            if(Board.board[changedX][changedY].hasElement()) {
                if (Board.board[changedX][changedY].getElement().getType() == ElementType.STAR)
                    Board.board[changedX][changedY].getElement().star.move(-1, -1);
                if (Board.board[changedX][changedY].getElement().getType() == ElementType.SLOW)
                    Board.board[changedX][changedY].getElement().slow.move(-1, -1);
            }
            Board.board[_x][_y].getElement().piece.move(changedX, changedY);
            Board.board[changedX][changedY].setElement(Board.board[_x][_y].getElement());
            Board.board[_x][_y].setElement(null);
            for(int i =0;i<WIDTH;i++)
                Board.safeMargin[i][_y].setVisible(false);

            for(int j=0;j<HEIGHT;j++)
                Board.safeMargin[_x][j].setVisible(false);
        });
    }
    public void move(int x, int y) {

        relocate(x* TILE_SIZE, y* TILE_SIZE);
    }

}
