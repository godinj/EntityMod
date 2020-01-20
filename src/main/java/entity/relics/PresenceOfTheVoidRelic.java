package entity.relics;

import static entity.EntityMod.makeRelicOutlinePath;
import static entity.EntityMod.makeRelicPath;

import com.megacrit.cardcrawl.random.Random;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomRelic;
import entity.EntityMod;
import entity.util.TextureLoader;

public class PresenceOfTheVoidRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID("PresenceOfTheVoidRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PresenceOfTheVoid.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PresenceOfTheVoid.png"));

    public PresenceOfTheVoidRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        int cost = 0;
        int index = 0;
        java.util.Vector<Integer> candidateIndices = new java.util.Vector<Integer>();
        int highestCost = 0;
        if (AbstractDungeon.player.hand.isEmpty()) {
            return;
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.costForTurn > highestCost && !c.isEthereal) {
                candidateIndices.clear();
                highestCost = c.costForTurn;
                candidateIndices.add(index);
            } else if (c.costForTurn == highestCost && !c.isEthereal) {
                candidateIndices.add(index);
            }
            index++;
        }

        if (candidateIndices.isEmpty()) {
            return;
        }
        Random rand = new Random();
        int chosenCardIndex = candidateIndices.get(rand.random(0, candidateIndices.size() - 1));
        AbstractCard highest = AbstractDungeon.player.hand.group.get(chosenCardIndex);
        highest.retain = true;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
