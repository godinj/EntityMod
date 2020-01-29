package entity.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import entity.powers.FluxCapacitorPower;
import entity.powers.FluxPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FluxCapacitorAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(FluxCapacitorPower.class.getName());

    private AbstractPower power;

    public FluxCapacitorAction(AbstractPower power) {
        this.power = power;
    }

    @Override
    public void update() {
        this.power.flash();
        int blockGained = FluxPower.calculateTotalFlux();
        logger.info("BRUH -- blockGained: " + blockGained);
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.power.owner, this.power.owner, blockGained));
        this.isDone = true;
    }
}
