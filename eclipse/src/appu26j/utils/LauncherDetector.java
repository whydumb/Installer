package appu26j.utils;

import java.io.File;

public class LauncherDetector
{
    private static final File minecraftLauncherWindows = new File(System.getenv("APPDATA"), ".minecraft");
    private static final File minecraftLauncherMacOS = new File(System.getProperty("user.home") + "/Library/Application Support", "minecraft");
    private static final File minecraftLauncherWindowsCheck = new File(minecraftLauncherWindows, "launcher_profiles.json");
    private static final File minecraftLauncherMacOSCheck = new File(minecraftLauncherMacOS, "launcher_profiles.json");

    public static boolean minecraftLauncherIsInstalled()
    {
        return OSUtil.isOnWindows() ? minecraftLauncherWindowsCheck.exists() : minecraftLauncherMacOSCheck.exists();
    }

    public static File getMinecraftLauncherPath()
    {
        return OSUtil.isOnWindows() ? minecraftLauncherWindows : minecraftLauncherMacOS;
    }
}
