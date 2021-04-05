package board.elements;

import board.Element;
import board.elements.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static board.Config.TILE_SIZE;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class Tile extends Rectangle {

    private Element element ;


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
        element=null;
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.valueOf("#e9d2af") : Color.valueOf("#af8460"));
    }
}
