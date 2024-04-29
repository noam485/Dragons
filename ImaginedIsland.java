public class ImaginedIsland extends Island {
    static int instanceCount = 0;
    final Person imaginingPerson;
    public ImaginedIsland(int pNumberOfPersons, Person pImaginingPerson) {
        super(pNumberOfPersons);
        imaginingPerson = pImaginingPerson;
        instanceCount ++;
    }

    public boolean isAlreadyImaginedBefore() {
        Island island = imaginingPerson.island;
        while (!island.isReal()) {
            if (((ImaginedIsland)island).sameEyeColors(this)) {
                return true;
            }
            island = ((ImaginedIsland)island).imaginingPerson.island;
        }
        return false;
    }

    private boolean sameEyeColors(ImaginedIsland other) {
        if (inIslandDragons.size() != other.inIslandDragons.size()) return false;
        for (Person person : inIslandDragons.values()) {
            if (!other.inIslandDragons.containsKey(person.id)) return false;
            if (person.isBlueEyed != other.inIslandDragons.get(person.id).isBlueEyed) return false;
        }
        return true;
    }

    @Override
    boolean isReal() {
        return false;
    }

    @Override
    public void updateLeavingDragonsExpectation() {
        for (Person person : inIslandDragons.values()) {
            ((ImaginedPerson) person).updateLeavingExpectation();
        }
    }

    public boolean isLeavingExpectationMatchObservation(Island island) {
        for (Person person : island.inIslandDragons.values()) {
            if ((inIslandDragons.get(person.id)).imaginedIslands == null) continue; // leaf dragon
            if (((ImaginedPerson)inIslandDragons.get(person.id)).isExpectedToLeave) return false;
        }
        for (Person person : island.outOfIslandDragons.values()) {
            if ((inIslandDragons.get(person.id)).imaginedIslands == null) continue; // leaf dragon
            if (!((ImaginedPerson)inIslandDragons.get(person.id)).isExpectedToLeave) return false;
        }
        return true;
    }
}
