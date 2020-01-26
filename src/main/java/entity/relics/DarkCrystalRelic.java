package entity.relics;

import static entity.EntityMod.makeRelicOutlinePath;
import static entity.EntityMod.makeRelicPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.random.Random;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomRelic;
import entity.EntityMod;
import entity.powers.EssencePower;
import entity.util.TextureLoader;

public class DarkCrystalRelic extends CustomRelic {
    // ID, images, text.
    public static final String ID = EntityMod.makeID(DarkCrystalRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DarkCrystal.png"));

    private static final int ESSENCE_AMT = 3;

    public DarkCrystalRelic() {
        super(ID, IMG, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new EssencePower(p, p, ESSENCE_AMT), ESSENCE_AMT));
    }

    // Description
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ESSENCE_AMT + this.DESCRIPTIONS[1];
    }

}
