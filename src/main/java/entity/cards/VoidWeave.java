package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.VoidWeavePlusPower;
import entity.powers.VoidWeavePower;

public class VoidWeave extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(VoidWeave.class.getSimpleName());
    public static final String IMG = makeCardPath("Otherworldly.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    // TODO: Change block to magic number

    public VoidWeave() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new VoidWeavePlusPower(p, p, 1)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new VoidWeavePower(p, p, 1)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }
}
