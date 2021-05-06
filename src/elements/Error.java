package elements;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class Error extends Alert {
    public Error(String s){
        super(AlertType.ERROR);
        initStyle(StageStyle.UNDECORATED);
        setContentText(s);
        showAndWait();
    }
}
