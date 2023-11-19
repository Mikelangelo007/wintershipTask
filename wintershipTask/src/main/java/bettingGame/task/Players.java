package bettingGame.task;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Players {
    List<Player> players;

    public Players() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void iteratePlayers() {
        for (Player player : players) {
            System.out.println(player);
        }
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getPlayerById(UUID playerId) {
        for (Player player : players) {
            if (player.getPlayerId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Players{\n");

        for (Player player : players) {
            stringBuilder.append("Player UUID: ").append(player.getPlayerId()).append("\n");
            stringBuilder.append(player.getPlayerOperations()).append("\n");
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }


}