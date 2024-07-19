package battlestatsmod;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class MinimalCard {
    public String cardID;
    public boolean upgraded;

    public MinimalCard(String cardID, boolean upgraded) {
        this.cardID = cardID;
        this.upgraded = upgraded;
    }

    public static MinimalCard fromCard(AbstractCard card) {
        return new MinimalCard(card.cardID, card.upgraded);
    }

}
