package entity.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
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
    }

    @Override
    public void update() {
        virulentPoxPower.flash();
        int fluxTransferred = virulentPoxPower.amount * fluxAmount;
        // Figure out how to rng to a non-dying/non-dead creature
//        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(new FluxPower(), fluxTransferred));
        this.isDone = true;
    }
}
