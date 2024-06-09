package game;

import java.io.BufferedReader;

public class GameFactory {
    private GameFactory() {}

    public static Game getGame(String option, BufferedReader in, String name, String ip, int port) {
        if ("1".equals(option)) {
            return new LocalGame(in, name);
        } else {
            return new RemoteGame(in, name, ip, port);
        }
    }
}
