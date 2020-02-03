package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.FluxPower;

//Shared Fate	Common	Skill	1	Gain 3 Flux(remove gain 3 flux). Apply 3 Flux to all enemies.
public class Anomaly extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Anomaly.class.getSimpleName());
    public static final String IMG = makeCardPath("Anomaly.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 0;

    private static final int FLUX = 7;
    private static final int UPGRADE_PLUS_FLUX = 2;

    public Anomaly() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.flux = this.baseFlux = FLUX;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new StrengthPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new LoseStrengthPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeFlux(UPGRADE_PLUS_FLUX);
        }
    }
}