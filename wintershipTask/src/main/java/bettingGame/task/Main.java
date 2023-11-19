package bettingGame.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Path pathMatchData = Paths.get("src", "main", "resources", "match_data.txt");
        Path pathPlayerData = Paths.get("src", "main", "resources", "player_data.txt");

        try {
            Matches matches = readMatchData(pathMatchData);
            Players players = new Players();
            Casino casino = new Casino();
            casino.setBalance(0L);

            List<String> matchPlayerLines = Files.readAllLines(pathPlayerData);

            for (String matchPlayerLine : matchPlayerLines) {
                processPlayerOperation(players, matchPlayerLine);
            }

            StringBuilder legitimateResult = new StringBuilder();
            StringBuilder illegitimateResult = new StringBuilder();

            for (Player playerLegalCheck : players.getPlayers()) {
                playerLegalCheck.setLegalPlayer(true);
                int totalWonGames = 0;
                int totalPlacedBets = 0;
                long originalBalance = playerLegalCheck.getPlayerBalance();

                for (PlayerOperation playerOperation : playerLegalCheck.getPlayerOperations().getPlayerOperations()) {
                    if (playerLegalCheck.getLegalPlayer()) {
                        if (playerOperation.getOperation().equals("DEPOSIT")) {
                            playerLegalCheck.setPlayerBalance(playerLegalCheck.getPlayerBalance() + playerOperation.getOperationAmount());
                        } else if (playerOperation.getOperation().equals("WITHDRAW")) {
                            if (playerLegalCheck.getPlayerBalance() >= playerOperation.getOperationAmount()) {
                                playerLegalCheck.setPlayerBalance(playerLegalCheck.getPlayerBalance() - playerOperation.getOperationAmount());
                            } else {
                                playerLegalCheck.setLegalPlayer(false);
                                illegitimateResult.append(String.format("%s %s %s %d %s%n", playerLegalCheck.getPlayerId(),
                                        playerOperation.getOperation(),
                                        "null",
                                        playerOperation.getOperationAmount(),
                                        "null"));
                                break;
                            }
                        } else if (playerOperation.getOperation().equals("BET")) {
                            totalPlacedBets++;

                            for (Match match2 : matches.getMatches()) {
                                if (playerLegalCheck.getLegalPlayer()) {
                                    if (match2.getMatchId().equals(playerOperation.getBettingMatchId())) {
                                        if (match2.getResult().equals(playerOperation.getBettingSide())) {
                                            totalWonGames++;
                                            long winAmount = (long) Math.floor(playerOperation.getOperationAmount() * (match2.getResult().equals("A") ? match2.getReturnRateA() : match2.getReturnRateB()));
                                            playerLegalCheck.setPlayerBalance(playerLegalCheck.getPlayerBalance() + winAmount);
                                        } else if (!match2.getResult().equals("DRAW")) {
                                            playerLegalCheck.setPlayerBalance(playerLegalCheck.getPlayerBalance() - playerOperation.getOperationAmount());
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }

                if (!playerLegalCheck.getLegalPlayer()) {
                    playerLegalCheck.setPlayerBalance(originalBalance);
                }

                double winRate = (totalPlacedBets == 0) ? 0 : ((double) totalWonGames / totalPlacedBets);
                if (playerLegalCheck.getLegalPlayer()) {
                    legitimateResult.append(String.format("%s %d %.2f%n", playerLegalCheck.getPlayerId(), playerLegalCheck.getPlayerBalance(), winRate));
                }
            }

            Path resultFilePath = Paths.get("src", "main", "java", "bettingGame", "task", "result.txt");
            Files.writeString(resultFilePath, legitimateResult + System.lineSeparator());
            Files.writeString(resultFilePath, illegitimateResult + System.lineSeparator(), java.nio.file.StandardOpenOption.APPEND);

            for (Player playerLegalCheck : players.getPlayers()) {
                if (playerLegalCheck.getLegalPlayer()) {
                    long netPlayerBalanceChange = calculateNetBalanceChange(playerLegalCheck);
                    casino.setBalance(casino.getBalance() - netPlayerBalanceChange);
                }
            }

            Files.writeString(resultFilePath, String.valueOf(casino.getBalance()), java.nio.file.StandardOpenOption.APPEND);

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error reading files");
        }
    }

    public static Matches readMatchData(Path filePath) throws IOException {
        List<String> matchDataLines = Files.readAllLines(filePath);
        Matches matches = new Matches();

        for (String matchDataLine : matchDataLines) {
            List<String> matchData = List.of(matchDataLine.split(","));
            Match match = new Match();
            match.setMatchId(UUID.fromString(matchData.get(0)));
            match.setReturnRateA(Float.parseFloat(matchData.get(1)));
            match.setReturnRateB(Float.parseFloat(matchData.get(2)));
            match.setResult(matchData.get(3));
            matches.addMatch(match);
        }

        return matches;
    }

    public static void processPlayerOperation(Players players, String playerDataLine) {
        List<String> playerData = List.of(playerDataLine.split(","));
        UUID playerId = UUID.fromString(playerData.get(0));

        Player player = players.getPlayerById(playerId);

        if (player == null) {
            player = new Player();
            player.setPlayerId(playerId);
            player.setPlayerBalance(0L);
            player.setPlayerOperations(new PlayerOperations());
            players.addPlayer(player);
        }

        PlayerOperation playerOperation = new PlayerOperation();
        playerOperation.setOperation(playerData.get(1));

        if (playerData.size() > 4) {
            playerOperation.setBettingMatchId(UUID.fromString(playerData.get(2)));
            playerOperation.setBettingSide(playerData.get(4));
        }

        playerOperation.setOperationAmount(Integer.parseInt(playerData.get(3)));

        if (playerOperation.getOperation().equals("DEPOSIT")) {
            player.getDeposits().add((long) playerOperation.getOperationAmount());
        } else if (playerOperation.getOperation().equals("WITHDRAW")) {
            player.getWithdrawals().add((long) playerOperation.getOperationAmount());
        }

        player.getPlayerOperations().addPlayerOperation(playerOperation);
    }

    public static long calculateNetBalanceChange(Player player) {
        long totalDeposits = player.getDeposits().stream().mapToLong(Long::longValue).sum();
        long totalWithdrawals = player.getWithdrawals().stream().mapToLong(Long::longValue).sum();
        return player.getPlayerBalance() - (totalDeposits - totalWithdrawals);
    }
}
