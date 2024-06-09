package domain.usecase;

import domain.model.Player;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiplayerUC {
    private MultiplayerUC() {}

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static final Queue<Player> PLAYER_QUEUE = new LinkedList<>();

    public static void addPlayer(Player player) {
        checkWaitPlayers(player);
    }

    private static void checkWaitPlayers(Player player) {
        synchronized (PLAYER_QUEUE) {
            if (PLAYER_QUEUE.size() > 0) {
                final Player player2 = PLAYER_QUEUE.poll(); 

                EXECUTOR_SERVICE.execute(() -> {
                    try {
                        new GameUC().startGame(player, player2);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else {
                PLAYER_QUEUE.add(player);
            }
        }
    }
}
