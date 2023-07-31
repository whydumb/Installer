package appu26j.utils;

public class OSUtil
{
    private static final boolean windows = System.getProperty("os.name").toLowerCase().contains("win");

    public static boolean isOnWindows()
    {
        return windows;
    }
}
