package entity.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import entity.EntityMod;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;
//tentacle rage -- uncommon -- attack -- X -- X damage 7(10) times randomly.
public class TentacleRage extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(TentacleRage.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = -1; // X cost

    // Represents the number of attacks.
    private static final int MAGIC = 9;
    private static final int UPGRADE_PLUS_MAGIC = 4;

    public TentacleRage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = COST; //amount of damage
        this.magicNumber = this.baseMagicNumber = MAGIC;
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
            this.damage = this.baseDamage = effect;
            for (int i = 0; i < magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom(new AttackDamageRandomEnemyAction(this, AttackEffect.SLASH_HORIZONTAL));
            }
        }
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    @Override
   public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
        }
    }
}
