package elements;

import board.Board;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static main.Config.*;


public class Piece extends Element {

    private final PieceType pieceType;

    public Piece(PieceType type, int x, int y) {
        super(x, y, ElementType.PIECE);

        this.pieceType = type;
        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceType.RED ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnDragDetected(e -> {
            if (!PREPARE) {
                for (int i = this.x; i < WIDTH && (!Board.board[i][this.y].hasElement() || Board.board[i][this.y].getElement().getType() != ElementType.WALL); i++)
                    if (!Board.board[i][this.y].hasElement() ||
                            (Board.board[i][this.y].getElement().getType() == ElementType.SLOW || Board.board[i][this.y].getElement().getType() == ElementType.STAR))
                        Board.safeMargin[i][this.y].setVisible(true);
                for (int i = this.x; i >= 0 && (!Board.board[i][this.y].hasElement() || Board.board[i][this.y].getElement().getType() != ElementType.WALL); i--)
                    if (!Board.board[i][this.y].hasElement() ||
                            (Board.board[i][this.y].getElement().getType() == ElementType.SLOW || Board.board[i][this.y].getElement().getType() == ElementType.STAR))
                        Board.safeMargin[i][this.y].setVisible(true);


                for (int j = this.y; j < HEIGHT && (!Board.board[this.x][j].hasElement() || Board.board[this.x][j].getElement().getType() != ElementType.WALL); j++)
                    if (!Board.board[this.x][j].hasElement() ||
                            (Board.board[this.x][j].getElement().getType() == ElementType.SLOW || Board.board[this.x][j].getElement().getType() == ElementType.STAR))
                        Board.safeMargin[this.x][j].setVisible(true);
                for (int j = this.y; j >= 0 && (!Board.board[this.x][j].hasElement() || Board.board[this.x][j].getElement().getType() != ElementType.WALL); j--)
                    if (!Board.board[this.x][j].hasElement() ||
                            (Board.board[this.x][j].getElement().getType() == ElementType.SLOW || Board.board[this.x][j].getElement().getType() == ElementType.STAR))
                        Board.safeMargin[this.x][j].setVisible(true);
            }
            Dragboard db = startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putString(this.x + "|" + this.y);
            db.setContent(cb);
            e.consume();
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

    public PieceType getPieceType() {
        return pieceType;
    }


}
