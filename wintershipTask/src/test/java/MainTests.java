import bettingGame.task.Main;
import bettingGame.task.Matches;
import bettingGame.task.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTests {

    @Test
    @DisplayName("Test For ReadMatchData")
    public void testReadMatchData() {
        Path testMatchData = Paths.get("src", "test", "resources", "test_match_data.txt");

        try {
            Matches matches = Main.readMatchData(testMatchData);

            assertEquals(2, matches.getMatches().size());

            List<String> expectedMatchIds = List.of(
                    "abae2255-4255-4304-8589-737cdff61640",
                    "a3815c17-9def-4034-a21f-65369f6d4a56"
            );

            for (int i = 0; i < matches.getMatches().size(); i++) {
                assertEquals(UUID.fromString(expectedMatchIds.get(i)), matches.getMatches().get(i).getMatchId());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test For calculateNetBalanceChange")
    public void testCalculateNetBalanceChange() {
        Player player = new Player();
        player.setPlayerBalance(100L);
        player.setDeposits(Arrays.asList(50L, 30L));
        player.setWithdrawals(Arrays.asList(20L, 10L));

        long netBalanceChange = Main.calculateNetBalanceChange(player);

        assertEquals(50L, netBalanceChange);
    }

}
