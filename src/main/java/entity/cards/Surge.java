package entity.cards;

import static entity.EntityMod.makeCardPath;
import static java.lang.Integer.min;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;

// Surge -- rare -- skill -- 2(1) -- For each card played this turn, play and exhaust the top card of your library. Exhaust
public class Surge extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Surge.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public Surge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int cardsPlayed = 0;
        for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (!c.equals(this)) {
                cardsPlayed++;
            }
        }
        for (int i = 0; i < min(cardsPlayed, p.drawPile.size()); i++) {
            AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(
                AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), true));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
