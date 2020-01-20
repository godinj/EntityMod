package entity.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;
import basemod.helpers.BaseModCardTags;


public class Defend_Entity extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Defend_Entity.class.getSimpleName());
    public static final String IMG = makeCardPath("Defend.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    public Defend_Entity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 5;
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBlock(3);
        }
    }
}

