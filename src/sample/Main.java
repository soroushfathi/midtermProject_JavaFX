package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button button = new Button("start the Game");
        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(button);

        //Scene scene = new Scene(pane);
        primaryStage.setScene(new Scene(pane,300,275));
        primaryStage.setTitle("Game");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
