package entity.cards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import entity.EntityMod;
import entity.characters.Entity;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import entity.cards.AbstractEntityCard;

import java.util.*;

import static entity.EntityMod.makeCardPath;

//New Card: uncoiling tendrils	rare	Attack	2	deal 10(12) damage. If this kills, Transform a common card in your deck at random. exhaust
public class UncoilingTendrils extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(UncoilingTendrils.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;

    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 2;

    //private Object ABSTRACTCARD;
    private Object AbstractCard = cardID;

//    {
      //  ABSTRACTCARD = cardID;
  //  }

    /*public static void transformCard(AbstractCard c) {
        transformCard(c, false);
    }
     */
    private AbstractCard theCard = null;

    public UncoilingTendrils() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
    }

    //reference code:
    //"Deal !D! damage. NL If Fatal, Upgrade a random card in your deck. NL Exhaust."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                //deals damage
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
                //if monster is dead
        if (((m.isDying || m.currentHealth <= 0) && !m.halfDead &&
                !m.hasPower("Minion"))){
        //upgrade a random card in your deck.
            ArrayList<AbstractCard> possibleCards = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                //upgrades the card, we want to TRANSFORM the card
                if (c.canPlay((com.megacrit.cardcrawl.cards.AbstractCard) AbstractCard))
            possibleCards.add(c);
            }
            //if possible cards are empty/no more cards to transform.
            if (!possibleCards.isEmpty()) {
                this.theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
            //    this.theCard.upgrade();
                //transform the card
                AbstractDungeon.transformCard((com.megacrit.cardcrawl.cards.AbstractCard) AbstractCard);
            }
        }
        //if monsters are running away
      //  if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
          //  AbstractDungeon.actionManager.clearPostCombatActions();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
