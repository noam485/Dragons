class IslandPuzzleSolver {
    final boolean isOptimized;
    final int numOfPersons;
    IslandPuzzleSolver(boolean pIsOptimized, int pNumOfPersons) {
        this.isOptimized = pIsOptimized;
        numOfPersons = pNumOfPersons;
    }

    void solve() {
        if (isOptimized) {
            OptimizedIsland island = new OptimizedIsland(numOfPersons);

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
        } else {
            Island island = new Island(numOfPersons);

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
}
