package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.NewRipAndTearAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import entity.EntityMod;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;

//grasping arms	common	attack	1	deal 2 Damage 5(7)x randomly.
public class GraspingArms extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(GraspingArms.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    private static final int HITS = 5;
    private static final int UPGRADE_PLUS_HITS = 2;

    public GraspingArms() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 2; //amount of damage
        this.baseMagicNumber = HITS; //number of times it hits the enemy.
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use (AbstractPlayer p, AbstractMonster m){
               for (int i = 0; i < this.magicNumber; i++)
                   addToBot((AbstractGameAction)new NewRipAndTearAction(this));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_HITS);
        }
    }
}
