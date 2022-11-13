import java.util.HashSet;

public class Island {
	HashSet<Dragon> dragons = new HashSet<Dragon>();
	
	Island(int pNumberOfDragons) {
		for (int i=0; i<pNumberOfDragons; i++) {
			Dragon dragon = new Dragon();
			dragons.add(dragon);
		}
		for (Dragon dragon: dragons) {
			dragon.belief = createBelief(dragon, new HashSet<Dragon>());	
		}
	}
	
	void nextDay() {
		HashSet<Dragon> leavingDragons = new HashSet<Dragon>();
		for (Dragon dragon : dragons) {
			if (dragon.belief.myEyesAreBlue == MyEyesAreBlue.yes) {
				leavingDragons.add(dragon);
			}
		}
		dragons.removeAll(leavingDragons);
		HashSet<Belief> myEyesAreBlueBeliefs = getMyEyesAreBlueBeliefs();
		for (Belief belief: myEyesAreBlueBeliefs) {
			if (belief.inferredFrom != null) {
				belief.inferredFrom.myEyesAreBlue = MyEyesAreBlue.yes;
				belief.inferredFrom.ifMyEyesAreNotBlueThen.clear();				
			}
		}
	}
	
	private Belief createBelief(Dragon pDragon, HashSet<Dragon> pAssumedNotBlue) {
		HashSet<Dragon> potentialBlue = new HashSet<Dragon>();
		for (Dragon dragon : dragons) {
			if (!pAssumedNotBlue.contains(dragon)) {
				potentialBlue.add(dragon);
			}
		}
		if (potentialBlue.size()==1 && potentialBlue.contains(pDragon)) {
			return new Belief(pDragon, MyEyesAreBlue.yes);
		}
		Belief belief = new Belief(pDragon, MyEyesAreBlue.dontKnow);
		HashSet<Dragon> assumedNotBlue = new HashSet<Dragon>(pAssumedNotBlue);
		assumedNotBlue.add(pDragon);
		potentialBlue.remove(pDragon);
		for (Dragon dragon: potentialBlue) {
			Belief inferredBelief = createBelief(dragon, assumedNotBlue);
			inferredBelief.inferredFrom = belief;
			belief.ifMyEyesAreNotBlueThen.add(inferredBelief);
		}
		return belief;
	}
	
	HashSet<Integer> getBeliefsDepth() {
		HashSet<Integer> depths = new HashSet<Integer>();
		for (Belief belief: getMyEyesAreBlueBeliefs()) {
			depths.add(belief.getDepth());
		}
		return depths;
	}
	
	HashSet<Belief> getMyEyesAreBlueBeliefs() {
		HashSet<Belief> retVal = new HashSet<Belief>();
		for (Dragon dragon : dragons) {
			retVal.addAll(dragon.getMyEyesAreBlueBeliefs());
		}
		return retVal;
	}

}
