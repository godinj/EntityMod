package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.powers.VulnerablePower;
import entity.EntityMod;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;

// Void Blast -- Basic -- Attack -- 2 -- Apply 1 vulnerable to yourself, deal 2x6(8) damage single target. Apply 2 vulnerable to that target.
public class VoidBlast extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(VoidBlast.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 5;
    // Represents Vulnerable amount applied to enemy.
    private static final int MAGIC = 2;
    // Represents number of attacks.
    private static final int MULTIPLIER = 2;
    // Represents Vulnerable amount applied to self.
    private static final int SELF_MAGIC = 1;

    public VoidBlast() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.multiplier = this.baseMultiplier = MULTIPLIER;
        this.selfMagicNumber = this.baseSelfMagicNumber = SELF_MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, selfMagicNumber, false), selfMagicNumber));
        for (int i = 0; i < multiplier; i++) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
