package elements;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static main.Globals.PREPARE;
import static main.Config.TILE_SIZE;

public class Slow extends Element {

    private int value;
    public int getValue() {
        return value;
    }
    public void setValue(int val) { this.value = val; }


    public Slow(int x, int y,int value) {
        super(x, y);
        this.value = value;

        Rectangle slow = new Rectangle(0, 0, TILE_SIZE, TILE_SIZE);

        Image map = new Image("elements/assets/slow.gif");
        ImagePattern pattern = new ImagePattern(map);
        slow.setFill(pattern);

        setVisible(!PREPARE);
        getChildren().addAll(slow);
    }

}
