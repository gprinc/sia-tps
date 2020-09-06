public class InitializerJson {
    public final static String[] selectionMethods = {"elite", "roulette", "universal", "ranking", "boltzmann", "dTournament", "pTournament"};
    public final static String DEFAULT_METHOD = "roulette";
    public final static String[] possibleClasses = {"warrior", "archer", "defender", "infiltrate"};
    public final static String[] possibleMating = {"onePoint", "twoPoints", "anular", "uniform"};
    public final static String DEFAULT_MATING = "uniform";
    public final static String[] possibleImplementation = {"fillAll", "fillParent"};
    public final static String DEFAULT_IMPLEMENTATION = "fillAll";
    public final static String[] possibleMutation = {"gene", "limitedMultigene", "uniformMultigene", "complete"};
    public final static String DEFAULT_MUTATION = "gene";
    public final static String[] possibleCut = {"generations", "time", "accepted", "structure", "content"};
    public final static String DEFAULT_CUT = "generations";

    public static int giveRequiredInt(String intValue){
        if (intValue == null || Integer.parseInt(intValue) < 0)
            return 0;
        return Integer.parseInt(intValue);
    }

    public static double givePercentage(String stringValue){
        if (stringValue == null || Double.parseDouble(stringValue) < 0 || Double.parseDouble(stringValue) > 1)
            return -1;
        return Double.parseDouble(stringValue);
    }

    public static String giveSelectionMethod(String method) {
        if (method == null)
            return DEFAULT_METHOD;
        for (String s: selectionMethods) {
            if (s.equals(method))
                return method;
        }
        return DEFAULT_METHOD;
    }

    public static String giveClass(String dndClass) {
        if (dndClass == null)
            return null;
        for (String s: possibleClasses) {
            if (s.equals(dndClass))
                return dndClass;
        }
        return null;
    }

    public static String giveMating(String mating) {
        if (mating == null)
            return DEFAULT_MATING;
        for (String s: possibleMating) {
            if (s.equals(mating))
                return mating;
        }
        return DEFAULT_MATING;
    }

    public static String giveImplementation(String implementation) {
        if (implementation == null)
            return DEFAULT_IMPLEMENTATION;
        for (String s: possibleImplementation) {
            if (s.equals(implementation))
                return implementation;
        }
        return DEFAULT_IMPLEMENTATION;
    }

    public static String giveMutation(String mutation) {
        if (mutation == null)
            return DEFAULT_MUTATION;
        for (String s: possibleMutation) {
            if (s.equals(mutation))
                return mutation;
        }
        return DEFAULT_MUTATION;
    }

    public static String giveCut(String cut) {
        if (cut == null)
            return DEFAULT_CUT;
        for (String s: possibleCut) {
            if (s.equals(cut))
                return cut;
        }
        return DEFAULT_CUT;
    }

    public static int giveInt(String s, int def) {
        if (s == null || Integer.parseInt(s) < 0)
            return def;
        return Integer.parseInt(s);
    }

    public static double giveDouble(String s, double def) {
        if (s == null || Double.parseDouble(s) < 0)
            return def;
        return Double.parseDouble(s);
    }
}
