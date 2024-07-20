package battlestatsmod;

import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import basemod.interfaces.OnPlayerLoseBlockSubscriber;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostPotionUseSubscriber;
import basemod.interfaces.PreMonsterTurnSubscriber;
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
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.util.*;

@SpireInitializer
public class BattleStatsMod implements
        PostInitializeSubscriber, RenderSubscriber, OnPlayerTurnStartSubscriber, OnCardUseSubscriber,
        PostPotionUseSubscriber, PreMonsterTurnSubscriber, PostBattleSubscriber, OnStartBattleSubscriber,
        PostDrawSubscriber, PostExhaustSubscriber, OnPlayerDamagedSubscriber, OnPlayerLoseBlockSubscriber {
    private void saveTurnData() {
        AbstractPlayer player = AbstractDungeon.player;
        TurnData currentTurn = combatData.getCurrentTurn();
        TurnData previousTurn = combatData.getPreviousTurn();
        try {
            currentTurn.playerStrength = player.hasPower("Strength")
                    ? player.getPower("Strength").amount
                    : 0;
            currentTurn.playerDexterity = player.hasPower("Dexterity")
                    ? player.getPower("Dexterity").amount
                    : 0;
            currentTurn.playerFocus = player.hasPower("Focus") ? player.getPower("Focus").amount : 0;
            currentTurn.playerHealthRemaining = player.currentHealth;
            if (previousTurn != null) {
                currentTurn.playerHealthLost = previousTurn.playerHealthRemaining - player.currentHealth;
            }

            // loop through all monsters
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                // currentTurn.enemyDamageReceived += m.damage;
                // currentTurn.enemyBlockGenerated += m.currentBlock;
                // currentTurn.enemyHealthLost += m.maxHealth - m.currentHealth;
                // currentTurn.enemyHealthGained += m.currentHealth -
                // m.maxHealth;
                currentTurn.enemyHealthRemaining += m.currentHealth;
                currentTurn.enemyBlockGenerated += m.currentBlock;
            }
        } catch (Exception e) {
            logger.error("Error in saveTurnData: " + e.getMessage());
        }
    }

    // OnStartBattleSubscriber ------------------------------
    @Override
    public void receiveOnBattleStart(AbstractRoom r) {
        logger.info(modID + " received OnBattleStart.");
        this.combatData = new CombatData();
        combatData.addNewTurn(); // turn 0; captures starting state of the battle
    }

    // OnPlayerTurnStartSubscriber ------------------------------
    @Override
    public void receiveOnPlayerTurnStart() {
        logger.info(modID + " received OnPlayerTurnStart.");
        // save data for prior turn
        saveTurnData();
        combatData.addNewTurn();
    }

    // PostDrawSubscriber ------------------------------
    @Override
    public void receivePostDraw(AbstractCard c) {
        logger.info(modID + " received PostDraw: " + c.cardID);
        combatData.getCurrentTurn().cardsDrawn.add(MinimalCard.fromCard(c));
    }

    // OnCardUseSubscriber ------------------------------
    @Override
    public void receiveCardUsed(AbstractCard card) {
        logger.info(modID + " received CardUsed: " + card.cardID);
        combatData.getCurrentTurn().cardsPlayed.add(MinimalCard.fromCard(card));
    }

    // PostExhaustSubscriber ------------------------------
    @Override
    public void receivePostExhaust(AbstractCard c) {
        logger.info(modID + " received PostExhaust: " + c.cardID);
        combatData.getCurrentTurn().cardsExhausted.add(MinimalCard.fromCard(c));
    }

    // PostPotionUseSubscriber ------------------------------
    @Override
    public void receivePostPotionUse(AbstractPotion potion) {
        logger.info(modID + " received PostPotionUse: " + potion.name);
        combatData.getCurrentTurn().potionsUsed.add(potion.ID);
    }

    // PreMonsterTurnSubscriber ------------------------------
    @Override
    public boolean receivePreMonsterTurn(AbstractMonster m) {
        logger.info(modID + " received PreMonsterTurn: " + m.id);
        return true;
    }

    // OnPlayerDamagedSubscriber ------------------------------
    @Override
    public int receiveOnPlayerDamaged(int damageAmount, DamageInfo info) {
        logger.info(modID + " received OnPlayerDamaged: " + damageAmount);
        combatData.getCurrentTurn().playerDamageReceived += damageAmount;
        return damageAmount;
    }

    // OnPlayerLoseBlockSubscriber ------------------------------
    @Override
    public int receiveOnPlayerLoseBlock(int blockAmount) {
        logger.info(modID + " received OnPlayerLoseBlock: " + blockAmount);
        return blockAmount;
    }

    // PostBattleSubscriber ------------------------------
    @Override
    public void receivePostBattle(AbstractRoom r) {
        logger.info(modID + " received PostBattle.");
        saveTurnData();
        // TODO: only calculate this if stats are viewed
        combatData.calculateTotals();
    }

    public static ModInfo info;
    public static String modID;
    static {
        loadModInfo();
    }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); // Used to output to the console.
    public static BattleStatsMod instance;

    public boolean showingOverlay = false;
    private boolean mouseDownRight = false;
    private Overlay overlay;
    public CombatData combatData = new CombatData();

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

        instance.overlay = new Overlay();
        TurnRow.initialize();
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
        saveTurnData();
        overlay.update(combatData);
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
