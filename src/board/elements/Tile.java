package board.elements;

import board.Board;
import board.Element;
import board.elements.Piece;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static board.Config.TILE_SIZE;
import static board.Config.WIDTH;

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

        setFill(light ? Color.valueOf("#e9d2af") : Color.valueOf("#af8460"));

        setOnDragOver(e -> {
            var data = e.getDragboard().getString();
            _x = Integer.parseInt(data.substring(0, data.indexOf("|")));
            _y = Integer.parseInt(data.substring(data.indexOf("|") + 1));
            int changedX = (int) e.getSceneX() / TILE_SIZE;
            int changedY = (int) e.getSceneY() / TILE_SIZE;
            if (!hasElement() &&
                    ((changedX == _x && changedY != _y) || (changedX != _x && changedY == _y))) {
                e.acceptTransferModes(TransferMode.ANY);

            }
        });

    }
}
