package entity.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import entity.EntityMod;

public class PostPlayerTurnHook {
    @SpirePatch(
        cls="com.megacrit.cardcrawl.core.AbstractCreature",
        method="applyEndOfTurnTriggers"
    )
    public static class PreMonsterTurnHook {
        public static SpireReturn<Void> Postfix(AbstractCreature _instance) {
            if (_instance.isPlayer) {
                EntityMod.publishPostPlayerTurn();
            }
            return SpireReturn.Continue();
        }
    }
}
