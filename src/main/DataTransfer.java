package main;

import elements.ElementType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pages.Board;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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
                            send(s, "height");
                            var password = receive(s);
                            send(s, "elements");
                            var elements = (ElementType[][]) receiveObject(s);
                            send(s, "colors");
                            var colors = (String[][]) receiveObject(s);
                            send(s, "values");
                            var values = (int[][]) receiveObject(s);
                            send(s, "width");
                            WIDTH=Integer.parseInt(receive(s));
                            send(s, "height");
                            HEIGHT = Integer.parseInt(receive(s));
                            PREPARE = false;
                            Board board = new Board();
                            Board.TranslateBoard(elements, colors, values);
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Stage stage = new Stage();
                                    stage.setTitle("Game");
                                    stage.setScene(new Scene(board.build()));
                                    stage.show();
                                }
                            });
                            break;
                        }
                    } catch (ClassNotFoundException | InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                    while(true);
                }
            }

    }
}
