package pages;

import elements.*;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Globals;
import main.Player;

import static main.Config.*;


public class PrepareBoard {

    private static Tile[][] board;
    private static Star[][] stars;
    private static Slow[][] slows;
    private static Wall[][] walls;
    private final Group tileGroup = new Group();
    private final Group elementGroup = new Group();


    public PrepareBoard() {

        board = new Tile[WIDTH][HEIGHT];
        stars = new Star[WIDTH][HEIGHT];
        slows = new Slow[WIDTH][HEIGHT];
        walls = new Wall[WIDTH][HEIGHT];
    }

    public static Star[][] getStars() {
        return stars;
    }

    public static Slow[][] getSlows() {
        return slows;
    }

    public static Wall[][] getWalls() {
        return walls;
    }

    public static Tile[][] getBoard() {
        return board;
    }

    public Pane build() {
        int pieceCount=0;

        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE + SETTING_WIDTH, HEIGHT * TILE_SIZE);

        Setting setting = new Setting();
        setting.setPrefWidth(SETTING_WIDTH);

        GridPane mainGrid = new GridPane();
        Pane boardPane = new Pane();


        boardPane.getChildren().addAll(tileGroup, elementGroup);

        GridPane.setConstraints(setting, 1, 0);
        GridPane.setConstraints(boardPane, 0, 0);
        mainGrid.getChildren().add(setting);
        mainGrid.getChildren().add(boardPane);

        root.getChildren().addAll(mainGrid);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                getBoard()[x][y] = tile;

                Wall wall = new Wall(x, y);
                Slow slow = new Slow(x, y,0);
                Star star = new Star(x, y);

                getStars()[x][y] = star;
                getSlows()[x][y] = slow;
                getWalls()[x][y] = wall;

                Piece piece = null;
                if (y == 0 && x == 0) {
                    piece = new Piece(Color.RED.toString(), x, y,pieceCount);
                    Globals.players[pieceCount]=new Player(pieceCount++,piece);
                }

                if (y == WIDTH - 1 && x == WIDTH - 1) {
                    piece = new Piece(Color.WHITE.toString(), x, y,pieceCount);
                    Globals.players[pieceCount]=new Player(pieceCount++,piece);
                }

                if (piece != null) {
                    tile.setElement(piece);
                    elementGroup.getChildren().add(piece);
                }
                elementGroup.getChildren().addAll(wall,star,slow);
                tileGroup.getChildren().add(tile);
            }
        }
        return root;
    }


}
