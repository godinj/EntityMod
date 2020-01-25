package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.OtherworldlyPower;

// TODO: Rename to Warped Form
public class Otherworldly extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Otherworldly.class.getSimpleName());
    public static final String IMG = makeCardPath("Otherworldly.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;

    private static final int ARTIFACT = 3;
    private static final int UPGRADE_PLUS_ARTIFACT = 2;

    // Represents Vulnerable amount applied each turn.
    private static final int MAGIC = 1;

    public Otherworldly() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.artifact = this.baseArtifact = ARTIFACT;
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new OtherworldlyPower(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new ArtifactPower(p, artifact), artifact));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeArtifact(UPGRADE_PLUS_ARTIFACT);
        }
    }
}
