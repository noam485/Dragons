import java.util.HashSet;

public class RealIsland extends Island {
    static int instanceCount = 0;

    RealIsland(int personsCount) throws Exception {
        super(personsCount, null);
        if (instanceCount > 0) {
            throw new Exception("Shouldn't create more than 1 real islands");
        }
        for (int i=1; i<=personsCount; i++) {
            RealPerson realPerson = new RealPerson(this, "Person_" + i);
            inIslandPersons.put(realPerson.id, realPerson);
        }
        for (Person person : inIslandPersons.values()) {
            person.createImaginedPossibleIslands();
        }
    }

    @Override
    public void updateLeavingPersonsExpectation() {
        for (Person person : inIslandPersons.values()) {
            person.updateLeavingPersonsExpectation();
        }
    }

    public void awareBlueEyedPersonsLeave() {
        HashSet<Person> leavingPeople = new HashSet<>();
        for (Person person : inIslandPersons.values()) {
            if (((RealPerson) person).isAwareOfHavingBlueEyes()) {
                leavingPeople.add(person);
            }
        }
        for (Person person : leavingPeople) {
            inIslandPersons.remove(person.id);
            outOfIslandPersons.put(person.id, person);
        }
    }
}
