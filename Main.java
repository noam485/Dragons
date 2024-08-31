
public class Main {

	public static void main(String[] args) {
		System.out.println("non-optimized:");
		new IslandPuzzleSolver(false, 4).solve();
		System.out.println("optimized:");
		new IslandPuzzleSolver(true, 10).solve();
	}


}
