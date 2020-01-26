package entity.powers;

import static entity.EntityMod.makePowerPath;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.EntityMod;
import entity.util.TextureLoader;

public class VoidPulsePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(VoidPulsePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("refuge_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("refuge_small.png"));

    public VoidPulsePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
        if (card.type.equals(CardType.STATUS)) {
            flash();
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(amount));
            for (int i = 0; i < amount; i++) {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(1));
            }
        }
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        for (int i = 0; i < this.amount; i++) {
            sb.append("[E] ");
        }
        sb.append(LocalizedStrings.PERIOD);
        sb.append(powerStrings.DESCRIPTIONS[1]);
        if (amount == 1) {
            sb.append(powerStrings.DESCRIPTIONS[4]);
        } else {
            sb.append(powerStrings.DESCRIPTIONS[2]);
            sb.append(amount);
            sb.append(powerStrings.DESCRIPTIONS[3]);
        }
        this.description = sb.toString();
    }

    @Override
    public AbstractPower makeCopy() {
        return new VoidPulsePower(this.owner, this.source, this.amount);
    }
}
