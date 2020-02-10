package entity.relics;

import static entity.EntityMod.makeRelicOutlinePath;
import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import entity.EntityMod;
import entity.powers.EssencePower;
import entity.util.TextureLoader;

public class CrystalChamberRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(CrystalChamberRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CrystalChamber.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CrystalChamber.png"));

    public static final int ESSENCE_REDUCTION = 1;

    public CrystalChamberRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(DarkCrystalRelic.ID);
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ESSENCE_REDUCTION + this.DESCRIPTIONS[1];
    }
}
