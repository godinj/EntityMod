package entity.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import entity.EntityMod;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;

//Frenetic Find	rare	skill	1(0)	Gain 2(3) artifact, gain a slimed, void, dazed, burn, and wound card into your discard
public class FreneticFind extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(FreneticFind.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int ARTIFACT = 2;
    private static final int UPGRADE_PLUS_ARTIFACT = 1; //this plus ARTIFACT is the total.

    // Represents number of Void cards added.
    private static final int MAGIC = 1;
   // private static final int UPGRADE_PLUS_MAGIC = -1;

    public FreneticFind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.artifact = this.baseArtifact = ARTIFACT;
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new ArtifactPower(p, artifact), artifact)); //adds artifacts equal to ARTIFACT
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), magicNumber)); //add slimed cards equal to magic number.
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), magicNumber)); //add void cards equal to magic number.
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), magicNumber)); //add daze cards equal to magic number.
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), magicNumber)); //add burn cards equal to magic number.
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(), magicNumber)); //add wound cards equal to magic number.

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeArtifact(UPGRADE_PLUS_ARTIFACT);
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
