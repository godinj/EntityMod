package entity.powers;

import static entity.EntityMod.makePowerPath;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.EntityMod;
import entity.util.TextureLoader;

public class VoidWeavePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(VoidWeavePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("community_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("community_small.png"));

    private int block_multiplier;

    public VoidWeavePower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int block_multiplier) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.block_multiplier = block_multiplier;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.description = DESCRIPTIONS[0];

        updateDescription();
    }

    public int calculateBlockGained() {
        return this.amount * block_multiplier;
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.cardID.equals(VoidCard.ID)) {
            flash();
            AbstractCreature o = this.owner;
            int blockGain = calculateBlockGained();
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(o, o, blockGain));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]
            + calculateBlockGained() + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VoidWeavePower(this.owner, this.source, this.amount, this.block_multiplier);
    }
}
