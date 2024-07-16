package battlestatsmod;

import static battlestatsmod.BattleStatsMod.imagePath;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import battlestatsmod.ui.Label;
import battlestatsmod.util.ColorHelper;
import battlestatsmod.util.TextureLoader;

public class Overlay {
    private static final Texture TEX_BG = TextureLoader.getTexture(imagePath("screen.png"));
    public static final float startX = (Settings.WIDTH - (TEX_BG.getWidth() * Settings.scale)) * 0.5f;
    public static final float startY = (Settings.HEIGHT - (TEX_BG.getHeight() * Settings.scale)) * 0.5f;

    private ArrayList<Label> labels = new ArrayList<>();

    public Overlay() {

        for (float i = 0.0f; i <= 10.0f; i += 1.0f) {
            for (float j = 0.0f; j <= 10.0f; j += 1.0f) {
                float x = startX + i * 400.0f;
                float y = startY + j * 80.0f;

                this.labels.add(new Label("(" + x + ", " + y + ")", FontHelper.tipBodyFont, x, y, Color.WHITE));
            }
        }
    }

    private void renderBackground(SpriteBatch sb) {
        sb.setColor(ColorHelper.DIM_COLOR);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);

        sb.setColor(Color.WHITE);
        sb.draw(TEX_BG,
                startX,
                startY,
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
