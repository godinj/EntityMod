package entity.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import entity.powers.EssencePower;

public class ToTheAbyssAction extends AbstractGameAction {
    private AbstractPlayer p;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ToTheAbyssAction.class.getSimpleName());

    public static final String[] TEXT = uiStrings.TEXT;

    private boolean chooseAny;

    public ToTheAbyssAction() {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            int cardsDiscarded = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.p, cardsDiscarded));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EssencePower(p, p, cardsDiscarded)));
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
