package game;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.System.out;

public class LocalGame implements Game{
    private static final String[] moves = {"pedra", "papel", "tesoura"};
    private final BufferedReader in;
    private final String name;

    public LocalGame(BufferedReader in, String name) {
        this.in = in;
        this.name = name;
    }

    public void start() throws IOException {
        out.println("Iniciando partida contra CPU");

        int wins = 0;
        int losses = 0;

        while (true) {

            final String cpuMove = moves[(int) (Math.random() * 3)];

            out.println("Qual a sua jogada: pedra, papel, ou tesoura?");

            String playerMove = in.readLine();

            if (playerMove.equalsIgnoreCase("sair")) {
                break;
            }

            String result = determineWinner(playerMove, cpuMove);

            if (result.equals("Voce venceu!")) {
                wins++;
            } else if (result.equals("Voce perdeu!")) {
                losses++;
            }
            out.println("CPU escolheu " + cpuMove + ", e voce escolheu " + playerMove + ": " + result);
            out.println("===================================");
            out.println(name + ": " + wins + " Vitorias | " + losses + " Derrotas");
            out.println("===================================");
            out.println("Deseja continuar? (s/n)");
            if (!"s".equalsIgnoreCase(in.readLine())) {
                break;
            }
        }
    }


    private String determineWinner(String playerMove, String opponentMove) {
        if (playerMove.equals(opponentMove)) {
            return "Empate!";
        }
        switch (playerMove) {
            case "pedra":
                return (opponentMove.equals("tesoura")) ? "Voce venceu!" : "Voce perdeu!";
            case "papel":
                return (opponentMove.equals("pedra")) ? "Voce venceu!" : "Voce perdeu!";
            case "tesoura":
                return (opponentMove.equals("papel")) ? "Voce venceu!" : "Voce perdeu!";
            default:
                return "Jogada invalida!";
        }
    }
}
