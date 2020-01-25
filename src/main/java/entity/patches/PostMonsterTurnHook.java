package entity.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(
    cls="com.megacrit.cardcrawl.actions.GameActionManager",
    method="getNextAction"
)
public class PostMonsterTurnHook {
    public static ExprEditor Instrument()
    {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException
            {
                if (m.getClassName().equals(AbstractMonster.class.getName()) && m.getMethodName().equals("takeTurn")) {
                    m.replace("{" +
                        "$_ = $proceed();" +
                        EntityMod.class.getName() + ".receivePostMonsterTurn(m);" +
                        "}");
                }
            }
        };
    }
}
