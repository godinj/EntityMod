package entity.variables;

import static entity.EntityMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import entity.cards.AbstractEntityCard;

public class SelfMagicNumberNumber extends DynamicVariable {
    @Override
    public String key() {
        return makeID("S");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractEntityCard) card).isSelfMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractEntityCard) card).selfMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractEntityCard) card).baseSelfMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractEntityCard) card).upgradedSelfMagicNumber;
    }
}
