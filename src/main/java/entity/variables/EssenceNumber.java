package entity.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import entity.cards.AbstractEntityCard;

import static entity.EntityMod.makeID;

public class EssenceNumber extends DynamicVariable {
    @Override
    public String key() {
        return makeID("E");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractEntityCard) card).isEssenceModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractEntityCard) card).essence;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractEntityCard) card).baseEssence;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractEntityCard) card).upgradedEssence;
    }
}
