package entity.powers;

import static entity.EntityMod.makePowerPath;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.EntityMod;
import entity.util.TextureLoader;

// If Tu was the last card played, draw 2 and gain 4 block. Trigger Tu
public class TuPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(TuPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("tu_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("tu_small.png"));

    public static final int DRAW_MULTIPLIER = 2;
    public static final int BLOCK_MULTIPLIER = 4;

    public TuPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.description = DESCRIPTIONS[0];

        updateDescription();
    }

    public int calculateCardsDrawn(boolean stacksMultiply) {
        return stacksMultiply ? this.amount * DRAW_MULTIPLIER : DRAW_MULTIPLIER;
    }

    public int calculateBlockGained(boolean stacksMultiply) {
        return stacksMultiply ? this.amount * BLOCK_MULTIPLIER : BLOCK_MULTIPLIER;
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
    public void onRemove() {
        super.onRemove();
        int cardsDrawn = calculateCardsDrawn(false);
        int blockGained = calculateBlockGained(false);
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, cardsDrawn));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, blockGained));
    }

    @Override
    public void updateDescription() {
        int cardsDrawn = calculateCardsDrawn(false);
        int blockGained = calculateBlockGained(false);
        this.description = DESCRIPTIONS[0]
            + cardsDrawn
            + DESCRIPTIONS[1]
            + blockGained
            + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TuPower(this.owner, this.source, this.amount);
    }
}
