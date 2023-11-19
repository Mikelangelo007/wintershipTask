package bettingGame.task;

import lombok.Data;

import java.util.UUID;

@Data
public class PlayerOperation {
    UUID bettingMatchId;
    int operationAmount;

    String bettingSide;

    String operation;

    public UUID getBettingMatchId() {
        return bettingMatchId;
    }

    public String getBettingSide(){
        return bettingSide;
    }
    public String getOperation(){
        return operation;
    }
    public int getOperationAmount(){
        return operationAmount;
    }

    public void setOperation(String operation){
        this.operation = operation;
    }

    public void setBettingMatchId(UUID bettingMatchId){
        this.bettingMatchId = bettingMatchId;
    }

    public void setOperationAmount(int operationAmount){
        this.operationAmount = operationAmount;
    }

    public void setBettingSide(String bettingSide){
        this.bettingSide = bettingSide;
    }

    @Override
    public String toString() {
        return "PlayerOperation{" +
                "bettingMatchId=" + bettingMatchId +
                ", operationAmount=" + operationAmount +
                ", bettingSide='" + bettingSide + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }

}
