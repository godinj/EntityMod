package entity.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.actions.SplitAction;
import entity.characters.Entity;
import static entity.EntityMod.makeCardPath;

// Split -- common skill 1(0) discard a card and shuffle a copy into your deck.
public class Split extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Split.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    // Represents number of duplicate cards added. Cannot be greater than 5, otherwise draw will not work.
    private static final int MAGIC_NUMBER = 1;

    public Split() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.discard = 1;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SplitAction(p, this.magicNumber, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}
