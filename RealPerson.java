public class RealPerson extends Person {
    static int instanceCount = 0;
    public RealPerson(RealIsland realIsland, String pId) throws Exception {
        super(true, pId, realIsland);
        if (instanceCount >= residenceIsland.numberOfPersons) {
            throw new Exception("shouldn't have more than " + residenceIsland.numberOfPersons + " real persons");
        }
    }


}
