package entity.powers;

import static entity.EntityMod.makePowerPath;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.EntityMod;
import entity.util.TextureLoader;
import java.util.ArrayList;
import java.util.Comparator;

public class FluxPower extends AbstractPower implements CloneablePowerInterface {
    private AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(FluxPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("piety_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("piety_small.png"));

    private static final int FLUX_REDUCTION = 1;
    public static final Comparator<AbstractCreature> CREATURE_SORT = new Comparator<AbstractCreature>() {
        @Override
        public int compare(AbstractCreature o1, AbstractCreature o2) {
            return o1.id.compareTo(o2.id);
        }
    };

    public FluxPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.description = DESCRIPTIONS[0];

        updateDescription();
    }

    public static int calculateTotalFlux() {
        int totalFluxDamage = 0;
        if (AbstractDungeon.player.hasPower(FluxPower.POWER_ID)) {
            totalFluxDamage += AbstractDungeon.player.getPower(FluxPower.POWER_ID).amount;
        }
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo == null || mo.isDead || mo.isDying) {
                continue;
            }
            if (mo.hasPower(FluxPower.POWER_ID)) {
                totalFluxDamage += mo.getPower(FluxPower.POWER_ID).amount;
            }
        }
        return totalFluxDamage;
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if(this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            return;
        }

        this.flash();

        int totalFluxDamage = calculateTotalFlux();
        ArrayList<AbstractMonster> currentLivingMonsters = new ArrayList<AbstractMonster>();
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo == null || mo.isDead || mo.isDying) {
                continue;
            }
            currentLivingMonsters.add(mo);
        }
        currentLivingMonsters.sort(CREATURE_SORT);
        if (
            currentLivingMonsters.size() > 0 &&
            (this.owner == currentLivingMonsters.get(0)) &&
            AbstractDungeon.player.hasPower(FluxPower.POWER_ID)
        ) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, totalFluxDamage, DamageType.THORNS),
                    AbstractGameAction.AttackEffect.POISON
                ));
        }

        if (this.owner != AbstractDungeon.player) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(this.owner, new DamageInfo(AbstractDungeon.player, totalFluxDamage, DamageType.THORNS),
                    AbstractGameAction.AttackEffect.POISON
                ));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) {
            ArrayList<AbstractMonster> currentLivingMonsters = new ArrayList<AbstractMonster>();
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo == null || mo.isDead || mo.isDying) {
                    continue;
                }
                currentLivingMonsters.add(mo);
            }
            currentLivingMonsters.sort(CREATURE_SORT);
            if (currentLivingMonsters.size() > 0 && (this.owner == currentLivingMonsters.get(0))) {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, this.owner, POWER_ID, FLUX_REDUCTION));
            }
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, FLUX_REDUCTION));
        }
    }

    @Override
    public void updateDescription() {
        int totalFluxDamage = calculateTotalFlux();
        description = DESCRIPTIONS[0] + totalFluxDamage + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FluxPower(this.owner, this.source, this.amount);
    }
}
