package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import entity.EntityMod;
import entity.util.TextureLoader;

public class HatefulExpanseRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(HatefulExpanseRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HatefulExpanse.png"));

    public HatefulExpanseRelic() {
        super(ID, IMG, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
