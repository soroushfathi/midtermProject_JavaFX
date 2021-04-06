package elements;


import board.Board;
import board.Element;
import board.ElementType;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import prepare.PrepareBoard;

import static main.Config.*;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class Tile extends Rectangle {
    private int _x, _y;
    boolean drag = false;
    private Element element;


    public boolean hasElement() {
        return element != null;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Tile(boolean light, int x, int y) {
        element = null;
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.valueOf(FIRST_COLOR) : Color.valueOf(SECOND_COLOR));
        if(PREPARE) {
            setOnDragOver(e -> {
                int changedX = (int) e.getSceneX() / TILE_SIZE;
                int changedY = (int) e.getSceneY() / TILE_SIZE;
                if (!PrepareBoard.board[changedX][changedY].hasElement())
                    e.acceptTransferModes(TransferMode.ANY);
            });
            setOnDragDropped(e -> {
                var data = e.getDragboard().getString();
                var _x = Integer.parseInt(data.substring(0, data.indexOf("|")));
                var _y = Integer.parseInt(data.substring(data.indexOf("|") + 1));
                int changedX = (int) e.getSceneX() / TILE_SIZE;
                int changedY = (int) e.getSceneY() / TILE_SIZE;

                PrepareBoard.board[_x][_y].getElement().piece.move(changedX, changedY);
                PrepareBoard.board[changedX][changedY].setElement(PrepareBoard.board[_x][_y].getElement());
                PrepareBoard.board[_x][_y].setElement(null);
            });
        }
    }
}
