public class RealPerson extends Person {
    static int instanceCount = 0;
    public RealPerson(RealIsland realIsland, String pId) {
        super(true, pId, realIsland);
        if (instanceCount >= residenceIsland.numberOfPersons) {
            System.out.println("shouldn't have more than " + residenceIsland.numberOfPersons + " real dragons");
        }
    }

    public boolean isAwareOfHavingBlueEyes() {
        return imaginedIslands.size() == 1 && imaginedIslands.iterator().next().inIslandPersons.get(id).isBlueEyed;
    }
}
