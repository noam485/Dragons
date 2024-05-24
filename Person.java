import java.util.HashSet;

public class Person {
    final Island residenceIsland;
    final boolean isBlueEyed;
    final String id;
    final long uniqueId;
    static long nextUniqueId = 1;
    HashSet<Island> possibleIslands;

    protected Person(boolean pIsBlueEyed, String pId, Island pIsland) {
        isBlueEyed = pIsBlueEyed;
        id = pId;
        residenceIsland = pIsland;
        uniqueId = nextUniqueId++;
    }

    public boolean isAwareOfHavingBlueEyes() {
        return possibleIslands.size() == 1 && possibleIslands.iterator().next().inIslandPersons.get(id).isBlueEyed;
    }

    public void generatePossibleIslands() {
        possibleIslands = new HashSet<>();
        for (boolean myEyesAreBlue: new boolean[] {true, false}) {
            HashSet<Person> inIslandBlueEyedPersons = residenceIsland.getInIslandBlueEyedPersons();
            if (residenceIsland.getBlueEyedPersonCount() == 1 && inIslandBlueEyedPersons.iterator().next() == this && !myEyesAreBlue) continue; // if the only blue-eyed is this person then this person would know they are blue-eyed
            Island island = new Island(residenceIsland.numberOfPersons, this);
            for (Person person : residenceIsland.inIslandPersons.values()) {
                boolean isImaginedPersonBlueEyed = person.isBlueEyed;
                if (person == this) isImaginedPersonBlueEyed = myEyesAreBlue;
                Person imaginedPerson = new Person(isImaginedPersonBlueEyed, person.id, island);
                island.inIslandPersons.put(imaginedPerson.id, imaginedPerson);
                possibleIslands.add(island);
            }
        }
        for (Island island: possibleIslands) {
            if (!island.isAlreadyGeneratedBefore()) {
                island.generatePossibleIslands();
            }
        }
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
            if ((imaginedIsland.inIslandPersons.get(person.id)).possibleIslands == null) continue; // leaf person
            if (( imaginedIsland.inIslandPersons.get(person.id)).isAwareOfHavingBlueEyes()) return false;
        }
        for (Person person : residenceIsland.outOfIslandPersons.values()) {
            if ((imaginedIsland.inIslandPersons.get(person.id)).possibleIslands == null) continue; // leaf person
            if (!( imaginedIsland.inIslandPersons.get(person.id)).isAwareOfHavingBlueEyes()) return false;
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
