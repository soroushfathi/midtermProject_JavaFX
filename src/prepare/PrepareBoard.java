package prepare;

import elements.Element;
import elements.*;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Random;

import static main.Config.*;
import static main.Config.WIDTH;

public class PrepareBoard {

    private final Group tileGroup = new Group();
    private final Group pieceGroup = new Group();
    private final Group wallGroup = new Group();
    private final Group starGroup = new Group();
    private final Group slowGroup = new Group();

    public static Tile[][] board;
    public static Star[][] stars;
    public static Slow[][] slows;
    public static Wall[][] walls;

    public PrepareBoard() {
        board=new Tile[WIDTH][HEIGHT];
        stars=new Star[WIDTH][HEIGHT];
        slows=new Slow[WIDTH][HEIGHT];
        walls=new Wall[WIDTH][HEIGHT];
    }
    public Pane build(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE+SETTING_WIDTH, HEIGHT * TILE_SIZE);

        Setting setting = new Setting();
        setting.setPrefWidth(SETTING_WIDTH);

        GridPane mainGrid = new GridPane();
        Pane boardPane=new Pane();


        boardPane.getChildren().addAll(tileGroup,wallGroup,starGroup,slowGroup,pieceGroup);

        GridPane.setConstraints(setting,1,0);
        GridPane.setConstraints(boardPane,0,0);
        mainGrid.getChildren().add(setting);
        mainGrid.getChildren().add(boardPane);

        root.getChildren().addAll(mainGrid);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                Wall wall = new Wall(x,y);
                Slow slow = new Slow(x,y);
                Star star = new Star(x,y);

                stars[x][y]=star;
                slows[x][y]=slow;
                walls[x][y]=wall;

                Piece piece = null;
                if (y ==0 && x==0) {
                    piece = new Piece(PieceType.RED, x, y);
                }

                if (y ==WIDTH-1 && x==WIDTH-1) {
                    piece = new Piece(PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setElement(piece);
                    pieceGroup.getChildren().add(piece);
                }
                wallGroup.getChildren().add(wall);
                starGroup.getChildren().add(star);
                slowGroup.getChildren().add(slow);
                tileGroup.getChildren().add(tile);
            }
        }
        return root;
    }
}
