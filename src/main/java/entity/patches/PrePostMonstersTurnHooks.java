package entity.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import entity.EntityMod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class PrePostMonstersTurnHooks {
    @SpirePatch(
        cls="com.megacrit.cardcrawl.monsters.MonsterGroup",
        method="applyPreTurnLogic"
    )
    public static class PreMonsterTurnHook {
        public static SpireReturn<Void> Prefix(MonsterGroup _instance) {
            EntityMod.publishPreMonstersTurn();
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
        cls="com.megacrit.cardcrawl.monsters.MonsterGroup",
        method="applyEndOfTurnPowers"
    )
    public static class PostMonsterTurnHook {
        public static void Postfix(MonsterGroup _instance) {
            EntityMod.publishPostMonstersTurn();
        }
    }
}
