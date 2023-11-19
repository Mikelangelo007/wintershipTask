package bettingGame.task;

import lombok.Data;

import java.util.UUID;

@Data
public class Match {
    UUID matchId;
    float returnRateA;
    float returnRateB;
    String result;

    public String getResult(){
        return result;
    }

    public UUID getMatchId(){
        return matchId;
    }

    public float getReturnRateA(){
        return returnRateA;
    }
    public float getReturnRateB(){
        return returnRateB;
    }
    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public void setReturnRateA(float returnRateA) {
        this.returnRateA = returnRateA;
    }

    public void setReturnRateB(float returnRateB) {
        this.returnRateB = returnRateB;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // Add getReturnRate method
    public float getReturnRate(String side) {
        return (side.equals("A")) ? returnRateA : returnRateB;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", returnRateA=" + returnRateA +
                ", returnRateB=" + returnRateB +
                ", result='" + result + '\'' +
                '}';
    }
}
