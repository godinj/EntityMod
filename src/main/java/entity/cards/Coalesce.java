package entity.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.EssencePower;
import entity.powers.FluxPower;

import static entity.EntityMod.makeCardPath;

//Coalesce 	Common	Skill	1	Gain 2 Essence. Draw 1(2) cards.
public class Coalesce extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Coalesce.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    private static final int ESSENCE = 2;

    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public Coalesce() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.essence = this.baseEssence = ESSENCE;
        this.magicNumber = this.baseMagicNumber = MAGIC;
        generateAndInitializeExtendedDescription();
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EssencePower(p, p, essence), essence));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            generateAndInitializeExtendedDescription();
        }
    }
}
