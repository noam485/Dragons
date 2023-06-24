import java.util.HashMap;
import java.util.HashSet;

public abstract class Island {
    final int numberOfDragons;

    HashMap<String, Dragon> inIslandDragons = new HashMap<>();
    HashMap<String, Dragon> outOfIslandDragons = new HashMap<>();

    public Island(int pNumberOfDragons) {
        numberOfDragons = pNumberOfDragons;
    }

    abstract boolean isReal();

    public void createImaginedPossibleIslands() {
        for (Dragon dragon: inIslandDragons.values()) {
            dragon.createImaginedPossibleIslands();
        }
    }

    public abstract void updateLeavingDragonsExpectation();


    public HashSet<Dragon> getInIslandBlueEyedDragons() {
        HashSet<Dragon> blueEyed = new HashSet<>();
        for (Dragon dragon: inIslandDragons.values()) {
            if (dragon.isBlueEyed) blueEyed.add(dragon);
        }
        return blueEyed;
    }

    public void updatePossibleImaginedIslands() {
        for (Dragon dragon: inIslandDragons.values()) {
            dragon.updatePossibleImaginedIslands();
        }
    }
}
