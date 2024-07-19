package battlestatsmod;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.FontHelper;

import battlestatsmod.ui.Label;
import battlestatsmod.ui.SmartLabel;

public class TurnRow {
    public static final int DEFAULT_CELL_WIDTH = 95;
    public static final int HORIZONTAL_GAP = 5;
    public static final int ROW_HEIGHT = 40;
    public static final int LINE_HEIGHT = 25;
    public static final int HEADER_ROW_SIZE = 3;

    public static DataCell[] getDataCells(int turnNumber, TurnData data) {
        return new DataCell[] {
                new DataCell("Turn", Integer.toString(turnNumber), DEFAULT_CELL_WIDTH, ROW_HEIGHT),
                new DataCell("Player Strength", Integer.toString(data.playerStrength), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Enemy Damage Received", Integer.toString(data.enemyDamageReceived), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Enemy Block Generated", Integer.toString(data.enemyBlockGenerated), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Enemy Block Utilized", Integer.toString(data.enemyBlockUtilized), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Enemy Health Lost", Integer.toString(data.enemyHealthLost), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Enemy Health Gained", Integer.toString(data.enemyHealthGained), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Enemy Health Remaining", Integer.toString(data.enemyHealthRemaining), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Player 10Dexterity", Integer.toString(data.playerDexterity), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Player Damage Received", Integer.toString(data.playerDamageReceived), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Player Block Generated", Integer.toString(data.playerBlockGenerated), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Player Block Utilized", Integer.toString(data.playerBlockUtilized), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Player Health Lost", Integer.toString(data.playerHealthLost), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Player Health Gained", Integer.toString(data.playerHealthGained), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Player Health Remaining", Integer.toString(data.playerHealthRemaining),
                        DEFAULT_CELL_WIDTH, ROW_HEIGHT),
                new DataCell("Player Energy Spent", Integer.toString(data.playerEnergySpent), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Player Focus", Integer.toString(data.playerFocus), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Cards Drawn", Integer.toString(data.cardsDrawn.size()), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Cards Played", Integer.toString(data.cardsPlayed.size()), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Cards Discarded", Integer.toString(data.cardsDiscarded.size()), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Cards Exhausted", Integer.toString(data.cardsExhausted.size()), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT),
                new DataCell("Potions Used", Integer.toString(data.potionsUsed.size()), DEFAULT_CELL_WIDTH,
                        ROW_HEIGHT)
        };
    }

    public static Label[] getLabels(int turnNumber, TurnData data, float tableLeftX, float tableTopY) {
        DataCell[] cells = getDataCells(turnNumber, data);
        Label[] labels = new Label[cells.length];
        float currentX = tableLeftX;
        float currentY = tableTopY - (turnNumber + HEADER_ROW_SIZE + 1) * ROW_HEIGHT;
        for (int i = 0; i < cells.length; i++) {
            labels[i] = new Label(cells[i].text, FontHelper.tipBodyFont, currentX, currentY, Color.WHITE);
            currentX += cells[i].width + HORIZONTAL_GAP;
        }
        return labels;
    }

    public static Label[] getHeaderLabels(float tableLeftX, float tableTopY) {
        DataCell[] cells = getDataCells(0, new TurnData());
        Label[] labels = new Label[cells.length];
        float currentX = tableLeftX;
        float currentY = tableTopY;
        for (int i = 0; i < cells.length; i++) {
            labels[i] = new SmartLabel(cells[i].header, FontHelper.tipBodyFont, currentX, currentY, cells[i].width,
                    LINE_HEIGHT,
                    Color.WHITE);
            currentX += cells[i].width + HORIZONTAL_GAP;
        }
        return labels;
    }

}
