package entity.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.FluxPower;

import static entity.EntityMod.makeCardPath;

//Avoid	common	skill	0	6(8) Block. Add 1 void card.
public class Avoid extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Avoid.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 0;

    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 8;

    private static final int SELF_MAGIC_NUMBER = 1;

    public Avoid() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = BLOCK;
        this.selfMagicNumber = this.baseSelfMagicNumber = SELF_MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //apply block to yourself
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        //make 1 void card
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInDiscardAction(new VoidCard(), selfMagicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}
