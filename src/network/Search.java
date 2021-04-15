package network;

import javafx.concurrent.Task;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static main.Config.SERVER_RESPONSE_TIMEOUT;
import static network.Network.*;

public class Search  extends Task<Long> {
    public static List<Server> servers = new ArrayList<Server>();
    @Override
    protected Long call() throws Exception {
        servers.clear();
        for (int i = 1; i < 100; i++) {
            try {
                Socket s = new Socket("localhost", i);
                if (s.isConnected()) {
                    s.setSoTimeout(SERVER_RESPONSE_TIMEOUT);
                    while (true) {
                        try {
                            if (receive(s).equals("hello!")) {
                                s.setSoTimeout(0);
                                send(s, "size");
                                var size = Integer.parseInt(receive(s));
                                send(s, "player");
                                var player = Integer.parseInt(receive(s));
                                send(s, "name");
                                var name = receive(s);
                                send(s, "password");
                                var password = receive(s);
                                send(s, "close");
                                servers.add(new Network.Server(name, password, i, size, player));
                                break;
                            }
                        } catch (SocketTimeoutException e) {
                            // timeout exception.
                            //System.out.println(e);
                            break;
                        }
                    }
                    s.close();
                }



            } catch (IOException ignored) {

            }
        }
        return 0L;
    }
}
