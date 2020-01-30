package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import entity.EntityMod;
import entity.util.TextureLoader;

public class ChaliceRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(ChaliceRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Chalice.png"));

    private static final int ARTIFACT_GAIN_AMT = 1;
    private static final int NUM_CARDS = 3;

    public ChaliceRelic() {
        super(ID, IMG, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type.equals(CardType.ATTACK)) {
            this.counter++;
            if (this.counter % 3 == 0) {
                this.counter = 0;
                flash();
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, ARTIFACT_GAIN_AMT)));
            }
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + NUM_CARDS
            + this.DESCRIPTIONS[1] + ARTIFACT_GAIN_AMT
            + this.DESCRIPTIONS[2];
    }

}
