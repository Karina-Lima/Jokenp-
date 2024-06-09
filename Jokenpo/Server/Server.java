import domain.model.Player;
import domain.usecase.MultiplayerUC;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final boolean RUNNING = true;
    public static final int SERVER_PORT = 6666;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            System.out.println("Servidor rodando na porta " + SERVER_PORT + " e pronto para aceitar conexões...");
            while (RUNNING) {
                final Socket playerSession = server.accept();
                new Thread(() -> handlePlayer(playerSession)).start();
            }
        } catch (Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static void handlePlayer(Socket playerSession) {
        try {
            final DataInputStream dis = new DataInputStream(playerSession.getInputStream());
            final String name = dis.readUTF();

            final Player player = new Player(name, 0, 0, playerSession);

            MultiplayerUC.addPlayer(player);

            System.out.println(name.concat(" - entrou no servidor"));
        } catch (Exception e) {
            System.out.println("Erro ao lidar com o jogador: " + e.getMessage());
        }
    }
}
