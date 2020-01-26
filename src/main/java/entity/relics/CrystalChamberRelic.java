package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import entity.EntityMod;
import entity.util.TextureLoader;

public class CrystalChamberRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(CrystalChamberRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CrystalChamber.png"));

    public CrystalChamberRelic() {
        super(ID, IMG, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(DarkCrystalRelic.ID);
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
