public class ImaginedIsland extends Island {
    static int instanceCount = 0;
    public ImaginedIsland(int pNumberOfPersons, Person pImaginingPerson) {
        super(pNumberOfPersons, pImaginingPerson);
        instanceCount ++;
    }

    @Override
    public void updateLeavingPersonsExpectation() {
        for (Person person : inIslandPersons.values()) {
            ((ImaginedPerson) person).updateLeavingExpectation();
        }
    }
}
