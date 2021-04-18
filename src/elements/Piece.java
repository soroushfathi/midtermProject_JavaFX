package elements;

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
import static main.Globals.*;


public class Piece extends Element {

    private final int id;

    public int getPieceId() { return id; }

    public Piece(String color, int x, int y, int id) {
        super(x, y);

        this.id=id;

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
                    for (int i = this.x; i < WIDTH && (!Board.getBoard()[i][this.y].hasElement() || !(Board.getBoard()[i][this.y].getElement() instanceof Wall)) && (LIMIT==-1 ||(i - this.x) <= LIMIT); i++)
                        if (!(Board.getBoard()[i][this.y].getElement() instanceof Piece))
                            Board.safeMargin[i][this.y].setVisible(true);
                    for (int i = this.x; i >= 0 && (!Board.getBoard()[i][this.y].hasElement() || !(Board.getBoard()[i][this.y].getElement() instanceof Wall)) && (LIMIT==-1 ||(this.x - i) <= LIMIT); i--)
                        if (!(Board.getBoard()[i][this.y].getElement() instanceof Piece))
                            Board.safeMargin[i][this.y].setVisible(true);


                    for (int j = this.y; j < HEIGHT && (!Board.getBoard()[this.x][j].hasElement() || !(Board.getBoard()[this.x][j].getElement() instanceof Wall)) && (LIMIT==-1 || (j - this.y) <= LIMIT); j++)
                        if ((!(Board.getBoard()[this.x][j].getElement() instanceof Piece)))
                            Board.safeMargin[this.x][j].setVisible(true);
                    for (int j = this.y; j >= 0 && (!Board.getBoard()[this.x][j].hasElement() || !(Board.getBoard()[this.x][j].getElement() instanceof Wall)) && (LIMIT==-1 ||(this.y - j) <= LIMIT); j--)
                        if ((!(Board.getBoard()[this.x][j].getElement() instanceof Piece)))
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
//            MiniMax.miniMax(Board.getBoard(),0,0,5,false);
//            System.out.println("hello");
            if (!PREPARE) {
                for (int i = 0; i < WIDTH; i++)
                    Board.safeMargin[i][this.y].setVisible(false);

                for (int j = 0; j < HEIGHT; j++)
                    Board.safeMargin[this.x][j].setVisible(false);
            }
        });

    }


}
