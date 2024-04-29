public class ImaginedIsland extends Island {
    static int instanceCount = 0;
    final Person imaginingPerson;
    public ImaginedIsland(int pNumberOfPersons, Person pImaginingPerson) {
        super(pNumberOfPersons);
        imaginingPerson = pImaginingPerson;
        instanceCount ++;
    }

    public boolean isAlreadyImaginedBefore() {
        Island island = imaginingPerson.residenceIsland;
        while (!island.isReal()) {
            if (((ImaginedIsland)island).sameEyeColors(this)) {
                return true;
            }
            island = ((ImaginedIsland)island).imaginingPerson.residenceIsland;
        }
        return false;
    }

    private boolean sameEyeColors(ImaginedIsland other) {
        if (inIslandPersons.size() != other.inIslandPersons.size()) return false;
        for (Person person : inIslandPersons.values()) {
            if (!other.inIslandPersons.containsKey(person.id)) return false;
            if (person.isBlueEyed != other.inIslandPersons.get(person.id).isBlueEyed) return false;
        }
        return true;
    }

    @Override
    boolean isReal() {
        return false;
    }

    @Override
    public void updateLeavingPersonsExpectation() {
        for (Person person : inIslandPersons.values()) {
            ((ImaginedPerson) person).updateLeavingExpectation();
        }
    }

    public boolean isLeavingExpectationMatchObservation(Island island) {
        for (Person person : island.inIslandPersons.values()) {
            if ((inIslandPersons.get(person.id)).imaginedIslands == null) continue; // leaf dragon
            if (((ImaginedPerson) inIslandPersons.get(person.id)).isExpectedToLeave) return false;
        }
        for (Person person : island.outOfIslandDragons.values()) {
            if ((inIslandPersons.get(person.id)).imaginedIslands == null) continue; // leaf dragon
            if (!((ImaginedPerson) inIslandPersons.get(person.id)).isExpectedToLeave) return false;
        }
        return true;
    }
}
