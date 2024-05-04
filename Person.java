import java.util.HashSet;

public abstract class Person {
    final Island residenceIsland;
    final boolean isBlueEyed;
    final String id; // an instance of the person may appear on another island (real/imagined) and would have the same id
    final long uniqueId; // unique over all real and imagined persons
    static long nextUniqueId = 1;

    HashSet<Island> imaginedIslands; // each imagined island is a possible island in the person's eyes

    protected Person(boolean pIsBlueEyed, String pId, Island pIsland) {
        isBlueEyed = pIsBlueEyed;
        id = pId;
        residenceIsland = pIsland;
        uniqueId = nextUniqueId++;
    }

    public void createImaginedPossibleIslands() {
        imaginedIslands = new HashSet<>();
        for (boolean myEyesAreBlue: new boolean[] {true, false}) {
            HashSet<Person> inIslandBlueEyedPersons = residenceIsland.getInIslandBlueEyedPersons();
            if (residenceIsland.getBlueEyedPersonCount() == 1 && inIslandBlueEyedPersons.iterator().next() == this && !myEyesAreBlue) continue; // if the only blue-eyed is this person then this person would know they are blue-eyed
            ImaginedIsland imaginedIsland = new ImaginedIsland(residenceIsland.numberOfPersons, this);
            for (Person person : residenceIsland.inIslandPersons.values()) {
                boolean isImaginedPersonBlueEyed = person.isBlueEyed;
                if (person == this) isImaginedPersonBlueEyed = myEyesAreBlue;
                ImaginedPerson imaginedPerson = new ImaginedPerson(isImaginedPersonBlueEyed, person.id, this, imaginedIsland);
                imaginedIsland.inIslandPersons.put(imaginedPerson.id, imaginedPerson);
                imaginedIslands.add(imaginedIsland);
            }
        }
        for (Island imaginedIsland: imaginedIslands) {
            if (!imaginedIsland.isAlreadyImaginedBefore()) {
                imaginedIsland.createImaginedPossibleIslands();
            }
        }
    }

    public void updateLeavingPersonsExpectation() {
        for (Island imaginedIsland: imaginedIslands) {
            imaginedIsland.updateLeavingPersonsExpectation();
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
            if (((ImaginedPerson) imaginedIsland.inIslandPersons.get(person.id)).isExpectedToLeave) return false;
        }
        for (Person person : residenceIsland.outOfIslandPersons.values()) {
            if ((imaginedIsland.inIslandPersons.get(person.id)).imaginedIslands == null) continue; // leaf person
            if (!((ImaginedPerson) imaginedIsland.inIslandPersons.get(person.id)).isExpectedToLeave) return false;
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
