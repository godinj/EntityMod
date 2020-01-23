package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.FluxPower;
import entity.powers.KaPower;
import entity.powers.LuPower;

public class Ka extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Ka.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int FLUX = 3;
    private static final int KA_STACKS = 1;

    public Ka() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.flux = this.baseFlux = FLUX;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
            new FluxPower(m, p, flux), flux));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new KaPower(p, p, KA_STACKS), KA_STACKS));
        if (p.hasPower(LuPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, LuPower.POWER_ID));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}
