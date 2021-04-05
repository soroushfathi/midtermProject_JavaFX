package board.elements;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import static board.Config.TILE_SIZE;


public class Star extends StackPane {
    private double oldX, oldY;

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Star(int x, int y){
        Rectangle star=new Rectangle(0,0,TILE_SIZE,TILE_SIZE);

        Image map=new Image("board/elements/star.png");
        ImagePattern pattern = new ImagePattern(map);
        star.setFill(pattern);
        move(x, y);
        getChildren().addAll(star);

    }
    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

}
