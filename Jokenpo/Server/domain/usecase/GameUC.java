package domain.usecase;

import domain.model.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GameUC {
    public void startGame(Player player1, Player player2) throws IOException {
        final DataInputStream player1DIS = new DataInputStream(player1.getSession().getInputStream());
        final DataInputStream player2DIS = new DataInputStream(player2.getSession().getInputStream());

        final DataOutputStream player1DOS = new DataOutputStream(player1.getSession().getOutputStream());
        final DataOutputStream player2DOS = new DataOutputStream(player2.getSession().getOutputStream());


        player1DOS.writeUTF("Iniciando partida contra " + player2.getName());
        player2DOS.writeUTF("Iniciando partida contra " + player1.getName());

        while (true) {
            player1DOS.writeUTF("Qual a sua jogada: pedra, papel, ou tesoura?");

            final String playerMove = player1DIS.readUTF();

            if (playerMove.equalsIgnoreCase("sair")) {
                player1DOS.writeUTF("Voce saiu da partida.");
                player2DOS.writeUTF("Seu oponente saiu da partida.");
                break;
            }

            player2DOS.writeUTF(player1.getName() + " ja fez sua jogada.");
            player2DOS.writeUTF("Qual a sua jogada: pedra, papel, ou tesoura?");

            final String opponentMove = player2DIS.readUTF();

            if (opponentMove.equalsIgnoreCase("sair")) {
                player2DOS.writeUTF("Voce saiu da partida.");
                player1DOS.writeUTF("Seu oponente saiu da partida.");
            }

            String result = determineWinner(playerMove, opponentMove);

            if (result.equals("Voce venceu!")) {
                player1.setWins(player1.getWins()+1);
                player2.setLoss(player2.getLoss()+1);
            } else if (result.equals("Voce perdeu!")) {
                player2.setWins(player2.getWins()+1);
                player1.setLoss(player1.getLoss()+1);
            }
            player1DOS.writeUTF(player2.getName() + " escolheu " + opponentMove + ", e voce escolheu " + playerMove + ": " + result);
            player2DOS.writeUTF(player1.getName() + " escolheu " + playerMove + ", e voce escolheu " + opponentMove + ": " + determineWinner(opponentMove, playerMove));

            printPlacar(player1DOS,player1, player2);
            printPlacar(player2DOS,player2, player1);

            player1DOS.writeUTF("Deseja continuar? (s/n)");
            player2DOS.writeUTF("Deseja continuar? (s/n)");
            if (!"s".equalsIgnoreCase(player1DIS.readUTF()) || !"s".equalsIgnoreCase(player2DIS.readUTF())) {
                player1DOS.writeUTF("Voce saiu da partida.");
                player2DOS.writeUTF("Seu oponente saiu da partida.");
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

    private void printPlacar(DataOutputStream dos, Player player1, Player player2) throws IOException {
        dos.writeUTF("===================================");
        dos.writeUTF(player1.getName() + ": " + player1.getWins() + " Vitorias | " + player1.getLoss() + " Derrotas");
        dos.writeUTF(player2.getName() + ": " + player2.getWins() + " Vitorias | " + player2.getLoss() + " Derrotas");
        dos.writeUTF("===================================");
    }
}
