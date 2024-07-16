package battlestatsmod;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.RenderSubscriber;
import battlestatsmod.util.GeneralUtils;
import battlestatsmod.util.SoundHelper;
import battlestatsmod.util.TextureLoader;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.util.*;

@SpireInitializer
public class BattleStatsMod implements
        PostInitializeSubscriber, RenderSubscriber {
    public static ModInfo info;
    public static String modID;
    static {
        loadModInfo();
    }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); // Used to output to the console.
    private static BattleStatsMod instance;

    public boolean showingOverlay = false;
    private boolean mouseDownRight = false;
    private Overlay overlay;

    public static void initialize() {
        instance = new BattleStatsMod();
    }

    public BattleStatsMod() {
        BaseMod.subscribe(this);
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description,
                null);
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = BattleStatsMod.class.getName(); // getPackage can be iffy with patching, so class name is used
                                                      // instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);
        if (resources.child("images").exists()) {
            return name;
        }

        throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                " Either make sure the folder under resources has the same name as your mod's package, or change the line\n"
                +
                "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                "\tat the top of the " + BattleStatsMod.class.getSimpleName() + " java file.");
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(),
                    Collections.emptySet());
            return initializers.contains(BattleStatsMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    // RenderSubscriber ------------------------------

    @Override
    public void receiveRender(SpriteBatch sb) {
        if (CardCrawlGame.isInARun() && this.showingOverlay) {
            instance.overlay.render(sb);
        }
    }

    private void handleRightClick() {
        if (this.showingOverlay) {
            SoundHelper.closeSound();
            this.showingOverlay = false;
            return;
        }

        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room != null) {
            openOverlay();
        }
    }

    private void openOverlay() {
        SoundHelper.openSound();
        // TODO: prepare for being opened
        this.showingOverlay = true;
    }

    public static void update() {
        if (!CardCrawlGame.isInARun()) {
            return;
        }

        if (InputHelper.isMouseDown_R) {
            instance.mouseDownRight = true;
        } else {
            // We already had the mouse down, and now we released, so fire our right click
            // event
            if (instance.mouseDownRight) {
                instance.handleRightClick();
                instance.mouseDownRight = false;
            }
        }
    }
}
