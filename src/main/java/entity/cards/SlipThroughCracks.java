package entity.cards;

import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.EssencePower;

import java.util.Iterator;

import static entity.EntityMod.makeCardPath;

//Slip through cracks		skill	2	Exhaust all cards in hand, gain 1(2) intangible. exhaust.
public class SlipThroughCracks extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(SlipThroughCracks.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;

    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public SlipThroughCracks() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.exhaust = true;
    }

    public void generateAndInitializeExtendedDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTION);
        if (magicNumber == 1)  {
            sb.append(EXTENDED_DESCRIPTION[0]);
        } else {
            sb.append(EXTENDED_DESCRIPTION[1]);
            sb.append(magicNumber);
            sb.append(EXTENDED_DESCRIPTION[2]);
        }
        this.rawDescription = sb.toString();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Exhaust all cards.
        Iterator<AbstractCard> iterator = p.hand.group.iterator();
        int cardsExhausted = 0;
        AbstractCard card;
        while(iterator.hasNext()) {
            card = iterator.next();
            if (card.equals(this)) {
                continue;
            }
            AbstractDungeon.actionManager.addToBottom(
                new ExhaustSpecificCardAction(card, p.hand));
            cardsExhausted++;
        }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction
                    (p,p, new IntangiblePower(p, magicNumber), magicNumber));

    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
        }
    }
}
