package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import java.util.Iterator;

// Exhaust your hand. For each card exhausted, draw a card, gain [E], gain 1 block and add a void card to your discard pile. Exhaust.
public class RipplingGrace extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(RipplingGrace.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 0;

    public RipplingGrace() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 0;
        this.exhaust = true;
    }

    public void generateAndInitializeExtendedDescription() {
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
        // Exhaust all cards, count the cards exhausted.
        Iterator<AbstractCard> iterator = p.hand.group.iterator();
        int cardsExhausted = 0;
        AbstractCard card;
        while(iterator.hasNext()) {
            card = iterator.next();
            if (card.equals(this)) {
                continue;
            }
            AbstractDungeon.actionManager.addToBottom(
                new ExhaustSpecificCardAction(card, p.hand));
            cardsExhausted++;
        }
        this.magicNumber = cardsExhausted;
        // Draw that many cards.
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        // Gain that much energy.
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
        // Gain that much block.
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, magicNumber));
        // Add that many Void cards to your discard.
        for (int voidsToGenerate = magicNumber; voidsToGenerate > 0; voidsToGenerate--) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), 1));
        }
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}
