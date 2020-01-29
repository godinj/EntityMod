package entity.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import java.util.Iterator;

import static entity.EntityMod.makeCardPath;


public class Parry extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Parry.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 0;

    private static final int BLOCK = 11;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public Parry() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    //check to see if all cards in your hand are attack cards. or if the hand is empty.
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        Iterator<AbstractCard> iterator = AbstractDungeon.player.hand.group.iterator();
        AbstractCard c;
        while(iterator.hasNext()) {
            c = iterator.next();
            if (c.equals(this)) {
                continue;
            }
            if (c.type != AbstractCard.CardType.ATTACK) {
                canUse = false;
            }
        }
        return canUse;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}

