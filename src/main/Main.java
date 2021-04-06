package main;

import board.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prepare.PrepareBoard;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PrepareBoard pBoard = new PrepareBoard();

        primaryStage.setScene(new Scene(pBoard.build()));
        primaryStage.setTitle("Prepare Game");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
