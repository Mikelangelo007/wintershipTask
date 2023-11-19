package bettingGame.task;

import java.util.ArrayList;
import java.util.List;

public class PlayerOperations {
    List<PlayerOperation> playerOperations;
    public PlayerOperations(){
        this.playerOperations = new ArrayList<>();
    }
    public void addPlayerOperation(PlayerOperation playerOperations) {
        this.playerOperations.add(playerOperations);
    }
    public List<PlayerOperation> getPlayerOperations() {
        return playerOperations;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("PlayerOperations{\n");

        for (PlayerOperation playerOperation : playerOperations) {
            stringBuilder.append("\t").append(playerOperation).append(",\n");
        }

        if (!playerOperations.isEmpty()) {
            // Remove the trailing comma and newline for the last player operation
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        stringBuilder.append("\n}");

        return stringBuilder.toString();
    }
}
