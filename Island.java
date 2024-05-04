import java.util.HashMap;
import java.util.HashSet;

public abstract class Island {
    final int numberOfPersons;

    HashMap<String, Person> inIslandPersons = new HashMap<>();
    HashMap<String, Person> outOfIslandPersons = new HashMap<>();

    public Island(int pNumberOfPersons) {
        numberOfPersons = pNumberOfPersons;
    }

    abstract boolean isReal();

    public void createImaginedPossibleIslands() {
        for (Person person : inIslandPersons.values()) {
            person.createImaginedPossibleIslands();
        }
    }

    public abstract void updateLeavingPersonsExpectation();


    public int getBlueEyedPersonCount() {
        int count = 0;
        for (Person person : inIslandPersons.values()) {
            if (person.isBlueEyed) count++;
        }
        for (Person person : outOfIslandPersons.values()) {
            if (person.isBlueEyed) count++;
        }
        return count;
    }

    public HashSet<Person> getInIslandBlueEyedPersons() {
        HashSet<Person> blueEyed = new HashSet<>();
        for (Person person : inIslandPersons.values()) {
            if (person.isBlueEyed) blueEyed.add(person);
        }
        return blueEyed;
    }

    public void updatePossibleImaginedIslands() {
        for (Person person : inIslandPersons.values()) {
            person.updatePossibleImaginedIslands();
        }
    }

    public int depth() {
        int depth = 0;
        for (Person person : inIslandPersons.values()) {
            depth = Math.max(depth, person.depth());
        }
        return depth;
    }
}
