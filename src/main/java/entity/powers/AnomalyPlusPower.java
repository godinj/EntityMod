package entity.powers;

import static entity.EntityMod.makePowerPath;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import entity.EntityMod;
import entity.util.TextureLoader;

// If Ka was the last card played, Deal 3 flux to a random enemy and gain 4 additional block.
public class AnomalyPlusPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(AnomalyPlusPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("anomaly_plus_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("anomaly_plus_small.png"));

    private int strengthGainedThisTurn;
    private int dexterityGainedThisTurn;

    public static final int STRENGTH_MULTIPLIER = 1;
    public static final int DEXTERITY_MULTIPLIER = 1;

    public AnomalyPlusPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.strengthGainedThisTurn = amount;
        this.dexterityGainedThisTurn = amount;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.description = DESCRIPTIONS[0];

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        EntityMod.logger.info("BRUH -- stacking: " + stackAmount);
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    public void calculateAdditiveScalingDeltas() {
        this.strengthGainedThisTurn += STRENGTH_MULTIPLIER;
        this.dexterityGainedThisTurn += DEXTERITY_MULTIPLIER;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        AbstractCreature o = this.owner;
        EntityMod.logger.info("BRUH -- Anomaly+");
        calculateAdditiveScalingDeltas();
        int strengthGain = this.amount * this.strengthGainedThisTurn;
        int dexterityGain = this.amount * this.dexterityGainedThisTurn;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(o, o, new StrengthPower(o, strengthGain), strengthGain));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(o, o, new DexterityPower(o, dexterityGain), dexterityGain));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(o, o, new LoseStrengthPower(o, strengthGain), strengthGain));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(o, o, new LoseDexterityPower(o, dexterityGain), dexterityGain));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        this.strengthGainedThisTurn = 0;
        this.dexterityGainedThisTurn = 0;
    }

    @Override
    public AbstractPower makeCopy() {
        EntityMod.logger.info("BRUH -- making a copy");
        return new AnomalyPlusPower(this.owner, this.source, this.amount);
    }
}
