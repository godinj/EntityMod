package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import entity.EntityMod;
import entity.util.TextureLoader;

public class ShroudRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(ShroudRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Shroud.png"));

    public ShroudRelic() {
        super(ID, IMG, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
