package main;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Network {

    public static class Server {
        public String name;
        public String password;
        public int port;
        public int size;
        public int player;

        public Server(Socket socket, String name, String password, int port, int size, int player) {

            this.name = name;
            this.port = port;
            this.size = size;
            this.player = player;
            this.password = password;
        }

    }

    public static void search() {

    }

    public static void send(Socket s, String data) throws IOException, InterruptedException {
        TimeUnit.MILLISECONDS.sleep(5);
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        out.writeUTF(data);
    }

    public static String receive(Socket s) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        return in.readUTF();
    }

    public static Object receiveObject(Socket s) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
        return in.readObject();

    }

}
