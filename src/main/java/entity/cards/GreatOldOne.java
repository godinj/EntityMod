package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;

//Frenetic Find	rare	skill	1(0)	Gain 2(3) artifact, gain a slimed, void, dazed, burn, and wound card into your discard
public class GreatOldOne extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(GreatOldOne.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;

    public boolean playedFromExhaust = false;

    public GreatOldOne() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.playedFromExhaust = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Iterate through cards as many times as we see GreatOldOne in the Exhaust pile + 1
        // Play each card if it isn't GreatOldOne by making a stat equivalent copy. These copies disappear on use.
        // Use booleans to keep track of which GreatOldOnes we've already seen.
        int greatOldOnesToPlay = 1;
        while (greatOldOnesToPlay >= 1) {
            // Exhaust all cards.
            for (AbstractCard card : p.exhaustPile.group) {
                if (card.cardID.equals(GreatOldOne.ID)) {
                    if (!((GreatOldOne) card).playedFromExhaust) {
                        greatOldOnesToPlay++;
                        ((GreatOldOne) card).playedFromExhaust = true;
                    }
                    continue;
                }
                AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.monsterRng);
                AbstractCard tmp = card.makeStatEquivalentCopy();
                tmp.use(p, mo);
            }
            greatOldOnesToPlay--;
        }

        // Since we're done playing cards from the exhaust pile, we should reset the state of each GreatOldOne.
        for (AbstractCard card : p.exhaustPile.group) {
            if (card.cardID.equals(GreatOldOne.ID)) {
                ((GreatOldOne) card).playedFromExhaust = false;
            }
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
