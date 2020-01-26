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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FluxPower extends AbstractPower implements CloneablePowerInterface {
    public static final Logger logger = LogManager.getLogger(EntityMod.class.getName());
    private AbstractCreature source;

    public static final String POWER_ID = EntityMod.makeID(FluxPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("flux_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("flux_small.png"));

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

    public static void receivePreMonstersTurnHook() {
        logger.info("receivePreMonstersTurnHook");
        int totalFluxDamage = calculateTotalFlux();
        ArrayList<AbstractMonster> currentLivingMonsters = new ArrayList<>();
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo == null || mo.isDead || mo.isDying) {
                continue;
            }
            currentLivingMonsters.add(mo);
            if (mo.hasPower(FluxPower.POWER_ID)) {
                mo.getPower(FluxPower.POWER_ID).flash();
                logger.info("BRUH -- monster receiving damage -- " + totalFluxDamage);
                AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(mo, new DamageInfo(AbstractDungeon.player, totalFluxDamage, DamageType.THORNS),
                        AbstractGameAction.AttackEffect.POISON
                    ));
            }
        }
        // We're not determining if the monsters are still alive after taking Flux damage.
        // This means that if the monsters die from Flux, the player will still take damage from Flux.
        currentLivingMonsters.sort(CREATURE_SORT);
        AbstractPlayer p = AbstractDungeon.player;
        if (currentLivingMonsters.size() > 0 && p.hasPower(FluxPower.POWER_ID)) {
            p.getPower(FluxPower.POWER_ID).flash();
            logger.info("BRUH -- player receiving damage -- " + totalFluxDamage);
            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(p, new DamageInfo(p, totalFluxDamage, DamageType.THORNS),
                    AbstractGameAction.AttackEffect.POISON
                ));
        }
    }

    public static void receivePostMonstersTurnHook() {
        logger.info("receivePostMonstersTurnHook");
        ArrayList<AbstractMonster> currentLivingMonsters = new ArrayList<>();
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo == null || mo.isDead || mo.isDying) {
                continue;
            }
            currentLivingMonsters.add(mo);
            if (mo.hasPower(FluxPower.POWER_ID)) {
                logger.info("BRUH -- monster flux reduction -- " + FLUX_REDUCTION);
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(mo, mo, POWER_ID, FLUX_REDUCTION));
            }
        }
        currentLivingMonsters.sort(CREATURE_SORT);
        AbstractPlayer p = AbstractDungeon.player;
        if (currentLivingMonsters.size() > 0 && p.hasPower(FluxPower.POWER_ID)) {
            logger.info("BRUH -- monster flux reduction -- " + FLUX_REDUCTION);
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, POWER_ID, FLUX_REDUCTION));
        }
    }

    @Override
    public void updateDescription() {
        int totalFluxDamage = calculateTotalFlux();
        if (this.owner == null || this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0] + totalFluxDamage + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + totalFluxDamage + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FluxPower(this.owner, this.source, this.amount);
    }
}
