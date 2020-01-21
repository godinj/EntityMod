package entity.cards;

import basemod.abstracts.CustomCard;

public abstract class AbstractEntityCard extends CustomCard {
    public int artifact;
    public int baseArtifact;
    public boolean upgradedArtifact;
    public boolean isArtifactModified;
    public int essence;
    public int baseEssence;
    public boolean upgradedEssence;
    public boolean isEssenceModified;
    public int flux;
    public int baseFlux;
    public boolean upgradedFlux;
    public boolean isFluxModified;

    public AbstractEntityCard(final String id,
                              final String name,
                              final String img,
                              final int cost,
                              final String rawDescription,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        this.isCostModified = false;
        this.isCostModifiedForTurn = false;
        this.isDamageModified = false;
        this.isBlockModified = false;
        this.isMagicNumberModified = false;
        this.isEssenceModified = false;
        this.isFluxModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedArtifact) {
            artifact = baseArtifact;
            isArtifactModified = true;
        }
        if (upgradedEssence) {
            essence = baseEssence;
            isEssenceModified = true;
        }
        if (upgradedFlux) {
            flux = baseFlux;
            isFluxModified = true;
        }
    }

    public void upgradeArtifact(int amount) {
        baseArtifact += amount;
        artifact = baseArtifact;
        upgradedArtifact = true;
    }

    public void upgradeEssence(int amount) {
        baseEssence += amount;
        essence = baseEssence;
        upgradedEssence = true;
    }

    public void upgradeFlux(int amount) {
        baseFlux += amount;
        flux = baseFlux;
        upgradedFlux = true;
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();
        artifact = baseArtifact;
        isArtifactModified = false;
        essence = baseEssence;
        isEssenceModified = false;
        flux = baseFlux;
        isFluxModified = false;
    }
}
