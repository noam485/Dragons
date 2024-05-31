import java.util.HashMap;
import java.util.HashSet;

public class OptimizedIsland {
    int blueEyedCount;
    int brownEyedCount;
    boolean blueEyedLeft = false;

    OptimizedPerson blueEyed = new OptimizedPerson(true, "", this);
    OptimizedPerson brownEyed = new OptimizedPerson(false, "", this);

    OptimizedPerson imaginingPerson; // the person that sees this as a possible island

    public OptimizedIsland(int pBlueEyedCount, int pBrownEyedCount, OptimizedPerson pImaginingPerson) {
        blueEyedCount = pBlueEyedCount;
        brownEyedCount = pBrownEyedCount;
        imaginingPerson = pImaginingPerson;
    }

    OptimizedIsland(int pNumberOfPersons) {
        blueEyedCount = pNumberOfPersons;
        brownEyedCount = 0;

        generatePossibleIslands();
    }

    public boolean isAlreadyGeneratedBefore() {
        OptimizedIsland island = imaginingPerson.residenceIsland;
        while (island.imaginingPerson != null) {
            if (island.blueEyedCount == this.blueEyedCount && island.brownEyedCount == this.brownEyedCount) {
                return true;
            }
            island = (island).imaginingPerson.residenceIsland;
        }
        return false;
    }

    public void generatePossibleIslands() {
        for (OptimizedPerson person : new OptimizedPerson[] { blueEyed, brownEyed }) {
            person.generatePossibleIslands();
        }
    }

    public void updatePossibleIslands() {
        for (OptimizedPerson person : new OptimizedPerson[] { blueEyed, brownEyed }) {
            person.removeImpossibleIslands();
        }
    }

    public void awareBlueEyedPersonsLeave() {
        if (blueEyed.isAwareOfHavingBlueEyes()) {
            blueEyedLeft = true;
        }
    }

    @Override
    public String toString() {
        return blueEyedCount + " " + brownEyedCount;
    }

}
