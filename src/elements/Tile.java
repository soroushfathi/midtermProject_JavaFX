package elements;


import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Move;
import prepare.PrepareBoard;

import static main.Config.*;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class Tile extends Rectangle {

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
                var x1 = Integer.parseInt(data.substring(0, data.indexOf("|")));
                var y1 = Integer.parseInt(data.substring(data.indexOf("|") + 1));
                int x2 = (int) e.getSceneX() / TILE_SIZE;
                int y2 = (int) e.getSceneY() / TILE_SIZE;
                Move.set(PrepareBoard.board,x1,y1,x2,y2);
            });
        }
    }
}
