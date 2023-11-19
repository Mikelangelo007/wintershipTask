package bettingGame.task;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Matches {
    List<Match> matches;

    public Matches(){
        this.matches = new ArrayList<>();
    }
    public void addMatch(Match match) {
        this.matches.add(match);
    }
    public List<Match> getMatches(){
        return matches;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Matches:\n");

        for (Match match : matches) {
            stringBuilder.append(match).append("\n");
        }

        return stringBuilder.toString();
    }
}
