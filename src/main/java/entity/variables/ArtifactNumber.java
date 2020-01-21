package entity.variables;

import static entity.EntityMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import entity.cards.AbstractEntityCard;

public class ArtifactNumber extends DynamicVariable {
    @Override
    public String key() {
        return makeID("A");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractEntityCard) card).isArtifactModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractEntityCard) card).artifact;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractEntityCard) card).baseArtifact;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractEntityCard) card).upgradedArtifact;
    }
}
