public class RealPerson extends Person {
    static int instanceCount = 0;
    public RealPerson(RealIsland realIsland, String pId) {
        super(true, pId, realIsland);
        if (instanceCount >= island.numberOfDragons) {
            System.out.println("shouldn't have more than " + island.numberOfDragons + " real dragons");
        }
    }

    public boolean isAwareOfHavingBlueEyes() {
        return imaginedIslands.size() == 1 && imaginedIslands.iterator().next().inIslandDragons.get(id).isBlueEyed;
    }
}
