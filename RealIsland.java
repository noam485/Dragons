import java.util.HashSet;

public class RealIsland extends Island {
    static int instanceCount = 0;

    RealIsland(int dragonsCount) {
        super(dragonsCount);
        if (instanceCount > 0) {
            System.out.println("Shouldn't create more than 1 real islands");
        }
        for (int i=1; i<=dragonsCount; i++) {
            RealDragon realDragon = new RealDragon(this, "Dragon_" + i);
            inIslandDragons.put(realDragon.id, realDragon);
        }
        for (Dragon dragon: inIslandDragons.values()) {
            dragon.createImaginedPossibleIslands();
        }
    }

    @Override
    boolean isReal() {
        return true;
    }

    @Override
    public void updateLeavingDragonsExpectation() {
        for (Dragon dragon: inIslandDragons.values()) {
            dragon.updateLeavingDragonsExpectation();
        }
    }

    public void awareBlueEyedRealDragonsLeave() {
        HashSet<Dragon> leavingDragons = new HashSet<>();
        for (Dragon dragon: inIslandDragons.values()) {
            if (((RealDragon)dragon).isAwareOfHavingBlueEyes()) {
                leavingDragons.add(dragon);
            }
        }
        for (Dragon dragon: leavingDragons) {
            inIslandDragons.remove(dragon.id);
            outOfIslandDragons.put(dragon.id, dragon);
        }
    }
}
