package battlestatsmod;

import java.util.ArrayList;

public class TurnData {
    public int playerStrength = 0;
    public int enemyDamageReceived = 0;
    public int enemyBlockGenerated = 0;
    public int enemyBlockUtilized = 0;
    public int enemyHealthLost = 0;
    public int enemyHealthGained = 0;
    public int enemyHealthRemaining = 0;

    public int playerDexterity = 0;
    public int playerDamageReceived = 0;
    public int playerBlockGenerated = 0;
    public int playerBlockUtilized = 0;
    public int playerHealthLost = 0;
    public int playerHealthGained = 0;
    public int playerHealthRemaining = 0;

    public int playerEnergySpent = 0;
    public int playerFocus = 0;

    public ArrayList<MinimalCard> cardsDrawn = new ArrayList<>();
    public ArrayList<MinimalCard> cardsPlayed = new ArrayList<>();
    public ArrayList<MinimalCard> cardsDiscarded = new ArrayList<>();
    public ArrayList<MinimalCard> cardsExhausted = new ArrayList<>();
    public ArrayList<String> potionsUsed = new ArrayList<>();

}
