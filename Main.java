
public class Main {

	public static void main(String[] args) {
		RealIsland realIsland = new RealIsland(4);
		for (int i=1; i< 10; i++) {
			System.out.println("day " + i);
			realIsland.updateLeavingPersonsExpectation();
			realIsland.awareBlueEyedPersonsLeave();
			realIsland.updatePossibleImaginedIslands();
			System.out.println(realIsland.inIslandPersons.size() + " / " + realIsland.outOfIslandDragons.size() + ". depth: " + realIsland.depth());
			if (realIsland.inIslandPersons.size() == 0) {
				System.out.println("all persons left in day " + i);
				break;
			}
		}
	}


}
