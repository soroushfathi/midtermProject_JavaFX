package board;

import board.elements.*;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import java.util.Random;

import static board.Config.*;

public class Board {
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private Group wallGroup = new Group();
    private Group starGroup = new Group();
    private Group safeGroup = new Group();

    public static Tile[][] board;
    public static Safe[][] safeMargin;

    public Board() {
            TILE_SIZE=70;
            WIDTH=10;
            HEIGHT=10;
            board=new Tile[WIDTH][HEIGHT];
            safeMargin=new Safe[WIDTH][HEIGHT];
    }
    public Pane build(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup,wallGroup,starGroup,pieceGroup,safeGroup);
        Random rand= new Random();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                Safe safe=new Safe(x,y);
                safeMargin[x][y] = safe;

                safeGroup.getChildren().add(safe);
                tileGroup.getChildren().add(tile);

                Piece piece = null;
                Wall wall=null;
                Star star=null;
                Slow slow=null;

                if (y ==0&&x==0) {
                    piece = new Piece(PieceType.RED, x, y);
                }

                if (y ==WIDTH-1 && x==WIDTH-1) {
                    piece = new Piece(PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setElement(new Element(piece));
                    pieceGroup.getChildren().add(piece);
                }
                if(rand.nextInt()%4==1 && !tile.hasElement()){
                    wall = new Wall(x,y);
                }
                if (wall != null) {
                    tile.setElement(new Element(wall));
                    wallGroup.getChildren().add(wall);
                }
                if(rand.nextInt()%6==1 && !tile.hasElement()){
                    star = new Star(x,y);
                }
                if (star != null) {
                    tile.setElement(new Element(star));
                    starGroup.getChildren().add(star);
                }
                if(rand.nextInt()%8==1 && !tile.hasElement()){
                    slow = new Slow(x,y);
                }
                if (slow != null) {
                    tile.setElement(new Element(slow));
                    wallGroup.getChildren().add(slow);
                }
            }
        }
        return root;
    }
}
