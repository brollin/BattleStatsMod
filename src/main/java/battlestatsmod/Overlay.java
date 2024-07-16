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

    private ArrayList<Label> labels = new ArrayList<>();

    public Overlay() {
        this.labels.add(new Label("hello world!", FontHelper.tipBodyFont, 0.0f, 0.0f, Color.WHITE));
    }

    private void renderBackground(SpriteBatch sb) {
        sb.setColor(ColorHelper.DIM_COLOR);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);

        sb.setColor(Color.WHITE);
        sb.draw(TEX_BG,
                startX,
                (Settings.HEIGHT - (TEX_BG.getHeight() * Settings.scale)) * 0.5f,
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
