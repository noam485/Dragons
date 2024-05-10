public class ImaginedPerson extends Person {
    Boolean isExpectedToLeave;
    public ImaginedPerson(boolean isBlueEyed, String id, Person pImaginingPerson, Island pIsland) {
        super(isBlueEyed, id, pIsland);
    }

    public void updateLeavingExpectation() {
        if (imaginedIslands == null) return;
        Boolean before = isExpectedToLeave;
        isExpectedToLeave = imaginedIslands.size() == 1 && imaginedIslands.iterator().next().inIslandPersons.get(id).isBlueEyed;
        if (before != null && before && !isExpectedToLeave) {
            System.out.println("err in updateLeavingExpectation");
        }
        if (imaginedIslands != null) {
            for (Island imaginedIsland: imaginedIslands) {
                imaginedIsland.updateLeavingPersonsExpectation();
            }
        }
    }
}
