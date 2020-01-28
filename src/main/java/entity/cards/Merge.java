package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.actions.MergeAction;
import entity.characters.Entity;

//Merge	uncommon	skill	1(0)	Draw 4 cards. Exhaust 2 cards from your hand.
public class Merge extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Merge.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int MAGIC = 4;
    private static final int SELF_MAGIC = 2;

    public Merge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.selfMagicNumber = this.baseSelfMagicNumber = SELF_MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Exhaust 2 cards from hand.
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MergeAction(p, this.selfMagicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
