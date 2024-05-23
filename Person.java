import java.util.HashSet;

public class Person {
    final Island residenceIsland;
    final boolean isBlueEyed;
    final String id; // an instance of the person may appear on another island (real/imagined) and would have the same id
    final long uniqueId; // unique over all real and imagined persons
    static long nextUniqueId = 1;

    Boolean shouldLeave;

    HashSet<Island> imaginedIslands; // each imagined island is a possible island in the person's eyes

    protected Person(boolean pIsBlueEyed, String pId, Island pIsland) {
        isBlueEyed = pIsBlueEyed;
        id = pId;
        residenceIsland = pIsland;
        uniqueId = nextUniqueId++;
    }

    public boolean isAwareOfHavingBlueEyes() {
        return imaginedIslands.size() == 1 && imaginedIslands.iterator().next().inIslandPersons.get(id).isBlueEyed;
    }

    public void createImaginedPossibleIslands() {
        imaginedIslands = new HashSet<>();
        for (boolean myEyesAreBlue: new boolean[] {true, false}) {
            HashSet<Person> inIslandBlueEyedPersons = residenceIsland.getInIslandBlueEyedPersons();
            if (residenceIsland.getBlueEyedPersonCount() == 1 && inIslandBlueEyedPersons.iterator().next() == this && !myEyesAreBlue) continue; // if the only blue-eyed is this person then this person would know they are blue-eyed
            Island island = new Island(residenceIsland.numberOfPersons, this);
            for (Person person : residenceIsland.inIslandPersons.values()) {
                boolean isImaginedPersonBlueEyed = person.isBlueEyed;
                if (person == this) isImaginedPersonBlueEyed = myEyesAreBlue;
                Person imaginedPerson = new Person(isImaginedPersonBlueEyed, person.id, island);
                island.inIslandPersons.put(imaginedPerson.id, imaginedPerson);
                imaginedIslands.add(island);
            }
        }
        for (Island imaginedIsland: imaginedIslands) {
            if (!imaginedIsland.isAlreadyImaginedBefore()) {
                imaginedIsland.createImaginedPossibleIslands();
            }
        }
    }

    public void reasonWhoShouldLeave() {
        if (imaginedIslands == null) return;
        Boolean before = shouldLeave;
        shouldLeave = imaginedIslands.size() == 1 && imaginedIslands.iterator().next().inIslandPersons.get(id).isBlueEyed;
        if (before != null && before && !shouldLeave) {
            System.out.println("err in updateLeavingExpectation");
        }
        if (imaginedIslands != null) {
            for (Island imaginedIsland: imaginedIslands) {
                imaginedIsland.updateLeavingPersonsExpectation();
            }
        }
    }

    public void removeImpossibleImaginedIslands() {
        HashSet<Island> impossibleImaginedIslands = new HashSet<>();
        if (imaginedIslands == null) return;
        for (Island imaginedIsland: imaginedIslands) {
            if (isLeavingExpectationMatchObservation(imaginedIsland)) {
                imaginedIsland.updatePossibleImaginedIslands();
            } else {
                impossibleImaginedIslands.add(imaginedIsland);
            }
        }
        imaginedIslands.removeAll(impossibleImaginedIslands);
    }

    private boolean isLeavingExpectationMatchObservation(Island imaginedIsland) {
        for (Person person : residenceIsland.inIslandPersons.values()) {
            if ((imaginedIsland.inIslandPersons.get(person.id)).imaginedIslands == null) continue; // leaf person
            if (( imaginedIsland.inIslandPersons.get(person.id)).shouldLeave) return false;
        }
        for (Person person : residenceIsland.outOfIslandPersons.values()) {
            if ((imaginedIsland.inIslandPersons.get(person.id)).imaginedIslands == null) continue; // leaf person
            if (!( imaginedIsland.inIslandPersons.get(person.id)).shouldLeave) return false;
        }
        return true;
    }

    public int depth() {
        if (imaginedIslands == null) return 1;
        int depth = 0;
        for (Island imaginedIsland: imaginedIslands) {
            depth = Math.max(depth, imaginedIsland.depth());
        }
        return depth + 1;
    }
}
