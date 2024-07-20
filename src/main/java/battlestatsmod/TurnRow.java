package battlestatsmod;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.AbstractDrawable;
import com.megacrit.cardcrawl.helpers.FontHelper;

import battlestatsmod.ui.Icon;
import battlestatsmod.ui.Label;
import battlestatsmod.ui.SmartLabel;

public class TurnRow {
        public static String timeTexturePath = "48/time";
        public static String strengthTexturePath = "48/strength";
        public static String dexterityTexturePath = "48/dexterity";

        public static final int DEFAULT_CELL_WIDTH = 100;
        public static final int HORIZONTAL_GAP = 15;
        public static final int ROW_HEIGHT = 40;
        public static final int LINE_HEIGHT = 35;
        public static final int HEADER_ROW_SIZE = 3;

        public static void initialize() {

        }

        public static DataCell[] getDataCells(int turnNumber, TurnData t) {
                return new DataCell[] {
                                new DataCell("Turn", timeTexturePath,
                                                Integer.toString(turnNumber),
                                                48, 48),
                                new DataCell("Player Strength", strengthTexturePath,
                                                Integer.toString(t.playerStrength),
                                                48, 48),
                                new DataCell("NL Dmg Dealt", null, Integer.toString(t.enemyDamageReceived),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("NL Enemy Block", null, Integer.toString(t.enemyBlockGenerated),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("Enemy Block Used", null, Integer.toString(t.enemyBlockUtilized),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("Enemy Health Lost", null, Integer.toString(t.enemyHealthLost),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                // new DataCell("Enemy Health Gained", null,
                                // Integer.toString(data.enemyHealthGained),
                                // DEFAULT_CELL_WIDTH,
                                // ROW_HEIGHT),
                                new DataCell("Enemy Health Left", null,
                                                Integer.toString(t.enemyHealthRemaining),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("Player Dexterity", dexterityTexturePath,
                                                Integer.toString(t.playerDexterity),
                                                48, 48),
                                new DataCell("NL Dmg Rcvd", null,
                                                Integer.toString(t.playerDamageReceived),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("NL Player Block", null,
                                                Integer.toString(t.playerBlockGenerated),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("Player Block Used", null, Integer.toString(t.playerBlockUtilized),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("Player Health Lost", null, Integer.toString(t.playerHealthLost),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                // new DataCell("Player Health Gained", null,
                                // Integer.toString(t.playerHealthGained),
                                // DEFAULT_CELL_WIDTH,
                                // ROW_HEIGHT),
                                new DataCell("Player Health Left", null,
                                                Integer.toString(t.playerHealthRemaining),
                                                DEFAULT_CELL_WIDTH, ROW_HEIGHT),
                                new DataCell("NL Energy Gained", null, Integer.toString(t.playerEnergyGained),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("NL Energy Used", null, Integer.toString(t.playerEnergyUsed),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                // new DataCell("Player Focus",null, Integer.toString(data.playerFocus),
                                // DEFAULT_CELL_WIDTH,
                                // ROW_HEIGHT),
                                new DataCell("NL Cards Drawn", null, Integer.toString(t.cardsDrawn.size()),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("NL Cards Played", null, Integer.toString(t.cardsPlayed.size()),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                // new DataCell("Cards Disca- rded", null,
                                // Integer.toString(t.cardsDiscarded.size()),
                                // DEFAULT_CELL_WIDTH,
                                // ROW_HEIGHT),
                                new DataCell("Cards Exhau- sted", null, Integer.toString(t.cardsExhausted.size()),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT),
                                new DataCell("NL Potions Used", null, Integer.toString(t.potionsUsed.size()),
                                                DEFAULT_CELL_WIDTH,
                                                ROW_HEIGHT)
                };
        }

        public static Label[] getElements(int turnNumber, TurnData data, float tableLeftX, float tableTopY) {
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

        public static ArrayList<AbstractDrawable> getHeaderElements(float tableLeftX, float tableTopY) {
                DataCell[] cells = getDataCells(0, new TurnData());
                ArrayList<AbstractDrawable> headerElements = new ArrayList<>();
                float currentX = tableLeftX;
                float currentY = tableTopY;
                for (int i = 0; i < cells.length; i++) {
                        DataCell cell = cells[i];
                        if (cell.texturePath != null) {
                                headerElements.add(new Icon(cell.texturePath, currentX, currentY - 3 * LINE_HEIGHT));
                        } else {
                                headerElements.add(
                                                new SmartLabel(cell.header, FontHelper.tipBodyFont, currentX, currentY,
                                                                cell.width,
                                                                LINE_HEIGHT,
                                                                Settings.GOLD_COLOR));
                        }
                        currentX += cell.width + HORIZONTAL_GAP;
                }
                return headerElements;
        }

}
