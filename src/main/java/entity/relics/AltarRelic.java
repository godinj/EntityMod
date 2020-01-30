package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import entity.EntityMod;
import entity.util.TextureLoader;

public class AltarRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(AltarRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Altar.png"));

    private static final int STRENGTH_GAIN_AMT = 1;
    private boolean isStrengthGainedThisTurn;
    private int totalStrengthGain;

    public AltarRelic() {
        super(ID, IMG, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        isStrengthGainedThisTurn = false;
        totalStrengthGain = 0;
    }

    @Override
    public void atTurnStart() {
        isStrengthGainedThisTurn = false;
        totalStrengthGain = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, StrengthPower.POWER_ID, totalStrengthGain));
        isStrengthGainedThisTurn = false;
        totalStrengthGain = 0;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type.equals(CardType.SKILL)) {
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH_GAIN_AMT)));
            this.totalStrengthGain += STRENGTH_GAIN_AMT;
            this.isStrengthGainedThisTurn = true;
        }
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + STRENGTH_GAIN_AMT + this.DESCRIPTIONS[1];
    }

}
