package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;

//cosmic ray	uncommon	attack	2	deal 18(25) damage. gain 1 void.
public class CosmicRay extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(CosmicRay.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;

    private static final int DAMAGE = 18;
    private static final int UPGRADE_PLUS_DMG = 7;

    // Represents number of Void cards added.
    private static final int MAGIC = 1;

    //this is a constructor. This initializes variables. Creates an instance of the card using the super constructor.
    public CosmicRay() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInDiscardAction(new VoidCard(), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
