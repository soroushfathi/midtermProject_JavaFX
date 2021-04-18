package network;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;


public class Network {

    public static class Server {
        private final String name;
        private final String password;
        private final int port;
        private final int size;
        private final int player;

        public Server(String name, String password, int port, int size, int player) {

            this.name = name;
            this.port = port;
            this.size = size;
            this.player = player;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public int getPort() {
            return port;
        }

        public int getSize() {
            return size;
        }

        public int getPlayer() {
            return player;
        }
    }

    public static void send(@NotNull Socket s, String data) throws IOException, InterruptedException {
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        out.writeUTF(data);
    }

    public static String receive(Socket s) throws IOException {
        DataInputStream in = new DataInputStream(s.getInputStream());
        return in.readUTF();
    }

    public static Object receiveObject(Socket s) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
        return in.readObject();

    }

}
