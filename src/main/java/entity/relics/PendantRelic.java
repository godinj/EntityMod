package entity.relics;

import static entity.EntityMod.makeRelicOutlinePath;
import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import entity.EntityMod;
import entity.util.TextureLoader;

public class PendantRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(PendantRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Pendant.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Pendant.png"));

    private static final int ARTIFACT = 1;
    private static final int TURN_ACTIVATION = 3;

    public PendantRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    public void atTurnStart() {
        this.counter++;
        if (this.counter == TURN_ACTIVATION) {
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, ARTIFACT), ARTIFACT));
            this.counter = 0;
        }
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + TURN_ACTIVATION + this.DESCRIPTIONS[1];
    }
}
