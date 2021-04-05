package board.elements;

import board.Element;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


import static board.Config.TILE_SIZE;

public class Wall extends StackPane {
    private double oldX, oldY;

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Wall(int x, int y){

        Rectangle wall=new Rectangle(0,0,TILE_SIZE,TILE_SIZE);
        Image map=new Image("board/elements/wall.png");
        ImagePattern pattern = new ImagePattern(map);
        wall.setFill(pattern);
        move(x, y);
        getChildren().addAll(wall);
    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }
}
