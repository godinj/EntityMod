package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import entity.EntityMod;
import entity.characters.Entity;

public class BoundlessAether extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(BoundlessAether.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardTarget UPGRADED_TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 0;

    private static final int ARTIFACT = 1;

    public BoundlessAether() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.artifact = this.baseArtifact = ARTIFACT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, artifact), artifact));
        // Not upgraded, this card applies Artifact to all characters.
        // Upgraded, this card applies Artifact to the player and one enemy.
        if (!this.upgraded) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo == null || mo.isDead || mo.isDying) {
                    continue;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new ArtifactPower(mo, artifact), artifact));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                new ArtifactPower(m, artifact), artifact));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.target = UPGRADED_TARGET;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
