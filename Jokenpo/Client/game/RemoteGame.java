package game;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RemoteGame implements Game {
    private final BufferedReader in;
    private final String name;
    private final String ip;
    private final int port;

    public RemoteGame(BufferedReader in, String name, String ip, int port) {
        this.in = in;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public void start() {
        try {
            Socket s = new Socket(ip, port);

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            dout.writeUTF(name);

            String response;

            while ((response = dis.readUTF()) != null) {
                System.out.println(response);
                if (response.contains("Qual a sua jogada: pedra, papel, ou tesoura?")) {
                    String move = in.readLine();
                    dout.writeUTF(move);
                } else if (response.contains("Deseja continuar? (s/n)")) {
                    String continueGame = in.readLine();
                    dout.writeUTF(continueGame);
                    if (continueGame.equalsIgnoreCase("n")) {
                        break;
                    }
                } else if (response.contains("Você saiu da partida.") || response.contains("Seu oponente saiu da partida.")) {
                    break;
                }
            }

            dout.flush();
            dout.close();
            s.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
