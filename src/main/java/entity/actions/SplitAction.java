package entity.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.ArrayList;

// TODO: Have one card go to draw pile and one go to discard.
public class SplitAction extends AbstractGameAction {
  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SplitAction.class.getSimpleName());

  public static final String[] TEXT = uiStrings.TEXT;

  private AbstractPlayer p;

  private int dupeAmount;
  private boolean upgradeDupes;

  private ArrayList<AbstractCard> cannotDuplicate = new ArrayList<>();

  public SplitAction(AbstractCreature source, int amount, boolean upgradeDupes) {
      setValues(AbstractDungeon.player, source, amount);
      this.actionType = AbstractGameAction.ActionType.DRAW;
      this.duration = 0.25F;
      this.p = AbstractDungeon.player;
      this.dupeAmount = amount;
      this.upgradeDupes = upgradeDupes;
  }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (!isValidToDuplicate(c))
                    this.cannotDuplicate.add(c);
            }
            if (this.cannotDuplicate.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (isValidToDuplicate(c)) {
                        makeDuplicates(c, dupeAmount);
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                makeDuplicates(this.p.hand.getTopCard(), dupeAmount);
                returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                addToTop(new MakeTempCardInDiscardAction(c.makeStatEquivalentCopy(), 1));
                makeDuplicates(c, dupeAmount);
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void makeDuplicates(AbstractCard card, int amount) {
        AbstractCard toUpgrade = card.makeStatEquivalentCopy();
        if (this.upgradeDupes && toUpgrade.canUpgrade()) {
            toUpgrade.upgrade();
        }
        addToTop(new MakeTempCardInDiscardAction(toUpgrade, amount));
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotDuplicate)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }

    private boolean isValidToDuplicate(AbstractCard card) {
        return card.canUpgrade();
    }
}
