package elements;

import javafx.scene.layout.StackPane;

import static main.Globals.PREPARE;
import static main.Config.TILE_SIZE;

public abstract class Element extends StackPane {
    protected int x, y;

    public Element(int x, int y) {
        this.x = x;
        this.y = y;
        move(x, y);

        setOnContextMenuRequested(e -> {
            if (PREPARE && ! (this instanceof Piece)) {
                DropdownMenu dm = new DropdownMenu(true, x, y);
                dm.show(this, e.getScreenX(), e.getScreenY());
            }
        });

    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        relocate(x * TILE_SIZE, y * TILE_SIZE);
    }

}
