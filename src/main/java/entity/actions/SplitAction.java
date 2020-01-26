package entity.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import entity.EntityMod;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SplitAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(SplitAction.class.getName());
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
        if (this.dupeAmount <= 0) {
            this.isDone = true;
            return;
        }
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
                        this.p.hand.moveToDiscardPile(c);
                        c.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
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
                AbstractCard c = this.p.hand.getTopCard();
                this.p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
                makeDuplicates(c, dupeAmount);
                this.p.hand.refreshHandLayout();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                logger.info("BRUH -- cards were not retrieved.");
                this.p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
                makeDuplicates(c, dupeAmount);
            }
            this.p.hand.refreshHandLayout();
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
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(toUpgrade, amount, true, true));
    }

    private boolean isValidToDuplicate(AbstractCard card) {
        return card.canUpgrade();
    }
}
