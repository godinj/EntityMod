package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import entity.EntityMod;
import entity.util.TextureLoader;

public class DarkInnardsRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(DarkInnardsRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DarkInnards.png"));

    private static final int ARTIFACT_AMT = 1;

    public DarkInnardsRelic() {
        super(ID, IMG, RelicTier.RARE, LandingSound.MAGICAL);
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ARTIFACT_AMT + this.DESCRIPTIONS[1];
    }

}
