
public class OptimizedMain {

	public static void main(String[] args) {
		OptimizedIsland island = new OptimizedIsland(100);

		System.out.println("day 0");

		for (int i=1; i< 1000; i++) {
			System.out.println("day " + i);
			island.awareBlueEyedPersonsLeave();
			island.updatePossibleIslands();
			if (island.blueEyedLeft) {
				System.out.println("all persons left on day " + i);
				break;
			}
		}
	}


}
