package battlestatsmod.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

public class ColorHelper {
    public static final Color DIM_COLOR = new Color(0.0f, 0.0f, 0.0f, 0.75f);

    public static final Color GRAY_COLOR = new Color(0.6f, 0.6f, 0.6f, 1.0f);
    public static final Color DARK_GRAY_COLOR = new Color(0.2f, 0.2f, 0.2f, 1.0f);

    public static final Color BUFF_COLOR = new Color(0.596f, 0.306f, 0.639f, 1.0f);
    public static final Color DEBUFF_COLOR = new Color(1.0f, 0.498f, 0.0f, 1.0f);
    public static final Color BLOCK_COLOR = new Color(0.337f, 0.612f, 0.843f, 1.0f);
    public static final Color DAMAGE_COLOR = new Color(0.91f, 0.22f, 0.227f, 1.0f);
    public static final Color SPECIAL_COLOR = new Color(0.302f, 0.686f, 0.29f, 1.0f);

    public static final Color RED_COLOR = Settings.RED_TEXT_COLOR.cpy();

    public static Color TEXT_BORDER_COLOR = new Color(0.23f, 0.26f, 0.317f, 1.0f);

    public static Color rainbow() {
        float r = (MathUtils.cosDeg((float) (System.currentTimeMillis() / 10L % 360L)) + 1.25F) / 2.3F;
        float g = (MathUtils.cosDeg((float) ((System.currentTimeMillis() + 1000L) / 10L % 360L)) + 1.25F) / 2.3F;
        float b = (MathUtils.cosDeg((float) ((System.currentTimeMillis() + 2000L) / 10L % 360L)) + 1.25F) / 2.3F;
        return new Color(r, g, b, 1.0f);
    }
}
