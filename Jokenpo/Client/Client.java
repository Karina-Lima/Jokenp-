import game.GameFactory;

import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {

        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

        System.out.print("Por favor, informe seu nome: ");
        final String name = reader.readLine();

        System.out.print("Informe qual é o servidor (ipv4): ");
        final String ip = reader.readLine();

        System.out.print("Informe qual é o host do servidor (porta): ");
        final String port = reader.readLine();

        System.out.print("Informe qual partida deseja jogar: (1) Jogar contra um oponente CPU (2) Jogar contra um adversário real: ");
        final String option = reader.readLine();

        GameFactory.getGame(option, reader, name, ip, Integer.parseInt(port)).start();
    }
}
