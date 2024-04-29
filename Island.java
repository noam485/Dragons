import java.util.HashMap;
import java.util.HashSet;

public abstract class Island {
    final int numberOfDragons;

    HashMap<String, Person> inIslandDragons = new HashMap<>();
    HashMap<String, Person> outOfIslandDragons = new HashMap<>();

    public Island(int pNumberOfDragons) {
        numberOfDragons = pNumberOfDragons;
    }

    abstract boolean isReal();

    public void createImaginedPossibleIslands() {
        for (Person person : inIslandDragons.values()) {
            person.createImaginedPossibleIslands();
        }
    }

    public abstract void updateLeavingDragonsExpectation();


    public HashSet<Person> getInIslandBlueEyedDragons() {
        HashSet<Person> blueEyed = new HashSet<>();
        for (Person person : inIslandDragons.values()) {
            if (person.isBlueEyed) blueEyed.add(person);
        }
        return blueEyed;
    }

    public void updatePossibleImaginedIslands() {
        for (Person person : inIslandDragons.values()) {
            person.updatePossibleImaginedIslands();
        }
    }

    public int depth() {
        int depth = 0;
        for (Person person : inIslandDragons.values()) {
            depth = Math.max(depth, person.depth());
        }
        return depth;
    }
}
