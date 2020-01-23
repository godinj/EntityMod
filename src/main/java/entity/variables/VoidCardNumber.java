package entity.variables;

import static entity.EntityMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import entity.cards.AbstractEntityCard;

public class VoidCardNumber extends DynamicVariable {
    @Override
    public String key() {
        return makeID("V");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractEntityCard) card).isVoidCardModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractEntityCard) card).voidCard;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractEntityCard) card).baseVoidCard;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractEntityCard) card).upgradedVoidCard;
    }
}
