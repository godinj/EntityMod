package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import entity.EntityMod;
import entity.util.TextureLoader;

public class JazzOrganRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(JazzOrganRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("JazzOrgan.png"));

    public static final int DRAW_BONUS = 1;

    public JazzOrganRelic() {
        super(ID, IMG, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
