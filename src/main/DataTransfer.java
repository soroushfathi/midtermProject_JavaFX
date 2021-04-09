package main;

import elements.ElementType;
import elements.Loading;
import elements.LoadingType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pages.Board;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static main.Config.*;
import static main.Network.*;

public class DataTransfer implements Runnable {
    Server server;

    public DataTransfer(Server s) {
        server = s;
    }

    @Override
    public void run() {

        Socket s = new Socket();
        try {
            s = new Socket("localhost", server.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (s.isConnected()) {
            try {
                s.setSoTimeout(SERVER_RESPONSE_TIMEOUT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            while (true) {
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
                        System.gc();
                        break;
                    }
                } catch (ClassNotFoundException | InterruptedException | IOException e) {
                    e.printStackTrace();

                }

            }
            boolean showWait = true;
            while (true) {
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
                        if(LIMIT==-1) {
                            send(s, "limit");
                            LIMIT = Integer.parseInt(receive(s));
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private static void getTurn(Socket s) throws IOException, InterruptedException {
        send(s, "turn");
        YOUR_TURN = MY_ID == Integer.parseInt(receive(s));
    }

    private static void drawBoard(ElementType[][] elements, String[][] colors, int[][] values, int[][] id) {
        Board board = new Board();
        Board.TranslateBoard(elements, colors, values, id);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = new Stage();
                stage.setTitle("Game");
                stage.setScene(new Scene(board.build()));
                stage.show();
            }
        });
    }

    private static void waitingForPlayers(Socket s) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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

                }
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
            send(s, "lastMove");
            var LAST_MOVE = receive(s);
            if ( LAST_MOVE.length() > 0) {

                Config.LAST_MOVE = String.valueOf(LAST_MOVE);
                var x1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(0, LAST_MOVE.indexOf("|")));
                var y1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(LAST_MOVE.indexOf("|") + 1));
                var x2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(0, LAST_MOVE.indexOf("|")));
                var y2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(LAST_MOVE.indexOf("|") + 1));

                Move.set(Board.board, x1, y1, x2, y2);
            }

    }

}

