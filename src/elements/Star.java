package elements;

import board.ElementType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static main.Config.*;


public class Star extends Element {

    public Star(int x, int y){
        super(x,y, ElementType.STAR);
        Rectangle star=new Rectangle(TILE_SIZE,TILE_SIZE);

        Image map=new Image("elements/star.png");
        ImagePattern pattern = new ImagePattern(map);
        star.setFill(pattern);

        setVisible(!PREPARE);

        getChildren().addAll(star);
    }

}
