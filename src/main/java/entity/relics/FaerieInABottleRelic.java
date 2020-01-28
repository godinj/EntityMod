package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import entity.EntityMod;
import entity.util.TextureLoader;

public class FaerieInABottleRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(FaerieInABottleRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FaerieInABottle.png"));

    public FaerieInABottleRelic() {
        super(ID, IMG, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
