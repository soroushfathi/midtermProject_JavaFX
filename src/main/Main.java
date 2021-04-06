package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prepare.PrepareBoard;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PrepareBoard pBoard = new PrepareBoard();

        primaryStage.setScene(new Scene(pBoard.build()));
        primaryStage.setTitle("Prepare Game");
        primaryStage.show();
    }
}
