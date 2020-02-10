package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import entity.EntityMod;
import entity.actions.ToTheAbyssAction;
import entity.characters.Entity;

// Aether Form -- Basic -- Skill -- 2 -- Gain 7(11) block. Gain an artifact.
public class ToTheAbyss extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(ToTheAbyss.class.getSimpleName());
    public static final String IMG = makeCardPath("ToTheAbyss.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public ToTheAbyss() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ToTheAbyssAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}
