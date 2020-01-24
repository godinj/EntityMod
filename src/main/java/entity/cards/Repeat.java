/*package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.NightmareAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.FluxPower;

import javax.smartcardio.Card;

import java.util.ArrayList;

import static entity.EntityMod.makeCardPath;
//Repeat	common	skill	1(0)	discard a card and shuffle a copy into your deck. (upgrade the copy)
public class Repeat extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Repeat.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int UPGRADE_COST = -1;

    private static final int MAGIC_NUMBER = 1;

    public Repeat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.discard = 1;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cannotDuplicate = new ArrayList<>();

        for (AbstractCard c : p.hand.group) {
            if (!isDualWieldable(c))
                this.cannotDuplicate.add(c);
        }
        if (this.cannotDuplicate.size() == this.p.hand.group.size()) {
            this.isDone = true;
            return;
        }
        if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1)
            for (AbstractCard c : this.p.hand.group) {
                if (isDualWieldable(c)) {
                    for (int i = 0; i < this.dupeAmount; i++)
                        addToTop((AbstractGameAction)new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                    this.isDone = true;
                    return;
                }
            }
        this.p.hand.group.removeAll(this.cannotDuplicate);
        if (this.p.hand.group.size() > 1) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
            tickDuration();
            return;
        }
        if (this.p.hand.group.size() == 1) {
            for (int i = 0; i < this.dupeAmount; i++)
                addToTop((AbstractGameAction)new MakeTempCardInHandAction(this.p.hand.getTopCard().makeStatEquivalentCopy()));
            returnCards();
            this.isDone = true;
        }
    }
    if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
        for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
            addToTop((AbstractGameAction)new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
            for (int i = 0; i < this.dupeAmount; i++)
                addToTop((AbstractGameAction)new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
        }
        returnCards();
        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        this.isDone = true;
    }

    //DISCARD A CARD
        addToBot((AbstractGameAction)new DiscardAction((AbstractCreature)p, (AbstractCreature)p, 1, false));

        //SHUFFLE A COPY INTO DECK. IF UPGRADED SHUFFLE AN UPGRADED VERSION.

        //this adds an insight card to the deck. I need to change insight to the card i'm discarding instead.
        Insight insight = new Insight();
        addToBot((AbstractGameAction)new MakeTempCardInDrawPileAction((AbstractCard)insight, 1, true, true, false));

       // AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard.CardTarget)));

    }


    private void returnCards() {
        for (AbstractCard c : this.cannotDuplicate)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }

    private boolean isDualWieldable(AbstractCard card) {
        return (card.type.equals(AbstractCard.CardType.ATTACK) || card.type.equals(AbstractCard.CardType.POWER));
    }



    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        //    upgradeMagicNumber(1);
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}
*/