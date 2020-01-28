package entity.powers;

import static entity.EntityMod.makePowerPath;
import static java.lang.Math.floor;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.EntityMod;
import entity.relics.CrystalChamberRelic;
import entity.relics.FaerieInABottleRelic;
import entity.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EssencePower extends AbstractPower implements CloneablePowerInterface {
    public static final Logger logger = LogManager.getLogger(EntityMod.class.getName());
    public AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(EssencePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("essence_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("essence_small.png"));

    private static final int ESSENCE_REDUCTION = 1;
    private static final float ESSENCE_HEALTH_NUMERATOR = 1;
    private static final float ESSENCE_HEALTH_DENOMINATOR = 2;

    public EssencePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.description = DESCRIPTIONS[0];

        updateDescription();
        logger.info("constructor -- this.amount: " + this.amount);
        if (!this.owner.hasPower(EssencePower.POWER_ID) && this.amount > 0) {
            checkFaerieInABottleRelic(this.amount);
        }
    }

    private int calculateHealthGained() {
        return (int)floor(amount * (ESSENCE_HEALTH_NUMERATOR / ESSENCE_HEALTH_DENOMINATOR));
    }

    private void checkFaerieInABottleRelic(int stackAmount) {
        logger.info("checkFaerieInABottleRelic");
        AbstractPlayer p = AbstractDungeon.player;
        if (stackAmount > 0 && this.owner.equals(p) && p.hasRelic(FaerieInABottleRelic.ID)) {
            p.getRelic(FaerieInABottleRelic.ID).flash();
            multiplyFluxDamageOnEnemies(stackAmount);
        }
    }

    // Iterate through all enemies and multiply their Flux.
    private void multiplyFluxDamageOnEnemies(int multiplier) {
        if (multiplier <= 1) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo == null || mo.isDead || mo.isDying) {
                continue;
            }
            if (mo.hasPower(FluxPower.POWER_ID)) {
                int multipliedFlux = mo.getPower(FluxPower.POWER_ID).amount * (multiplier - 1);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new FluxPower(mo, p, multipliedFlux)));
            }
        }
    }

    @Override
    public void stackPower(int stackAmount)
    {
        if (!this.owner.hasPower(EssencePower.POWER_ID)) {
            logger.info("stackPower -- no power check: " + stackAmount);
            return;
        }
        logger.info("stackPower -- stackAmount: " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        checkFaerieInABottleRelic(stackAmount);
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount)
    {
        logger.info("reducePower");
        super.reducePower(reduceAmount);
    }

    @Override
    public void atStartOfTurn() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            return;
        }

        this.flash();

        int healthGained = calculateHealthGained();
        AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, healthGained));
        if (AbstractDungeon.player.hasRelic(CrystalChamberRelic.ID)) {
            AbstractDungeon.player.getRelic(CrystalChamberRelic.ID).flash();
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, ESSENCE_REDUCTION));
        }
    }


    @Override
    public void updateDescription() {
        int healthGained = calculateHealthGained();
        if (this.owner == null || this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0] + healthGained + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + healthGained + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new EssencePower(this.owner, this.source, this.amount);
    }
}
