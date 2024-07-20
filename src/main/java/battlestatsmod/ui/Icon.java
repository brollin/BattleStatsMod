package battlestatsmod.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.AbstractDrawable;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Icon extends AbstractDrawable {
    public TextureAtlas.AtlasRegion region48;
    public float x;
    public float y;

    public Icon(String regionName, float x, float y) {
        this.region48 = AbstractPower.atlas.findRegion(regionName);
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(this.region48, x, y,
                this.region48.packedWidth / 2.0F, this.region48.packedHeight / 2.0F, this.region48.packedWidth,
                this.region48.packedHeight, Settings.scale, Settings.scale, 0.0F);
    }

}
