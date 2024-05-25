import java.util.HashMap;
import java.util.HashSet;

public class Island {
    final int numberOfPersons;

    HashMap<String, Person> inIslandPersons = new HashMap<>();
    HashMap<String, Person> outOfIslandPersons = new HashMap<>();
    Person imaginingPerson;

    public Island(int pNumberOfPersons, Person pImaginingPerson) {
        numberOfPersons = pNumberOfPersons;
        imaginingPerson = pImaginingPerson;
    }

    Island(int pNumberOfPersons) {
        numberOfPersons = pNumberOfPersons;
        for (int i=1; i<= pNumberOfPersons; i++) {
            Person person = new Person(true, "Person_" + i, this);
            inIslandPersons.put(person.name, person);
        }
        for (Person person : inIslandPersons.values()) {
            person.generatePossibleIslands();
        }
    }

    public boolean isAlreadyGeneratedBefore() {
        Island island = imaginingPerson.residenceIsland;
        while (island.imaginingPerson != null) {
            if ((island).sameEyeColors(this)) {
                return true;
            }
            island = (island).imaginingPerson.residenceIsland;
        }
        return false;
    }

    private boolean sameEyeColors(Island other) {
        if (inIslandPersons.size() != other.inIslandPersons.size()) return false;
        for (Person person : inIslandPersons.values()) {
            if (!other.inIslandPersons.containsKey(person.name)) return false;
            if (person.isBlueEyed != other.inIslandPersons.get(person.name).isBlueEyed) return false;
        }
        return true;
    }

    public void generatePossibleIslands() {
        for (Person person : inIslandPersons.values()) {
            person.generatePossibleIslands();
        }
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
            person.removeImpossibleImaginedIslands();
        }
    }

    public int depth() {
        int depth = 0;
        for (Person person : inIslandPersons.values()) {
            depth = Math.max(depth, person.depth());
        }
        return depth;
    }

    public void awareBlueEyedPersonsLeave() {
        HashSet<Person> leavingPeople = new HashSet<>();
        for (Person person : inIslandPersons.values()) {
            if (person.isAwareOfHavingBlueEyes()) {
                leavingPeople.add(person);
            }
        }
        for (Person person : leavingPeople) {
            inIslandPersons.remove(person.name);
            outOfIslandPersons.put(person.name, person);
        }
    }
}
