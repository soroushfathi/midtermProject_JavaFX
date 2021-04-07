package pages;

import elements.*;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import java.util.Random;

import static main.Config.*;

public class Board {
    public static Tile[][] board;
    public static Safe[][] safeMargin;

    private final Group tileGroup = new Group();
    private final Group safeGroup = new Group();
    private final Group elementGroup = new Group();
    private final Group pieceGroup = new Group();

    public Board() {
        board = new Tile[WIDTH][HEIGHT];
        safeMargin = new Safe[WIDTH][HEIGHT];
    }

    public Pane build() {
        Pane root = new Pane();

        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, elementGroup, pieceGroup, safeGroup);

        Random rand = new Random();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = board[x][y];

                Safe safe = new Safe(x, y);
                safeMargin[x][y] = safe;

                Wall wall = new Wall(x, y);
                safeGroup.getChildren().add(safe);
                tileGroup.getChildren().add(tile);

                Element element = board[x][y].getElement();

                if (element != null && element.getType() != ElementType.PIECE)
                    elementGroup.getChildren().add(element);
                else if (element != null && element.getType() == ElementType.PIECE)
                    pieceGroup.getChildren().add(element);

            }
        }
        return root;
    }
}
