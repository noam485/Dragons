import java.util.HashSet;

public class OptimizedPerson {
    static long nextUniqueId = 1;
    final OptimizedIsland residenceIsland;
    final boolean isBlueEyed;
    final String name;
    final long uniqueId;
    HashSet<OptimizedIsland> possibleIslands;

    protected OptimizedPerson(boolean pIsBlueEyed, String pName, OptimizedIsland pIsland) {
        isBlueEyed = pIsBlueEyed;
        name = pName;
        residenceIsland = pIsland;
        uniqueId = nextUniqueId++;
    }

    public boolean isAwareOfHavingBlueEyes() {
        return possibleIslands!= null && possibleIslands.size() == 1 && isBlueEyed;
    }

    public void generatePossibleIslands() {
        possibleIslands = new HashSet<>();
        for (boolean myEyesAreBlue : new boolean[]{true, false}) {
            if (!areThereBlueEyedNeighbours() && !myEyesAreBlue)
                continue; // if the only blue-eyed is this person then this person would know they are blue-eyed
            OptimizedIsland island = new OptimizedIsland(residenceIsland.blueEyedCount, residenceIsland.brownEyedCount, this);
            if (isBlueEyed) {
                if (myEyesAreBlue) {
                    island.blueEyedCount = residenceIsland.blueEyedCount;
                    island.brownEyedCount = residenceIsland.brownEyedCount;
                } else {
                    island.blueEyedCount = residenceIsland.blueEyedCount - 1;
                    island.brownEyedCount = residenceIsland.brownEyedCount + 1;
                }
            } else {
                if (myEyesAreBlue) {
                    island.blueEyedCount = residenceIsland.blueEyedCount + 1;
                    island.brownEyedCount = residenceIsland.brownEyedCount - 1;
                } else {
                    island.blueEyedCount = residenceIsland.blueEyedCount;
                    island.brownEyedCount = residenceIsland.brownEyedCount;
                }
            }
            if (island.blueEyedCount >=0 && island.brownEyedCount >=0) {
                possibleIslands.add(island);
            }
        }
        for (OptimizedIsland island : possibleIslands) {
            if (!island.isAlreadyGeneratedBefore()) {
                island.generatePossibleIslands();
            }
        }
    }

    private boolean areThereBlueEyedNeighbours() {
        if (isBlueEyed) {
            return residenceIsland.blueEyedCount >= 2;
        } else {
            return residenceIsland.blueEyedCount >= 1;
        }
    }

    public void removeImpossibleIslands() {
        HashSet<OptimizedIsland> impossibleImaginedIslands = new HashSet<>();
        if (possibleIslands == null) return;
        for (OptimizedIsland imaginedIsland : possibleIslands) {
            if (isLeavingExpectationMatchObservation(imaginedIsland)) {
                imaginedIsland.updatePossibleIslands();
            } else {
                impossibleImaginedIslands.add(imaginedIsland);
            }
        }
        possibleIslands.removeAll(impossibleImaginedIslands);
    }

    private boolean isLeavingExpectationMatchObservation(OptimizedIsland imaginedIsland) {
        if (imaginedIsland.blueEyed.isAwareOfHavingBlueEyes()) {
            return residenceIsland.blueEyedLeft;
        }
        return true;
    }


}
