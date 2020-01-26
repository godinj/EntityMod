package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.EssencePower;

import static entity.EntityMod.makeCardPath;

// Sap -- common -- attack -- 2 -- Deal 12(15) damage to all enemies. gain 2(4) essence. exhuast
public class Sap extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Sap.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;

    // Represents number of essence added.
    private static final int ESSENCE = 2;
    private static final int UPGRADED_PLUS_ESSENCE = 2;

    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DAMAGE = 3;

    //this is a constructor. This initializes variables. Creates an instance of the card using the AbyssalCall above^
    public Sap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.essence = this.baseEssence = ESSENCE;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(m, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EssencePower(p, p, essence), essence));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeEssence(UPGRADED_PLUS_ESSENCE);
        }
    }
}
