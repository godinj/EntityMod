package entity.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import entity.cards.AbstractEntityCard;

import static entity.EntityMod.makeID;

public class FluxNumber extends DynamicVariable {
    @Override
    public String key() {
        return makeID("F");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractEntityCard) card).isFluxModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractEntityCard) card).flux;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractEntityCard) card).baseFlux;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractEntityCard) card).upgradedFlux;
    }
}
