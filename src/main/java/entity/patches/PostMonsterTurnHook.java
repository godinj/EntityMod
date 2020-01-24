package entity.patches;

import basemod.interfaces.PreMonsterTurnSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
                if (m.getMethodName().equals("onEvoke")) {
                    m.replace("{" +
                        "$_ = $proceed($$);" +
                        Inner.class.getName() + ".();" +
                        "}");
                }
            }
        };
    }

    public static class Inner {
        public static boolean publishPostMonsterTurn(AbstractMonster m) {
            for (PreMonsterTurnSubscriber sub : preMonsterTurnSubscribers) {
                if (!sub.receivePreMonsterTurn(m)) {
                    takeTurn = false;
                }
            }
            unsubscribeLaterHelper(PreMonsterTurnSubscriber.class);

            return takeTurn;
        }
    }
}
