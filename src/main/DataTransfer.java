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
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pages.Board;
import pages.PrepareBoard;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static main.Config.*;
import static main.Network.*;

public class DataTransfer implements Runnable {
     static Server server;

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
                        System.gc();
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
                        send(s, "end?");
                        var winner=Integer.parseInt(receive(s));
                        if(winner>=0){
                            Platform.runLater(()-> {
                                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                                    a.initStyle(StageStyle.UNDECORATED);
                                    a.setHeaderText("Game is ended");
                                    a.setContentText("Player "+ winner+1 +" win!!!");
                                    Optional<ButtonType> result = a.showAndWait();
                                    result.ifPresent(__ ->System.exit(1));

                            });
                            GAME_IS_ENDED=true;
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    System.out.println(e);
                    break;
                }
            }

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
        for(int i=0;i<server.size;i++){
            if(i!=MY_ID) {
                send(s, "l:" + i);
                var data = receive(s);
                var x2 = Integer.parseInt(data.substring(0, data.indexOf("|")));
                var y2 = Integer.parseInt(data.substring(data.indexOf("|") + 1));

                int finalI = i;
                Platform.runLater(()-> {
                    for (var t : Board.board)
                        for (var tile : t)
                            if (tile.hasElement() && tile.getElement().id == finalI)
                                if (tile.getElement().getX() != x2 || tile.getElement().getY() != y2)
                                    Move.set(Board.board, tile.getElement().getX(), tile.getElement().getY(), x2, y2);

                });
            }
        }

    }

}

