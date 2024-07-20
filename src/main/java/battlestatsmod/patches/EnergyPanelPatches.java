package battlestatsmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import battlestatsmod.BattleStatsMod;

public class EnergyPanelPatches {
    @SpirePatch(clz = EnergyPanel.class, method = "addEnergy")
    public static class AddEnergyPatch {
        public static void Postfix(int energy) {
            BattleStatsMod.instance.combatData.getCurrentTurn().playerEnergyGained += energy;
        }
    }

    @SpirePatch(clz = EnergyPanel.class, method = "useEnergy")
    public static class UseEnergyPatch {
        public static void Postfix(int energy) {
            BattleStatsMod.instance.combatData.getCurrentTurn().playerEnergyUsed += energy;
        }
    }
}
