package elements;


import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Globals;
import main.Move;
import pages.PrepareBoard;

import static main.Config.*;


public class Tile extends Rectangle {

    private Element element;


    public Tile(boolean light, int x, int y) {
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(light ? Color.valueOf(FIRST_COLOR) : Color.valueOf(SECOND_COLOR));

        setOnContextMenuRequested(e -> {
            if (Globals.PREPARE && (!hasElement() || getElement().getType() != ElementType.PIECE)) {
                DropdownMenu dm = new DropdownMenu(hasElement(), x, y);
                dm.show(this, e.getScreenX(), e.getScreenY());
            }
        });
        setOnDragOver(e -> {
            if (Globals.PREPARE) {
                int x2 = (int) e.getSceneX() / TILE_SIZE;
                int y2 = (int) e.getSceneY() / TILE_SIZE;
                if (!PrepareBoard.getBoard()[x2][y2].hasElement())
                    e.acceptTransferModes(TransferMode.ANY);
            }
        });
        setOnDragDropped(e -> {
            if (Globals.PREPARE) {
                var data = e.getDragboard().getString();
                var x1 = Integer.parseInt(data.substring(0, data.indexOf("|")));
                var y1 = Integer.parseInt(data.substring(data.indexOf("|") + 1));
                int x2 = (int) e.getSceneX() / TILE_SIZE;
                int y2 = (int) e.getSceneY() / TILE_SIZE;
                Move.set(PrepareBoard.getBoard(), x1, y1, x2, y2);
            }
        });

    }

    public boolean hasElement() {
        return element != null;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
