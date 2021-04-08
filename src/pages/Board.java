package pages;

import elements.*;
import javafx.application.Platform;
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
    public static void TranslateBoard(ElementType[][] elements,String[][] colors,int[][] values,int [][] id){
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {
                        Tile tile = new Tile((x + y) % 2 == 0, x, y);
                        board[x][y] = tile;
                        if(elements[x][y]!=null) {
                            switch (elements[x][y]) {
                                case STAR -> board[x][y].setElement(new Star(x, y));
                                case WALL -> board[x][y].setElement(new Wall(x, y));
                                case PIECE -> board[x][y].setElement(new Piece(colors[x][y], x, y,id[x][y]));
                                case SLOW -> board[x][y].setElement(new Slow(x, y, values[x][y]));
                            }
                        }
                    }

                }
            }
        });

    }
}
