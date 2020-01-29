package entity.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import entity.EntityMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MergeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(EntityMod.class.getName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(MergeAction.class.getSimpleName());

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    public MergeAction(AbstractCreature source, int amount) {
        setValues(AbstractDungeon.player, source, amount);
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {//When hand is empty, stop
        if (this.duration == Settings.ACTION_DUR_FAST) {
            logger.info("Reached boi");
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() == 1) {
                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
                tickDuration();
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                c.current_x = Settings.WIDTH / 2.0f;
                c.current_y = Settings.HEIGHT / 2.0f;
                c.target_x = Settings.WIDTH / 2.0f;
                c.target_y = Settings.HEIGHT / 2.0f;
                this.p.hand.moveToExhaustPile(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }
        tickDuration();
    }
}



