package main;

import board.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
       Board board = new Board();

        //Scene scene = new Scene(pane);
        primaryStage.setScene(new Scene(board.build()));
        primaryStage.setTitle("Game");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
