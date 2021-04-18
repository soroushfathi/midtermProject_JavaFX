package elements;

import javafx.scene.layout.StackPane;

import static main.Globals.PREPARE;
import static main.Config.TILE_SIZE;

public class Element extends StackPane {
    protected int x, y;

    private final ElementType type;

    public Element(int x, int y, ElementType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        move(x, y);

        setOnContextMenuRequested(e -> {
            if (PREPARE && type != ElementType.PIECE) {
                DropdownMenu dm = new DropdownMenu(true, x, y);
                dm.show(this, e.getScreenX(), e.getScreenY());
            }
        });

    }

    public ElementType getType() {
        return type;
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
