package entity.powers;

import static entity.EntityMod.makePowerPath;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.EntityMod;
import entity.interfaces.InvisiblePower;
import entity.util.TextureLoader;

public class FluxBarPower extends AbstractPower implements CloneablePowerInterface, InvisiblePower, HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(FluxBarPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("enlightened_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("enlightened_small.png"));

    public FluxBarPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.type = PowerType.BUFF;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.description = DESCRIPTIONS[0];
        updateDescription();
    }

    @Override
    public int getHealthBarAmount()
    {
        int amount = 0;
        if (this.owner.hasPower(FluxPower.POWER_ID)) {
            amount = FluxPower.calculateTotalFlux();
        }
        return amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new FluxBarPower(this.owner, this.source, this.amount);
    }

    public Color getColor() {
        return Color.TEAL;
    }
}
