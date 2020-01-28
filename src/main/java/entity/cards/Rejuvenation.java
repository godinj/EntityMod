package entity.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.EssencePower;

import static entity.EntityMod.makeCardPath;

//Rush of Rejuvenation	Uncommon	Skill	2	Gain 15(20) Block and 3(4) essence. Exhaust.
public class Rejuvenation extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Rejuvenation.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;

    private static final int BLOCK = 15;
    private static final int UPGRADE_PLUS_BLOCK = 5;

    private static final int ESSENCE = 3;
    private static final int UPGRADED_PLUS_ESSENCE = 1;

    public Rejuvenation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = BLOCK;
        this.essence = this.baseEssence = ESSENCE;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EssencePower(p, p, essence), essence));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeEssence(UPGRADED_PLUS_ESSENCE);
        }
    }
}
