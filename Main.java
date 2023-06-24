
public class Main {

	public static void main(String[] args) {
		RealIsland realIsland = new RealIsland(4);
		for (int i=0; i< 10; i++) {
			realIsland.updateLeavingDragonsExpectation();
			realIsland.awareBlueEyedRealDragonsLeave();
			realIsland.updatePossibleImaginedIslands();
			System.out.println(realIsland.inIslandDragons.size() + " / " + realIsland.outOfIslandDragons.size());
		}
	}


}
