import java.util.Collection;
import java.util.HashSet;

public class ImaginedIsland extends Island {
    static int instanceCount = 0;
    final Dragon imaginingDragon;
    public ImaginedIsland(int pNumberOfDragons, Dragon pImaginingDragon) {
        super(pNumberOfDragons);
        imaginingDragon = pImaginingDragon;
        instanceCount ++;
    }

    public boolean isAlreadyImaginedBefore() {
        Island island = imaginingDragon.island;
        while (!island.isReal()) {
            if (((ImaginedIsland)island).sameEyeColors(this)) {
                return true;
            }
            island = ((ImaginedIsland)island).imaginingDragon.island;
        }
        return false;
    }

    private boolean sameEyeColors(ImaginedIsland other) {
        if (inIslandDragons.size() != other.inIslandDragons.size()) return false;
        for (Dragon dragon: inIslandDragons.values()) {
            if (!other.inIslandDragons.containsKey(dragon.id)) return false;
            if (dragon.isBlueEyed != other.inIslandDragons.get(dragon.id).isBlueEyed) return false;
        }
        return true;
    }

    @Override
    boolean isReal() {
        return false;
    }

    @Override
    public void updateLeavingDragonsExpectation() {
        for (Dragon dragon: inIslandDragons.values()) {
            ((ImaginedDragon)dragon).updateLeavingExpectation();
        }
    }

    public boolean isLeavingExpectationMatchObservation(Island island) {
        for (Dragon dragon: island.inIslandDragons.values()) {
            if ((inIslandDragons.get(dragon.id)).imaginedIslands == null) continue; // leaf dragon
            if (((ImaginedDragon)inIslandDragons.get(dragon.id)).isExpectedToLeave) return false;
        }
        for (Dragon dragon: island.outOfIslandDragons.values()) {
            if ((inIslandDragons.get(dragon.id)).imaginedIslands == null) continue; // leaf dragon
            if (!((ImaginedDragon)inIslandDragons.get(dragon.id)).isExpectedToLeave) return false;
        }
        return true;
    }
}
