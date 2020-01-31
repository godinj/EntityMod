package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;

public class Insanity extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Insanity.class.getSimpleName());
    public static final String IMG = makeCardPath("Insanity.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 0;

    // Represents number of cards drawn.
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    // Represents amount of energy gained.
    private static final int MULTIPLIER = 2;
    private static final int UPGRADE_PLUS_MULTIPLIER = 1;

    // Represents number of curses added to deck.
    private static final int SELF_MAGIC = 1;

    public Insanity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.multiplier = this.baseMultiplier = MULTIPLIER;
        this.selfMagicNumber = this.baseSelfMagicNumber = SELF_MAGIC;
        generateAndInitializeExtendedDescription();
    }

    public void generateAndInitializeExtendedDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTION);
        if (magicNumber == 1) {
            sb.append(EXTENDED_DESCRIPTION[0]);
        } else {
            sb.append(magicNumber);
            sb.append(EXTENDED_DESCRIPTION[1]);
        }
        for (int i = 0; i < multiplier; i++) {
            sb.append("[E] ");
        }
        sb.append(EXTENDED_DESCRIPTION[2]);
        this.rawDescription = sb.toString();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // draw cards
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        // gain energy
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.multiplier));
        // add a curse to the deck.
        AbstractCard c = AbstractDungeon.returnRandomCurse();
        c.current_x = Settings.WIDTH / 2.0f;
        c.current_y = Settings.HEIGHT / 2.0f;
        c.target_x = Settings.WIDTH / 2.0f;
        c.target_y = Settings.HEIGHT / 2.0f;
        AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(c));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.upgradeMultiplier(UPGRADE_PLUS_MULTIPLIER);
            generateAndInitializeExtendedDescription();
        }
    }
}
