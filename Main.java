
public class Main {

	public static void main(String[] args) {
		RealIsland realIsland = new RealIsland(4);
		for (int i=1; i< 10; i++) {
			System.out.println("day " + i);
			realIsland.updateLeavingDragonsExpectation();
			realIsland.awareBlueEyedRealDragonsLeave();
			realIsland.updatePossibleImaginedIslands();
			System.out.println(realIsland.inIslandDragons.size() + " / " + realIsland.outOfIslandDragons.size() + ". depth: " + realIsland.depth());
		}
	}


}
