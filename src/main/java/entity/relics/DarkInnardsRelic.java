package entity.relics;

import static entity.EntityMod.makeRelicPath;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
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

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (drawnCard.type.equals(CardType.CURSE)) {
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, ARTIFACT_AMT), ARTIFACT_AMT));
        }
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ARTIFACT_AMT + this.DESCRIPTIONS[1];
    }
}
