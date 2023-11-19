package bettingGame.task;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Setter
public class Player {
    UUID playerId;
    long playerBalance;
    Boolean legalPlayer;
    PlayerOperations playerOperations;

    private List<Long> deposits;
    private List<Long> withdraws;
    public Player() {
        this.deposits = new ArrayList<>();
        this.withdraws = new ArrayList<>();
    }
    public UUID getPlayerId(){
        return playerId;
    }
    public List<Long> getDeposits() {
        return deposits;
    }

    public List<Long> getWithdrawals() {
        return withdraws;
    }
    public long getPlayerBalance(){
        return playerBalance;
    }
    public  Boolean getLegalPlayer(){
        return legalPlayer;
    }
    public void setLegalPlayer(Boolean legalPlayer){
        this.legalPlayer = legalPlayer;
    }
    public void setPlayerId(UUID playerId){
        this.playerId = playerId;
    }

    public void setPlayerOperations(PlayerOperations playerOperations){
        this.playerOperations = playerOperations;
    }
    public PlayerOperations getPlayerOperations() {
        return playerOperations;
    }

    public void setPlayerBalance(long playerBalance){
        this.playerBalance = playerBalance;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerBalance="+ playerBalance +
                ", legalPlayer=" + legalPlayer +
                ", playerOperations=" + playerOperations +
                '}';
    }


    public void setWithdrawals(List<Long> asList) {
        this.withdraws = asList;
    }

    public void setDeposits(List<Long> deposits){
        this.deposits = deposits;
    }
}
