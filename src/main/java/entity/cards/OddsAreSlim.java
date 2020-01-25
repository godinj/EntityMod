package entity.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.EssencePower;

import static entity.EntityMod.makeCardPath;

//Odds are slim	uncommon	skill	0	Gain 2 weak and 2 vulnerable, gain 2(3)artifact.
public class OddsAreSlim extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(OddsAreSlim.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 0;

    private static final int ARTIFACT = 2;
    private static final int UPGRADE_PLUS_ARTIFACT = 1; //this plus ARTIFACT is the total.
    // Represents both the Vulnerability and Weakness applied to self.
    private static final int SELF_MAGIC = 2;

    public OddsAreSlim() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.artifact = this.baseArtifact = ARTIFACT;
        this.selfMagicNumber = this.baseSelfMagicNumber = SELF_MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //adds 2 vulnerable to player
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new VulnerablePower(p, selfMagicNumber, false), selfMagicNumber));

        //adds 2 Weak to player
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new WeakPower(p, selfMagicNumber, false), selfMagicNumber));

        //adds Artifacts
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new ArtifactPower(p, artifact), artifact)); //adds artifacts equal to ARTIFACT
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeArtifact(UPGRADE_PLUS_ARTIFACT);
        }
    }
}