public class ImaginedDragon extends Dragon {
    static int totalExpectedToLeaveCount = 0;
    final Dragon imaginingDragon;
    Boolean isExpectedToLeave;
    public ImaginedDragon(boolean isBlueEyed, String id, Dragon pImaginingDragon, Island pIsland) {
        super(isBlueEyed, id, pIsland);
        imaginingDragon = pImaginingDragon;
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
