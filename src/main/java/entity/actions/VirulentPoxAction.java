package entity.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.EntityMod;
import entity.cards.VirulentPox;
import entity.powers.FluxCapacitorPower;
import entity.powers.FluxPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VirulentPoxAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(FluxCapacitorPower.class.getName());

    private AbstractPower virulentPoxPower;
    private int fluxAmount;

    public VirulentPoxAction(AbstractPower virulentPoxPower, int fluxAmount) {
        this.virulentPoxPower = virulentPoxPower;
        this.fluxAmount = fluxAmount;
        EntityMod.logger.info("BRO -- action constructor");
    }

    @Override
    public void update() {
        AbstractCreature vPOwner = this.virulentPoxPower.owner;
        EntityMod.logger.info("BRO -- vp owner: " + vPOwner);
        this.virulentPoxPower.flash();
        int fluxTransferred = this.virulentPoxPower.amount * this.fluxAmount;
        EntityMod.logger.info("BRO -- flux transferred: " + this.fluxAmount);
        AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.monsterRng);
        EntityMod.logger.info("BRO -- random monster: " + mo);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, vPOwner, new FluxPower(mo, vPOwner, fluxTransferred), fluxTransferred));
        EntityMod.logger.info("BRO -- action added to bottom");
        this.isDone = true;
    }
}
