package domain.model;

import java.net.Socket;

public class Player {
    private String name;
    private int wins;
    private int loss;
    private Socket session;

    public Player(final String name, final int wins, final int loss, final Socket session) {
        this.name = name;
        this.wins = wins;
        this.loss = loss;
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public Socket getSession() {
        return session;
    }

    public void setSession(Socket session) {
        this.session = session;
    }
}
