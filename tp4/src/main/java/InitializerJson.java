public class InitializerJson {
    private final static String DEFAULT_EJ = "Kohonen";
    public final static String[] possibleEjs = {"Kohonen", "Oja", "Hopfield"};
    public final static String[] possibleLetters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static String giveEj(String method) {
        if (method == null)
            return DEFAULT_EJ;
        for (String s: possibleEjs) {
            if (s.equals(method))
                return method;
        }
        return DEFAULT_EJ;
    }

    public static String giveLetter(String letter, String def) {
        if (letter == null)
            return def;
        for (String s: possibleLetters) {
            if (s.equals(letter.toLowerCase()))
                return letter.toLowerCase();
        }
        return def;
    }

    public static boolean giveBoolean(String s) {
        if (s == null || !s.equals("true") || !s.equals("false"))
            return false;
        return s.equals("true") ? true : false;
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
