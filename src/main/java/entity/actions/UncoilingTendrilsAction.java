package entity.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class UncoilingTendrilsAction extends AbstractGameAction {
    private DamageInfo info;

    private AbstractCard theCard = null;

    public UncoilingTendrilsAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED &&
            this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);
            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead &&
                !this.target.hasPower("Minion")) {
                ArrayList<AbstractCard> possibleCards = new ArrayList<>();
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.rarity.equals(CardRarity.BASIC) || c.rarity.equals(CardRarity.COMMON))
                        possibleCards.add(c);
                }
                if (!possibleCards.isEmpty()) {
                    float displayCount = 0.0F;
                    this.theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
                    this.theCard.untip();
                    this.theCard.unhover();
                    AbstractDungeon.transformCard(this.theCard, false, AbstractDungeon.miscRng);
                    if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.TRANSFORM && AbstractDungeon.transformedCard != null) {
                        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(
                            AbstractDungeon.getTransformedCard(), Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F, false));
                        displayCount += Settings.WIDTH / 6.0F;
                    }
                    (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
                }
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
    }
}
