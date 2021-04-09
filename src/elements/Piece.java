package elements;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.StageStyle;
import main.PlayType;
import pages.Board;

import static main.Config.*;


public class Piece extends Element {


    public Piece(String color, int x, int y, int id) {
        super(x, y, ElementType.PIECE);
        super.color = color;

        Label l = new Label(id == MY_ID ? "YOU" : String.valueOf(id+1));
        l.setTranslateX(TILE_SIZE / 4 - 3);
        l.setTranslateY(TILE_SIZE / 4);
        l.setVisible(PLAY_TYPE == PlayType.NETWORK);

        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse.setFill(Color.valueOf(color));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse, l);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error!!!");
        alert.initStyle(StageStyle.UNDECORATED);
        setOnDragDetected(e -> {
            if (PLAY_TYPE != PlayType.NETWORK || (YOUR_TURN && id == MY_ID)) {
                if (!PREPARE) {
                    for (int i = this.x; i < WIDTH && (!Board.board[i][this.y].hasElement() || Board.board[i][this.y].getElement().getType() != ElementType.WALL) && (i - this.x) < LIMIT; i++)
                        if (!Board.board[i][this.y].hasElement() || Board.board[i][this.y].getElement().getType() == ElementType.SLOW || Board.board[i][this.y].getElement().getType() == ElementType.STAR)
                            Board.safeMargin[i][this.y].setVisible(true);
                    for (int i = this.x; i >= 0 && (!Board.board[i][this.y].hasElement() || Board.board[i][this.y].getElement().getType() != ElementType.WALL) && (this.x - i) < LIMIT; i--)
                        if (!Board.board[i][this.y].hasElement() || Board.board[i][this.y].getElement().getType() == ElementType.SLOW || Board.board[i][this.y].getElement().getType() == ElementType.STAR)
                            Board.safeMargin[i][this.y].setVisible(true);


                    for (int j = this.y; j < HEIGHT && (!Board.board[this.x][j].hasElement() || Board.board[this.x][j].getElement().getType() != ElementType.WALL) && (j - this.y) < LIMIT; j++)
                        if (!Board.board[this.x][j].hasElement() || Board.board[this.x][j].getElement().getType() == ElementType.SLOW || Board.board[this.x][j].getElement().getType() == ElementType.STAR)
                            Board.safeMargin[this.x][j].setVisible(true);
                    for (int j = this.y; j >= 0 && (!Board.board[this.x][j].hasElement() || Board.board[this.x][j].getElement().getType() != ElementType.WALL) && (this.y - j) < LIMIT; j--)
                        if (!Board.board[this.x][j].hasElement() || Board.board[this.x][j].getElement().getType() == ElementType.SLOW || Board.board[this.x][j].getElement().getType() == ElementType.STAR)
                            Board.safeMargin[this.x][j].setVisible(true);
                }

                Dragboard db = startDragAndDrop(TransferMode.ANY);
                ClipboardContent cb = new ClipboardContent();
                cb.putString(this.x + "|" + this.y);
                db.setContent(cb);
                e.consume();
            }else if(!YOUR_TURN){
                alert.setContentText("Is not your turn");
                alert.showAndWait();
            }else {
                alert.setContentText("Is not your piece");
                alert.showAndWait();
            }
        });

        setOnDragDone(e -> {
            if (!PREPARE) {
                for (int i = 0; i < WIDTH; i++)
                    Board.safeMargin[i][this.y].setVisible(false);

                for (int j = 0; j < HEIGHT; j++)
                    Board.safeMargin[this.x][j].setVisible(false);
            }
        });

    }


}
