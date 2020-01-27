package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;

//Lash	common	attack	0	deal 7(11) damage. add 1 void card.
public class Lash extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Lash.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 0;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 4;

    // Represents number of Void cards added.
    private static final int MAGIC = 1;

    //this is a constructor. This initializes variables. Creates an instance of the card using the super constructor.
    public Lash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC;
        updateDescription();
    }

    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTION);
        if (magicNumber == 1)  {
            sb.append(EXTENDED_DESCRIPTION[0]);
        } else {
            sb.append(EXTENDED_DESCRIPTION[1]);
            sb.append(magicNumber);
            sb.append(EXTENDED_DESCRIPTION[2]);
        }
        this.rawDescription = sb.toString();
        initializeDescription();
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
