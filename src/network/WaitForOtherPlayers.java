package network;

import javafx.concurrent.Task;

import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static main.Config.GAME_IS_STARTED;
import static network.Network.receive;
import static network.Network.send;

public class WaitForOtherPlayers extends Task<Long> {
    Socket s;
    public WaitForOtherPlayers(Socket s){
        this.s=s;
    }
    @Override
    protected Long call() throws Exception {
        while (true) {
            TimeUnit.MILLISECONDS.sleep(300);
            send(s, "start?");
            if (receive(s).equals("start")) {
                GAME_IS_STARTED = true;
                break;
            }
        }
        return null;
    }
}
