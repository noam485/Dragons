
public class Main {

	public static void main(String[] args) throws Exception {
		Island island = new Island(4);
		for (int i=1; i< 10; i++) {
			System.out.println("day " + i);
			island.awareBlueEyedPersonsLeave();
			island.updatePossibleImaginedIslands();
			System.out.println(island.inIslandPersons.size() + " / " + island.outOfIslandPersons.size() + ". depth: " + island.depth());
			if (island.inIslandPersons.size() == 0) {
				System.out.println("all persons left on day " + i);
				break;
			}
		}
	}


}
