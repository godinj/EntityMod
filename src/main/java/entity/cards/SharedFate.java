package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.FluxPower;

import java.util.Iterator;

//Shared Fate	Common	Skill	1	Gain 3 Flux(remove gain 3 flux). Apply 3 Flux to all enemies.
public class SharedFate extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(SharedFate.class.getSimpleName());
    public static final String IMG = makeCardPath("AetherForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    private static final int FLUX = 3;

    public SharedFate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        flux = baseFlux = FLUX;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //if upgraded, targets only enemies, otherwise targets both player and enemies.
        if (!this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FluxPower(p, p, flux), flux));
        }
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo == null || mo.isDead || mo.isDying) {
                continue;
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                new FluxPower(mo, p, flux), flux));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}