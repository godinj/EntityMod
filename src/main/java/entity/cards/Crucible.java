package entity.cards;

import static entity.EntityMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.characters.Entity;
import entity.powers.FluxPower;

//Shared Fate	Common	Skill	1	Gain 3 Flux(remove gain 3 flux). Apply 3 Flux to all enemies.
public class Crucible extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(Crucible.class.getSimpleName());
    public static final String IMG = makeCardPath("Crucible.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 2;

    private static final int FLUX = 7;
    private static final int UPGRADE_PLUS_FLUX = 2;

    public Crucible() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.flux = this.baseFlux = FLUX;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo == null || mo.isDead || mo.isDying) {
                continue;
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new FluxPower(mo, p, flux)));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeFlux(UPGRADE_PLUS_FLUX);
        }
    }
}