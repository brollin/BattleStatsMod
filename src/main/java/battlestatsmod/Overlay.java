package battlestatsmod;

import static battlestatsmod.BattleStatsMod.imagePath;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import battlestatsmod.ui.Label;
import battlestatsmod.util.ColorHelper;
import battlestatsmod.util.TextureLoader;

public class Overlay {
    private static final Texture TEX_BG = TextureLoader.getTexture(imagePath("screen.png"));
    public static final float leftX = (Settings.WIDTH - (TEX_BG.getWidth() * Settings.scale)) * 0.5f;
    public static final float bottomY = (Settings.HEIGHT - (TEX_BG.getHeight() * Settings.scale)) * 0.5f;
    public static final float topY = bottomY + TEX_BG.getHeight() * Settings.scale;

    public static final float paddingY = 50.0f;
    public static final float paddingX = 50.0f;

    public static final float contentLeftX = leftX + paddingX;
    public static final float contentTopY = topY - paddingY;

    private ArrayList<Label> labels = new ArrayList<>();

    public void update(CombatData combatData) {
        labels = new ArrayList<Label>();
        labels.addAll(Arrays.asList(TurnRow.getHeaderLabels(contentLeftX, contentTopY)));

        for (int i = 0; i < combatData.turns.size(); i++) {
            TurnData turnData = combatData.turns.get(i);
            labels.addAll(Arrays.asList(TurnRow.getLabels(i, turnData, contentLeftX, contentTopY)));
        }
    }

    private void renderBackground(SpriteBatch sb) {
        sb.setColor(ColorHelper.DIM_COLOR);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);

        sb.setColor(Color.WHITE);
        sb.draw(TEX_BG,
                leftX,
                bottomY,
                TEX_BG.getWidth() * Settings.scale,
                TEX_BG.getHeight() * Settings.scale);
    }

    private void renderForeground(SpriteBatch sb) {
        for (Label l : labels)
            l.render(sb);
    }

    public void render(SpriteBatch sb) {
        renderBackground(sb);
        renderForeground(sb);
    }
}
