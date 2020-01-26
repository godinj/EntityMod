package entity.variables;

import static entity.EntityMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import entity.cards.AbstractEntityCard;

public class MultiplierNumber extends DynamicVariable {
    @Override
    public String key() {
        return makeID("M");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractEntityCard) card).isMultiplierModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractEntityCard) card).multiplier;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractEntityCard) card).baseMultiplier;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractEntityCard) card).upgradedMultiplier;
    }
}
