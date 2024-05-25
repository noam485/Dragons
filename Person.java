import java.util.HashSet;

public class Person {
    static long nextUniqueId = 1;
    final Island residenceIsland;
    final boolean isBlueEyed;
    final String name;
    final long uniqueId;
    HashSet<Island> possibleIslands;

    protected Person(boolean pIsBlueEyed, String pName, Island pIsland) {
        isBlueEyed = pIsBlueEyed;
        name = pName;
        residenceIsland = pIsland;
        uniqueId = nextUniqueId++;
    }

    public boolean isAwareOfHavingBlueEyes() {
        return possibleIslands.size() == 1 && possibleIslands.iterator().next().inIslandPersons.get(name).isBlueEyed;
    }

    public void generatePossibleIslands() {
        possibleIslands = new HashSet<>();
        for (boolean myEyesAreBlue: new boolean[] {true, false}) {
            if (!areThereBlueEyedNeighbours() && !myEyesAreBlue) continue; // if the only blue-eyed is this person then this person would know they are blue-eyed
            Island island = new Island(residenceIsland.numberOfPersons, this);
            for (Person person : residenceIsland.inIslandPersons.values()) {
                boolean isImaginedPersonBlueEyed = person.isBlueEyed;
                if (person == this) isImaginedPersonBlueEyed = myEyesAreBlue;
                Person imaginedPerson = new Person(isImaginedPersonBlueEyed, person.name, island);
                island.inIslandPersons.put(imaginedPerson.name, imaginedPerson);
                possibleIslands.add(island);
            }
        }
        for (Island island: possibleIslands) {
            if (!island.isAlreadyGeneratedBefore()) {
                island.generatePossibleIslands();
            }
        }
    }

    private boolean areThereBlueEyedNeighbours() {
        for (Person person: residenceIsland.inIslandPersons.values()) {
            if (person == this) continue;
            if (person.isBlueEyed) return true;
        }
        for (Person person: residenceIsland.outOfIslandPersons.values()) {
            if (person == this) continue;
            if (person.isBlueEyed) return true;
        }
        return false;
    }

    public void removeImpossibleImaginedIslands() {
        HashSet<Island> impossibleImaginedIslands = new HashSet<>();
        if (possibleIslands == null) return;
        for (Island imaginedIsland: possibleIslands) {
            if (isLeavingExpectationMatchObservation(imaginedIsland)) {
                imaginedIsland.updatePossibleImaginedIslands();
            } else {
                impossibleImaginedIslands.add(imaginedIsland);
            }
        }
        possibleIslands.removeAll(impossibleImaginedIslands);
    }

    private boolean isLeavingExpectationMatchObservation(Island imaginedIsland) {
        for (Person person : residenceIsland.inIslandPersons.values()) {
            if ((imaginedIsland.inIslandPersons.get(person.name)).possibleIslands == null) continue; // leaf person
            if (( imaginedIsland.inIslandPersons.get(person.name)).isAwareOfHavingBlueEyes()) return false;
        }
        for (Person person : residenceIsland.outOfIslandPersons.values()) {
            if ((imaginedIsland.inIslandPersons.get(person.name)).possibleIslands == null) continue; // leaf person
            if (!( imaginedIsland.inIslandPersons.get(person.name)).isAwareOfHavingBlueEyes()) return false;
        }
        return true;
    }

    public int depth() {
        if (possibleIslands == null) return 1;
        int depth = 0;
        for (Island imaginedIsland: possibleIslands) {
            depth = Math.max(depth, imaginedIsland.depth());
        }
        return depth + 1;
    }
}
