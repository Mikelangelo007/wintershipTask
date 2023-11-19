package bettingGame.task;

import lombok.Data;

@Data
public class Casino {
    Long balance;

    public Long getBalance() {
        return balance;
    }
    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
