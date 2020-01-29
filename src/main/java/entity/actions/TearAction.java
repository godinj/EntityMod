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

public class TearAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TearAction.class.getSimpleName());

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    public TearAction(AbstractCreature source, int amount) {
        setValues(AbstractDungeon.player, source, amount);
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {//When hand is empty, stop
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.discardPile.size() == 1) {
                this.p.discardPile.moveToExhaustPile(this.p.discardPile.getBottomCard());
                tickDuration();
                return;
            }
            if (this.amount== 1) {
                AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, true, TEXT[0]);
            } else {
                AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, TEXT[1] + this.amount + TEXT[2], false);
            }
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.current_x = Settings.WIDTH / 2.0f;
                c.current_y = Settings.HEIGHT / 2.0f;
                c.target_x = Settings.WIDTH / 2.0f;
                c.target_y = Settings.HEIGHT / 2.0f;
                this.p.discardPile.moveToExhaustPile(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }
}



