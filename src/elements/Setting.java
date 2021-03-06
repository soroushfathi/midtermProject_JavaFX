package elements;

import main.Globals;
import pages.Board;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pages.PrepareBoard;

import static main.Config.*;

public class Setting extends GridPane {
    public Setting() {
        // getStylesheets().add("pages/Style.css");

        setPadding(new Insets(10, 10, 10, 10));
        setVgap(2);
        setHgap(2);

        ColorPicker fcp = new ColorPicker(Color.valueOf(FIRST_COLOR));
        setConstraints(fcp, 0, 0);
        getChildren().add(fcp);

        ColorPicker scp = new ColorPicker(Color.valueOf(SECOND_COLOR));
        setConstraints(scp, 0, 1);
        getChildren().add(scp);

        Button submit = new Button("Submit");
        setConstraints(submit, 1, 0);
        getChildren().add(submit);

        submit.setOnMouseClicked(e -> {
            Globals.PREPARE = false;
            Board board = new Board();
            Board.setBoard(PrepareBoard.getBoard());
            Stage stage = new Stage();
            stage.setTitle("Game");
            stage.setScene(new Scene(board.build()));
            stage.show();

            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
        //Defining the Clear button
        Button clear = new Button("Reset");
        setConstraints(clear, 1, 1);
        getChildren().add(clear);

        clear.setOnMouseClicked(e -> {
            PrepareBoard pb = new PrepareBoard();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(pb.build()));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
        fcp.setOnAction(e -> {
            Color c = fcp.getValue();
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    if ((x + y) % 2 == 0)
                        PrepareBoard.getBoard()[x][y].setFill(c);
                }
            }
        });
        scp.setOnAction(e -> {
            Color c = scp.getValue();
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    if ((x + y) % 2 == 1)
                        PrepareBoard.getBoard()[x][y].setFill(c);
                }
            }
        });
    }
}
