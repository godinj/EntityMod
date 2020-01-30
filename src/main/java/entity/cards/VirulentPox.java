package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.VirulentPoxPower;
import entity.powers.VoidPulsePower;

// TODO: Rework
// Void Pulse -- rare -- power -- 1 --  whenever a status is drawn, Gain 1 energy and draw a card. (innate)
public class VirulentPox extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(VirulentPox.class.getSimpleName());
    public static final String IMG = makeCardPath("Otherworldly.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int MAGIC = 1;

    public VirulentPox() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new VirulentPoxPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
