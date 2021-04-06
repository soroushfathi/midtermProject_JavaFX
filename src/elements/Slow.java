package elements;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static main.Config.TILE_SIZE;

public class Slow extends StackPane {
    private double oldX, oldY;

    private int slowNum;

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Slow(int x, int y){
        Rectangle slow=new Rectangle(0,0,TILE_SIZE,TILE_SIZE);

        Image map=new Image("elements/slow.png");

        ImagePattern pattern = new ImagePattern(map);
        slow.setFill(pattern);
        move(x, y);
        getChildren().addAll(slow);
    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }
}
