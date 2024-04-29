import java.util.HashSet;

public class RealIsland extends Island {
    static int instanceCount = 0;

    RealIsland(int dragonsCount) {
        super(dragonsCount);
        if (instanceCount > 0) {
            System.out.println("Shouldn't create more than 1 real islands");
        }
        for (int i=1; i<=dragonsCount; i++) {
            RealPerson realDragon = new RealPerson(this, "Dragon_" + i);
            inIslandPersons.put(realDragon.id, realDragon);
        }
        for (Person person : inIslandPersons.values()) {
            person.createImaginedPossibleIslands();
        }
    }

    @Override
    boolean isReal() {
        return true;
    }

    @Override
    public void updateLeavingPersonsExpectation() {
        for (Person person : inIslandPersons.values()) {
            person.updateLeavingDragonsExpectation();
        }
    }

    public void awareBlueEyedRealDragonsLeave() {
        HashSet<Person> leavingPeople = new HashSet<>();
        for (Person person : inIslandPersons.values()) {
            if (((RealPerson) person).isAwareOfHavingBlueEyes()) {
                leavingPeople.add(person);
            }
        }
        for (Person person : leavingPeople) {
            inIslandPersons.remove(person.id);
            outOfIslandDragons.put(person.id, person);
        }
    }
}
