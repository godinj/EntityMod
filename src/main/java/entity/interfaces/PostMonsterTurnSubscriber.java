package entity.interfaces;

import basemod.interfaces.ISubscriber;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface PostMonsterTurnSubscriber extends ISubscriber {
    boolean receivePostMonsterTurn(AbstractMonster m);
}
