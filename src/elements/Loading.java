package elements;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Loading extends StackPane {
    public Loading(LoadingType type) {
        Rectangle r = new Rectangle();
        Image map = new Image(type==LoadingType.SEARCH?"elements/assets/search1.gif":"elements/assets/load2.gif");
        r.setWidth(300);
        r.setHeight(map.getWidth()/300*map.getHeight());
        ImagePattern pattern = new ImagePattern(map);
        r.setFill(pattern);
        getChildren().add(r);
    }
}
