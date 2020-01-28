package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import entity.EntityMod;
import entity.util.TextureLoader;

public class SchoolGirlRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(SchoolGirlRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SchoolGirl.png"));

    private static final int DRAW_AMT = 1;

    public SchoolGirlRelic() {
        super(ID, IMG, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        flash();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, DRAW_AMT));
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
