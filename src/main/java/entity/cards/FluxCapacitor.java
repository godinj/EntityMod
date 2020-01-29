package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.FluxPower;

public class FluxCapacitor extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(FluxCapacitor.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public FluxCapacitor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 0;
    }

    public void generateAndInitializeExtendedDescription() {
        this.block = FluxPower.calculateTotalFlux();
        this.rawDescription = DESCRIPTION +
            EXTENDED_DESCRIPTION[1] +
            block +
            EXTENDED_DESCRIPTION[2];
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.block = FluxPower.calculateTotalFlux();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        this.rawDescription = DESCRIPTION;
        initializeDescription();
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
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}
