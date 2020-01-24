package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.NewRipAndTearAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.EssencePower;

import static entity.EntityMod.makeCardPath;
//tentacle rage	uncommon	attack	X	X+1 5(6) damage attacks to a random enemy.
public class TentacleRage extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(TentacleRage.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = -1; // X cost

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 1;

    public TentacleRage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE; //amount of damage
        this.baseMagicNumber = COST + 1; //number of times it hits the enemy.
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use (AbstractPlayer p, AbstractMonster m){
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
               for (int i = 0; i < this.magicNumber; i++)
                   addToBot((AbstractGameAction)new NewRipAndTearAction(this));
/*
            AbstractDungeon.actionManager.addToBottom(
                  new AttackDamageRandomEnemyAction (m, DAMAGE, damageTypeForTurn);
                          (m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
            */
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
