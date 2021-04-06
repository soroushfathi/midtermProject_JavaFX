package elements;

import board.ElementType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static main.Config.*;

public class Wall extends Element {

    public Wall(int x, int y){
        super(x,y, ElementType.WALL);

        Rectangle wall=new Rectangle(0,0,TILE_SIZE,TILE_SIZE);

        Image map=new Image("elements/wall.png");
        ImagePattern pattern = new ImagePattern(map);
        wall.setFill(pattern);

        setVisible(!PREPARE);

        getChildren().addAll(wall);
    }

}