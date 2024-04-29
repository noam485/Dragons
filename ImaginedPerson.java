public class ImaginedPerson extends Person {
    static int totalExpectedToLeaveCount = 0;
    final Person imaginingPerson;
    Boolean isExpectedToLeave;
    public ImaginedPerson(boolean isBlueEyed, String id, Person pImaginingPerson, Island pIsland) {
        super(isBlueEyed, id, pIsland);
        imaginingPerson = pImaginingPerson;
    }

    public void updateLeavingExpectation() {
        if (imaginedIslands == null) return;
        Boolean before = isExpectedToLeave;
        isExpectedToLeave = imaginedIslands.size() == 1 && imaginedIslands.iterator().next().inIslandDragons.get(id).isBlueEyed;
        if (before != null && before && !isExpectedToLeave) {
            System.out.println("err in updateLeavingExpectation");
        }
        if (isExpectedToLeave && (before == null || !before)) {
            totalExpectedToLeaveCount ++;
        }
        if (imaginedIslands != null) {
            for (ImaginedIsland imaginedIsland: imaginedIslands) {
                imaginedIsland.updateLeavingDragonsExpectation();
            }
        }
    }
}
