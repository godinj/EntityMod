package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Coup extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Coup.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Coup() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 0;
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

    public void calculateUniqueCostTotal() {
        int total = 0;
        Map<Integer, Boolean> existingCardsByCost = new HashMap<Integer, Boolean>();
        Iterator<AbstractCard> iterator = AbstractDungeon.player.hand.group.iterator();
        AbstractCard c;
        while(iterator.hasNext()) {
            c = iterator.next();
            if (c.equals(this)) {
                continue;
            }
            existingCardsByCost.putIfAbsent(c.costForTurn, true);
        }
        for (int tmpCost: existingCardsByCost.keySet()) {
            total++;
        }

        this.magicNumber = total;
        generateAndInitializeExtendedDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        calculateUniqueCostTotal();
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, magicNumber));
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
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
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
