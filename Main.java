
public class Main {

	public static void main(String[] args) {
		
		for (int i=1; i<=9; i++) {
			simulateIsland(i);
		}
		
	}
	
	static void simulateIsland(int numOfDragons) {
		Island island = new Island(numOfDragons);
		System.out.println("island.getBeliefsDepth() = " + island.getBeliefsDepth());
		System.out.println("island.getMyEyesAreBlueBeliefs().size() = " + island.getMyEyesAreBlueBeliefs().size());

		
		int daysPassed=0;
		
		for (; daysPassed<numOfDragons * 2 && !island.dragons.isEmpty(); daysPassed++) {
//			System.out.println("island.getMyEyesAreBlueBeliefs().size() = " + island.getMyEyesAreBlueBeliefs().size());
//			System.out.println("island.getBeliefsDepth() = " + island.getBeliefsDepth());
			island.nextDay();
		}
		
		System.out.println(daysPassed);
	}
	


}
