package elements;

import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static main.Globals.PREPARE;
import static main.Config.TILE_SIZE;


public class Star extends Element {

    public Star(int x, int y) {
        super(x, y);
        Rectangle star = new Rectangle(TILE_SIZE, TILE_SIZE);

        Image map = new Image("elements/assets/star.png");
        ImagePattern pattern = new ImagePattern(map);
        star.setFill(pattern);

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(1000));


        rotateTransition.setNode(star);
        rotateTransition.setByAngle(30);


        rotateTransition.setCycleCount(10000);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

        setVisible(!PREPARE);

        getChildren().addAll(star);
    }

}
