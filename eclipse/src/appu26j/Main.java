package appu26j;

import appu26j.utils.OSUtil;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;

public class Main
{
	public static void main(String[] arguments)
	{
		if (!OSUtil.isOnWindows() && restartJVM(arguments))
		{
			System.exit(0);
		}

		AppleClientInstaller.INSTANCE.start();
	}

	public static boolean restartJVM(String[] args)
	{
		// get current jvm process pid
		String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		// get environment variable on whether XstartOnFirstThread is enabled
		String env = System.getenv("JAVA_STARTED_ON_FIRST_THREAD_" + pid);

		// if environment variable is "1" then XstartOnFirstThread is enabled
		if ("1".equals(env)) {
			return false;
		}

		// restart jvm with -XstartOnFirstThread
		String separator = System.getProperty("file.separator");
		String classpath = System.getProperty("java.class.path");
		String mainClass = System.getenv("JAVA_MAIN_CLASS_" + pid);
		String jvmPath = System.getProperty("java.home") + separator + "bin" + separator + "java";

		ArrayList<String> jvmArgs = new ArrayList<String>(128);
		jvmArgs.add(jvmPath);
		jvmArgs.add("-XstartOnFirstThread");
		jvmArgs.addAll(ManagementFactory.getRuntimeMXBean().getInputArguments());  // <-- input arguments!
		jvmArgs.add("-cp");
		jvmArgs.add(classpath);
		jvmArgs.add(mainClass);
		jvmArgs.addAll(Arrays.asList(args));

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(jvmArgs);
			processBuilder.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
}
