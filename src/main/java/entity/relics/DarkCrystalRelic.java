package entity.relics;

import static entity.EntityMod.makeRelicOutlinePath;
import static entity.EntityMod.makeRelicPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomRelic;
import entity.EntityMod;
import entity.powers.EssencePower;
import entity.util.TextureLoader;

public class DarkCrystalRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(DarkCrystalRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DarkCrystal.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DarkCrystal.png"));

    private static final int ESSENCE_AMT = 5;

    public DarkCrystalRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() <= 1) {
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new EssencePower(p, p, ESSENCE_AMT), ESSENCE_AMT));
        }
        super.onUseCard(targetCard, useCardAction);
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ESSENCE_AMT + this.DESCRIPTIONS[1];
    }
}
