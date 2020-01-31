package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.actions.ExhaustCardsInHandAction;
import entity.characters.Entity;

public class TaintedEyes extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(TaintedEyes.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    // Represents number of cards drawn.
    private static final int MAGIC = 5;

    // Represents number of cards exhausted.
    private static final int SELF_MAGIC = 1;

    public TaintedEyes() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.selfMagicNumber = this.baseSelfMagicNumber = SELF_MAGIC;
        generateAndInitializeExtendedDescription();
    }

    public void generateAndInitializeExtendedDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTION);
        if (magicNumber == 1) {
            sb.append(EXTENDED_DESCRIPTION[0]);
        } else {
            sb.append(magicNumber);
            sb.append(EXTENDED_DESCRIPTION[1]);
        }
        if (!this.upgraded) {
            sb.append(EXTENDED_DESCRIPTION[2]);
        } else {
            sb.append(EXTENDED_DESCRIPTION[3]);
        }
        this.rawDescription = sb.toString();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // draw cards
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        // add a curse to the deck.
        if (!this.upgraded) {
            AbstractCard c = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ExhaustCardsInHandAction(p, selfMagicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            generateAndInitializeExtendedDescription();
        }
    }
}
