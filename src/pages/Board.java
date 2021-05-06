package pages;

import elements.*;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Globals;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static main.Config.*;
import static main.Globals.SCORE;

public class Board {
    private static Tile[][] board;
    public static Safe[][] safeMargin;

    private final Group tileGroup = new Group();
    private final Group safeGroup = new Group();
    private final Group elementGroup = new Group();
    private final Group pieceGroup = new Group();

    public static Label timer = new Label();
    public static Label score = new Label();

    public Board() {
        setBoard(new Tile[WIDTH][HEIGHT]);
        safeMargin = new Safe[WIDTH][HEIGHT];
    }

    public static Tile[][] getBoard() {
        return board;
    }

    public static void setBoard(Tile[][] board) {
        Board.board = board;
    }

    public Pane build() {

        Pane root = new Pane();
        root.getStylesheets().add("pages/Style.css");
        root.getStyleClass().add("board");
       // root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

        timer.relocate(477,810);
        timer.setTextFill(Color.WHITE);
        score.relocate(130,810);
        score.setTextFill(Color.WHITE);

        root.getChildren().addAll(tileGroup, elementGroup, pieceGroup, safeGroup,timer,score);
        var dy=100+(550-HEIGHT * TILE_SIZE)/2;
        var dx=110+(450- WIDTH* TILE_SIZE)/2;
        tileGroup.relocate(dx,dy);
        elementGroup.relocate(dx,dy);
        pieceGroup.relocate(dx,dy);
        safeGroup.relocate(dx,dy);


        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = getBoard()[x][y];

                Safe safe = new Safe(x, y);
                safeMargin[x][y] = safe;

                safeGroup.getChildren().add(safe);
                tileGroup.getChildren().add(tile);

                Element element = getBoard()[x][y].getElement();

                if (element != null && !(element instanceof Piece))
                    elementGroup.getChildren().add(element);
                else if (element != null)
                    pieceGroup.getChildren().add(element);

            }
        }
        showTimer();

        return root;
    }

    private void showTimer() {
        AtomicInteger l= new AtomicInteger();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                var time=l.getAndIncrement();
                Platform.runLater(()->{
                    Board.timer.setText((time / 3600) +" : "+((time / 60)%60) +" : "+(time % 60) );
                    Board.score.setText(String.valueOf(SCORE));
                });
            }
        }, 0, 1000);
    }

    public static void TranslateBoard(ElementType[][] elements,String[][] colors,int[][] values,int [][] id){
        Platform.runLater(() -> {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Tile tile = new Tile((x + y) % 2 == 0, x, y);
                    getBoard()[x][y] = tile;
                    if(elements[x][y]!=null) {
                        switch (elements[x][y]) {
                            case STAR -> getBoard()[x][y].setElement(new Star(x, y));
                            case WALL -> getBoard()[x][y].setElement(new Wall(x, y));
                            case PIECE -> getBoard()[x][y].setElement(new Piece(colors[x][y], x, y,id[x][y]));
                            case SLOW -> {
                                Slow s=  new Slow(x, y, values[x][y]);
                                getBoard()[x][y].setElement(s);
                                Tooltip.install(
                                        s,
                                        new Tooltip("Value is: "+values[x][y])
                                );
                            }
                        }
                    }
                }

            }
        });

    }
}
