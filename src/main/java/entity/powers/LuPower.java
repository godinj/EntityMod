package entity.powers;

import static entity.EntityMod.makePowerPath;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.EntityMod;
import entity.util.TextureLoader;

// If Tu was the last card played, draw 2 and gain 4 block. Trigger Tu
public class LuPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(LuPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("refuge_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("refuge_small.png"));

    public static final int FLUX_MULTIPLIER = 3;
    public static final int DRAW_MULTIPLIER = 2;

    public LuPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

    public int calculateFluxApplied(boolean stacksMultiply) {
        return stacksMultiply ? this.amount * FLUX_MULTIPLIER : FLUX_MULTIPLIER;
    }

    public int calculateCardsDrawn(boolean stacksMultiply) {
        return stacksMultiply ? this.amount * DRAW_MULTIPLIER : DRAW_MULTIPLIER;
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
        int fluxApplied = calculateFluxApplied(false);
        int cardsDrawn = calculateCardsDrawn(false);
        AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, this.owner,
            new FluxPower(mo, this.owner, fluxApplied), fluxApplied));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, cardsDrawn));
    }

    @Override
    public void updateDescription() {
        int fluxApplied = calculateFluxApplied(false);
        int cardsDrawn = calculateCardsDrawn(false);
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        sb.append(fluxApplied);
        sb.append(powerStrings.DESCRIPTIONS[1]);
        if (amount == 1) {
            sb.append(powerStrings.DESCRIPTIONS[4]);
        } else {
            sb.append(powerStrings.DESCRIPTIONS[2]);
            sb.append(cardsDrawn);
            sb.append(powerStrings.DESCRIPTIONS[3]);
        }
        this.description = sb.toString();
    }


    @Override
    public AbstractPower makeCopy() {
        return new LuPower(this.owner, this.source, this.amount);
    }
}
