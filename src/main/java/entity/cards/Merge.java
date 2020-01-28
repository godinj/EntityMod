package entity.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.actions.MergeAction;
import entity.actions.SplitAction;
import entity.characters.Entity;
import entity.powers.EssencePower;

import java.util.Iterator;

import static entity.EntityMod.makeCardPath;

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

    private static final int MAGIC = 2;

    private static final int DRAW = 4;

    public Merge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.draw = this.baseDraw = DRAW;
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Exhaust 2 cards from hand.
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, draw));
        AbstractDungeon.actionManager.addToBottom(new MergeAction(p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
