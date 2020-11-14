public class InitializerJson {
    private final static String DEFAULT_EJ = "1";
    public final static String[] possibleEjs = {"1", "1-noise", "2"};

    public static String giveEj(String method) {
        if (method == null)
            return DEFAULT_EJ;
        for (String s: possibleEjs) {
            if (s.equals(method))
                return method;
        }
        return DEFAULT_EJ;
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
