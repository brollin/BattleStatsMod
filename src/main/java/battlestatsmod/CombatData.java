package battlestatsmod;

import java.util.ArrayList;

public class CombatData {
    public ArrayList<TurnData> turns = new ArrayList<>();
    public TurnData turnTotals = new TurnData();

    public void addNewTurn() {
        turns.add(new TurnData());
    }

    public TurnData getCurrentTurn() {
        return turns.get(turns.size() - 1);
    }

    public TurnData getPreviousTurn() {
        if (turns.size() > 1) {
            return turns.get(turns.size() - 2);
        }
        return null;
    }

    public void calculateTotals() {
        for (TurnData turn : turns) {
            turnTotals.playerStrength += turn.playerStrength;
            turnTotals.enemyDamageReceived += turn.enemyDamageReceived;
            turnTotals.enemyBlockGenerated += turn.enemyBlockGenerated;
            turnTotals.enemyBlockUtilized += turn.enemyBlockUtilized;
            turnTotals.enemyHealthLost += turn.enemyHealthLost;
            turnTotals.enemyHealthGained += turn.enemyHealthGained;
            turnTotals.enemyHealthRemaining += turn.enemyHealthRemaining;

            turnTotals.playerDexterity += turn.playerDexterity;
            turnTotals.playerDamageReceived += turn.playerDamageReceived;
            turnTotals.playerBlockGenerated += turn.playerBlockGenerated;
            turnTotals.playerBlockUtilized += turn.playerBlockUtilized;
            turnTotals.playerHealthLost += turn.playerHealthLost;
            turnTotals.playerHealthGained += turn.playerHealthGained;
            turnTotals.playerHealthRemaining += turn.playerHealthRemaining;

            turnTotals.playerEnergyUsed += turn.playerEnergyUsed;
            turnTotals.playerFocus += turn.playerFocus;

            turnTotals.cardsDrawn.addAll(turn.cardsDrawn);
            turnTotals.cardsPlayed.addAll(turn.cardsPlayed);
            turnTotals.cardsDiscarded.addAll(turn.cardsDiscarded);
            turnTotals.cardsExhausted.addAll(turn.cardsExhausted);
            turnTotals.potionsUsed.addAll(turn.potionsUsed);
        }
    }

}
