import java.util.HashSet;

class Belief {
	Dragon believer;
	MyEyesAreBlue myEyesAreBlue;
	HashSet<Belief> ifMyEyesAreNotBlueThen = new HashSet<Belief>();
	Belief inferredFrom;

	public Belief(Dragon pBeliever, MyEyesAreBlue pMyEyesAreBlue) {
		believer = pBeliever;
		myEyesAreBlue = pMyEyesAreBlue;
	}

	public HashSet<Belief> getMyEyesAreBlueBeliefs() {
		HashSet<Belief> retVal = new HashSet<Belief>();
		if (myEyesAreBlue == MyEyesAreBlue.yes) {
			retVal.add(this);
			return retVal;
		}
		for (Belief belief: ifMyEyesAreNotBlueThen) {
			retVal.addAll(belief.getMyEyesAreBlueBeliefs());
		}
		return retVal;
	}

	Integer getDepth() {
		if (inferredFrom == null) {
			return 1;
		}
		return 1 + inferredFrom.getDepth();
	}
}

enum MyEyesAreBlue{
	yes, dontKnow
}