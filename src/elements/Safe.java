package elements;

import pages.Board;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Move;

import static main.Config.*;

public class Safe extends Element {
    public Safe(int x, int y) {
        super(x, y, ElementType.SAFE);
        Rectangle r = new Rectangle(TILE_SIZE, TILE_SIZE);
        r.setOpacity(SAFE_OPACITY);
        r.setFill(Color.valueOf(SAFE_COLOR));

        getChildren().addAll(r);
        setVisible(false);

        setOnDragOver(e -> {
            e.acceptTransferModes(TransferMode.ANY);
            var data = e.getDragboard().getString();
            var x1 = Integer.parseInt(data.substring(0, data.indexOf("|")));
            var y1 = Integer.parseInt(data.substring(data.indexOf("|") + 1));
            int x2 = (int) e.getSceneX() / TILE_SIZE;
            int y2 = (int) e.getSceneY() / TILE_SIZE;
            if (Board.safeMargin[x2][y2].isVisible())
                Board.board[x1][y1].getElement().relocate(x2 * TILE_SIZE, y2 * TILE_SIZE);

        });

        setOnDragDropped(e -> {
            var data = e.getDragboard().getString();
            var x1 = Integer.parseInt(data.substring(0, data.indexOf("|")));
            var y1 = Integer.parseInt(data.substring(data.indexOf("|") + 1));

            int x2 = (int) e.getSceneX() / TILE_SIZE;
            int y2 = (int) e.getSceneY() / TILE_SIZE;
            if (Board.safeMargin[x2][y2].isVisible()) {


                Move.set(Board.board, x1, y1, x2, y2);

                for (int i = 0; i < WIDTH; i++)
                    Board.safeMargin[i][y1].setVisible(false);

                for (int j = 0; j < HEIGHT; j++)
                    Board.safeMargin[x1][j].setVisible(false);
            }
        });
    }

    public void move(int x, int y) {

        relocate(x * TILE_SIZE, y * TILE_SIZE);
    }

}
