package entity.cards;

import com.megacrit.cardcrawl.actions.watcher.LessonLearnedAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import entity.EntityMod;
import entity.actions.UncoilingTendrilsAction;
import entity.characters.Entity;

import static entity.EntityMod.makeCardPath;

// Uncoiling Tendrils -- rare -- Attack -- 2 -- deal 10(12) damage. If this kills, Transform a common card in your deck at random. exhaust
public class UncoilingTendrils extends AbstractDynamicCard {
    public static final String ID = EntityMod.makeID(UncoilingTendrils.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidBlast.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Entity.Enums.COLOR_TEAL;

    private static final int COST = 1;

    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 3;

    public UncoilingTendrils() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new UncoilingTendrilsAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
