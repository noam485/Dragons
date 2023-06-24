import java.util.HashSet;

public abstract class Dragon {
    final Island island;
    final boolean isBlueEyed;
    final String id;
    final long uniqueId;
    static long nextUniqueId = 1;

    HashSet<ImaginedIsland> imaginedIslands;

    protected Dragon(boolean pIsBlueEyed, String pId, Island pIsland) {
        isBlueEyed = pIsBlueEyed;
        id = pId;
        island = pIsland;
        uniqueId = nextUniqueId++;
    }

    public void createImaginedPossibleIslands() {
        imaginedIslands = new HashSet<>();
        for (boolean bool: new boolean[] {true, false}) {
            HashSet<Dragon> inIslandBlueEyedDragons = island.getInIslandBlueEyedDragons();
            if (inIslandBlueEyedDragons.size() == 1 && inIslandBlueEyedDragons.iterator().next() == this && !bool) continue;
            ImaginedIsland imaginedIsland = new ImaginedIsland(island.numberOfDragons, this);
            for (Dragon dragon: island.inIslandDragons.values()) {
                boolean isImaginedDragonBlueEyed = dragon.isBlueEyed;
                if (dragon == this) isImaginedDragonBlueEyed = bool;
                ImaginedDragon imaginedDragon = new ImaginedDragon(isImaginedDragonBlueEyed, dragon.id, this, imaginedIsland);
                imaginedIsland.inIslandDragons.put(imaginedDragon.id, imaginedDragon);
                imaginedIslands.add(imaginedIsland);
            }
        }
        for (ImaginedIsland imaginedIsland: imaginedIslands) {
            if (!imaginedIsland.isAlreadyImaginedBefore()) {
                imaginedIsland.createImaginedPossibleIslands();
            }
        }
    }

    public void updateLeavingDragonsExpectation() {
        for (ImaginedIsland imaginedIsland: imaginedIslands) {
            imaginedIsland.updateLeavingDragonsExpectation();
        }
    }

    public void updatePossibleImaginedIslands() {
        HashSet<ImaginedIsland> impossibleImaginedIslands = new HashSet<>();
        if (imaginedIslands == null) return;;
        for (ImaginedIsland imaginedIsland: imaginedIslands) {
            if (imaginedIsland.isLeavingExpectationMatchObservation(island)) {
                imaginedIsland.updatePossibleImaginedIslands();
            } else {
                impossibleImaginedIslands.add(imaginedIsland);
            }
        }
        imaginedIslands.removeAll(impossibleImaginedIslands);
    }

    public int depth() {
        if (imaginedIslands == null) return 1;
        int depth = 0;
        for (ImaginedIsland imaginedIsland: imaginedIslands) {
            depth = Math.max(depth, imaginedIsland.depth());
        }
        return depth + 1;
    }
}
