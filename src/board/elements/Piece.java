package board.elements;

import board.Board;
import board.Element;
import board.ElementType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static board.Config.*;


public class Piece extends StackPane {

    private PieceType type;

    private double mouseX, mouseY;
    private double oldX, oldY;

    public PieceType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(PieceType type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceType.RED
                ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMouseDragged(e -> {
            var selectedX = e.getSceneX() - TILE_SIZE / 2;
            var selectedY = e.getSceneY() - TILE_SIZE / 2;
            int _x = (int) Math.round(selectedX / TILE_SIZE);
            int _y = (int) Math.round(selectedY / TILE_SIZE);
            if (_x >= 0 && _y >= 0 && _x < WIDTH && _y < HEIGHT &&
                    ( !Board.board[_x][_y].hasElement()||
                    (Board.board[_x][_y].getElement().getType()!= ElementType.PIECE
                    && Board.board[_x][_y].getElement().getType()!= ElementType.WALL))) {

                relocate(_x * TILE_SIZE, _y * TILE_SIZE);


                if(Board.board[_x][_y].hasElement() &&Board.board[_x][_y].getElement().getType()== ElementType.STAR){
                    Board.board[_x][_y].getElement().star.move(10000,10000);
                }
                Board.board[_x][_y].setElement(new Element(this));
                Board.board[(int) oldX / TILE_SIZE][(int) oldY / TILE_SIZE].setElement(null);
                oldX = _x * TILE_SIZE;
                oldY = _y * TILE_SIZE;
            }

        });


    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
