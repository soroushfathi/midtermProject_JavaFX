package elements;

import board.Board;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static main.Config.PREPARE;

public class Setting extends GridPane {
    public Setting(){
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(5);
        setHgap(5);
        final TextField name = new TextField();
        name.setPromptText("Enter your first name.");
        name.setPrefColumnCount(10);
        name.getText();
        setConstraints(name, 0, 0);
        getChildren().add(name);
//Defining the Last Name text field
        final TextField lastName = new TextField();
        lastName.setPromptText("Enter your last name.");
        setConstraints(lastName, 0, 1);
        getChildren().add(lastName);
//Defining the Comment text field
        final TextField comment = new TextField();
        comment.setPrefColumnCount(15);
        comment.setPromptText("Enter your comment.");
        setConstraints(comment, 0, 2);
        getChildren().add(comment);
//Defining the Submit button
        Button submit = new Button("Submit");
        setConstraints(submit, 1, 0);
        getChildren().add(submit);
        submit.setOnMouseClicked(e->{
            PREPARE=false;
            Board board = new Board();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(board.build()));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(e.getSource())).getScene().getWindow().hide();
        });
//Defining the Clear button
        Button clear = new Button("Clear");
        setConstraints(clear, 1, 1);
        getChildren().add(clear);
    }
}
