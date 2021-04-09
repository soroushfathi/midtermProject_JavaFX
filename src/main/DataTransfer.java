package main;

import elements.ElementType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pages.Board;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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

        Socket s = null;
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
                        PREPARE = false;
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
                        System.gc();
                        break;
                    }
                } catch (ClassNotFoundException | InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }

            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                    send(s, "turn");
                    YOUR_TURN= MY_ID== Integer.parseInt(receive(s));
                    send(s, "lastMove");
                    var LAST_MOVE=  receive(s);
                    if(!Config.LAST_MOVE.equals(LAST_MOVE) && LAST_MOVE.length()>0) {
                        Config.LAST_MOVE=String.valueOf(LAST_MOVE);
                        var x1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(0, LAST_MOVE.indexOf("|")));
                        var y1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(LAST_MOVE.indexOf("|") + 1));
                        var x2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(0, LAST_MOVE.indexOf("|")));
                        var y2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(LAST_MOVE.indexOf("|") + 1));

                        Move.set(Board.board, x1, y1, x2, y2);
                    }
                    if(MOVED && YOUR_TURN ){

                        MOVED=false;
                        send(s, "moved");
                        send(s,FROM+"-"+TO);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
