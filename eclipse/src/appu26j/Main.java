package appu26j;

import appu26j.utils.OSUtil;

import java.io.File;
import java.lang.management.ManagementFactory;

public class Main
{
	public static void main(String[] arguments)
	{
		String allArgs = getArgs(arguments);

		if (!OSUtil.isOnWindows() && !allArgs.contains("-XstartOnFirstThread"))
		{
			restartItself(allArgs);
			System.exit(0);
		}

		AppleClientInstaller.INSTANCE.start();
	}

	private static void restartItself(String args)
	{
		try
		{
			args = args.replaceFirst("::", "::-XstartOnFirstThread::");
			String[] argsArray = args.split("::");
			Runtime.getRuntime().exec(argsArray);
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String getArgs(String[] args)
	{
		String separator = "::";
		StringBuilder cmd = new StringBuilder();
		cmd.append("\"").append(System.getProperty("java.home")).append(File.separator).append("bin").append(File.separator).append("java").append("\"").append(separator);

		for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments())
		{
			boolean temp = jvmArg.startsWith("-Djava.library.path=");

			if (temp)
			{
				cmd.append("-Djava.library.path=").append("\"").append(jvmArg.replace("-Djava.library.path=", "")).append("\"").append(separator);
			}

			else
			{
				cmd.append(jvmArg).append(separator);
			}
		}

		cmd.append("-cp").append(separator).append("\"").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append("\"").append(separator);
		cmd.append(Main.class.getName()).append(separator);

		for (String arg : args)
		{
			cmd.append(arg).append(separator);
		}

		cmd.setLength(cmd.length() - 2);
		return cmd.toString();
	}
}
