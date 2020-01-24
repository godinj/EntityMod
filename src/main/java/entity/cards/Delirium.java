package entity.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.EssencePower;
import entity.powers.FluxPower;

import static entity.EntityMod.makeCardPath;

//Delirium	Rare	Skill	2	Apply 7(9) flux to all enemies. Gain 2(3) essence. Exhaust
public class Delirium extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Delirium.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;

    private static final int ESSENCE = 2;
    private static final int UPGRADED_PLUS_ESSENCE = 1;

    private static final int FLUX = 7;
    private static final int UPGRADE_PLUS_FLUX = 2;


    public Delirium() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.essence = this.baseEssence = ESSENCE;
        this.flux = this.baseFlux = FLUX;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EssencePower(p, p, essence), essence));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo == null || mo.isDead || mo.isDying) {
                continue;
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new FluxPower(mo, p, flux), flux));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeFlux(UPGRADE_PLUS_FLUX);
            upgradeEssence(UPGRADED_PLUS_ESSENCE);
        }
    }
}
