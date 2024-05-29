
public class Main {

	public static void main(String[] args) {
		Island island = new Island(4);

		System.out.println("day 0");
		System.out.println(island.inIslandPersons.size() + " / " + island.outOfIslandPersons.size() + ". depth: " + island.depth());
		System.out.println(island.getLongestPath());
		for (int i=1; i< 10; i++) {
			System.out.println("day " + i);
			island.awareBlueEyedPersonsLeave();
			island.updatePossibleIslands();
			System.out.println(island.inIslandPersons.size() + " / " + island.outOfIslandPersons.size() + ". depth: " + island.depth());
			System.out.println(island.getLongestPath());
			if (island.inIslandPersons.size() == 0) {
				System.out.println("all persons left on day " + i);
				break;
			}
		}
	}


}
