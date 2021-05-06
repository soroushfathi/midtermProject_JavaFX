package network;

import elements.ElementType;
import elements.Loading;
import elements.LoadingType;
import elements.Piece;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.Globals;
import main.Move;
import pages.Board;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static main.Config.*;
import static main.Globals.*;
import static network.Network.*;

public class DataTransfer implements Runnable {
    private static Server server;
    private static Scene sc;
    int l = 0;

    public DataTransfer(Server s,Scene sc) {
        server = s;
        this.sc=sc;
    }

    @Override
    public void run() {

        Socket s = new Socket();
        try {
            s = new Socket("localhost", server.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (s.isConnected()) {
            try {
                s.setSoTimeout(SERVER_RESPONSE_TIMEOUT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            while (!GAME_IS_ENDED) {
                try {
                    if (receive(s).equals("hello!")) {
                        s.setSoTimeout(0);
                        send(s, "hello!");
                        send(s, "elements");
                        var elements = (ElementType[][]) receiveObject(s);
                        send(s, "colors");
                        var colors = (String[][]) receiveObject(s);
                        send(s, "values");
                        var values = (int[][]) receiveObject(s);
                        send(s, "id");
                        var id = (int[][]) receiveObject(s);
                        send(s, "width");
                        WIDTH = Integer.parseInt(receive(s));
                        send(s, "height");
                        HEIGHT = Integer.parseInt(receive(s));
                        send(s, "myId");
                        MY_ID = Integer.parseInt(receive(s));
                        send(s, "tileSize");
                        TILE_SIZE = Integer.parseInt(receive(s));
                        send(s, "fc");
                        FIRST_COLOR = receive(s);
                        send(s, "sc");
                        SECOND_COLOR = receive(s);
                        PREPARE = false;
                        drawBoard(elements, colors, values, id);
                        break;
                    }
                } catch (ClassNotFoundException | InterruptedException | IOException e) {
                    e.printStackTrace();

                }

            }
            boolean showWait = true;
            while (!GAME_IS_ENDED) {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                    if (!GAME_IS_STARTED && showWait) {
                        showWait = false;
                        waitingForPlayers(s);
                    }

                    if (GAME_IS_STARTED) {
                        getLastMove(s);
                        setMove(s);
                        getTurn(s);
                        getLimit(s);
                        getScore(s);
                        checkWinner(s);
                    }
                } catch (IOException | InterruptedException e) {
                    System.out.println(e);
                    break;
                }
            }

        }
    }

    private void getScore(Socket s) throws IOException, InterruptedException {
        send(s, "score?");
        SCORE=Integer.parseInt(receive(s));
    }

    private void checkWinner(Socket s) throws IOException, InterruptedException {
        send(s, "end?");
        int winner=Integer.parseInt(receive(s));
        if(winner>=0){
            Platform.runLater(()-> {
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.initStyle(StageStyle.UNDECORATED);
                    a.setHeaderText("Game is ended");
                    a.setContentText("Player "+ (winner + 1) +" win!!!");
                    Optional<ButtonType> result = a.showAndWait();
                    result.ifPresent(__ ->System.exit(1));

            });
            GAME_IS_ENDED=true;
        }
    }

    private void getLimit(Socket s) throws IOException, InterruptedException {
        if(LIMIT==-1) {
            send(s, "limit");
            LIMIT = Integer.parseInt(receive(s));
        }
    }

    private static void getTurn(Socket s) throws IOException, InterruptedException {
        send(s, "turn");
        YOUR_TURN = MY_ID == Integer.parseInt(receive(s));
    }

    private static void drawBoard(ElementType[][] elements, String[][] colors, int[][] values, int[][] id) {
        Board board = new Board();
        Board.TranslateBoard(elements, colors, values, id);
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Scene scene=new Scene(board.build());
            stage.setTitle("Game");
            stage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

            sc.getWindow().hide();
        });

    }

    private static void waitingForPlayers(Socket s) {
        Platform.runLater(() -> {
            Loading l=new Loading(LoadingType.SEARCH,"Wait for other players");

            WaitForOtherPlayers task = new WaitForOtherPlayers(s);
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(task);

            task.setOnRunning(__ ->l.show());

            task.setOnSucceeded(__ -> {
                l.close();
                executorService.shutdown();
                GAME_IS_STARTED=true;
            });

            });
    }

    private static void setMove(Socket s) throws IOException, InterruptedException {
        if (MOVED && YOUR_TURN) {
            MOVED = false;
            send(s, "moved");
            send(s, FROM + "-" + TO);
        }
    }

    private static void getLastMove(Socket s) throws IOException, InterruptedException {
        for(int i = 0; i< server.getSize(); i++){
            if(i!= MY_ID) {
                send(s, "l:" + i);
                var data = receive(s);
                var x2 = Integer.parseInt(data.substring(0, data.indexOf("|")));
                var y2 = Integer.parseInt(data.substring(data.indexOf("|") + 1));

                int finalI = i;
                Platform.runLater(()-> {
                    for (var t : Board.getBoard())
                        for (var tile : t)
                            if (tile.hasElement() &&tile.getElement() instanceof Piece &&((Piece)tile.getElement()).getPieceId() == finalI)
                                if (tile.getElement().getX() != x2 || tile.getElement().getY() != y2)
                                    Move.set(Board.getBoard(), tile.getElement().getX(), tile.getElement().getY(), x2, y2);

                });
            }
        }

    }

}

