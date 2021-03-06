package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import entity.EntityMod;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;

public class BleedingEdge extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(BleedingEdge.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    private static final int DAMAGE = 3;
    // Represents number of attacks.
    private static final int MULTIPLIER = 5;
    private static final int UPGRADE_PLUS_MULTIPLIER = 1;
    // Represents Strength loss.
    private static final int SELF_MAGIC = 1;

    public BleedingEdge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.multiplier = this.baseMultiplier = MULTIPLIER;
        this.selfMagicNumber = this.baseSelfMagicNumber = SELF_MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < multiplier; i++) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, selfMagicNumber), selfMagicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMultiplier(UPGRADE_PLUS_MULTIPLIER);
        }
    }
}
