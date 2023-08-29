package appu26j;

import appu26j.gui.screens.GUIInstaller;
import appu26j.utils.FileUtil;
import appu26j.utils.LauncherDetector;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Installer
{
    public static void install18(GUIInstaller guiInstaller)
    {
        new Thread(() ->
        {
            guiInstaller.status = "Downloading Apple Client";
            File appleClientZip = new File(LauncherDetector.getMinecraftLauncherPath(), "versions" + File.separator + "appleclient.zip");
            File appleClient = new File(LauncherDetector.getMinecraftLauncherPath(), "versions" + File.separator + "appleclient");
            boolean exists = appleClient.exists();
            boolean fileDownloaded = exists || downloadFile("https://github.com/AppleClient/AppleClient/releases/download/Apple-Client/appleclient.zip", appleClientZip);
            boolean fileExtracted = exists || FileUtil.unzip(appleClientZip, appleClient);

            if (!fileDownloaded || !fileExtracted)
            {
                String prevStatus = guiInstaller.status;
                guiInstaller.status = "Error " + String.valueOf(prevStatus.charAt(0)).toLowerCase() + prevStatus.substring(1);
                return;
            }

            if (appleClientZip.exists())
            {
                appleClientZip.delete();
            }

            boolean hasClient = true, temp = false;

            if (LauncherDetector.minecraftLauncherIsInstalled())
            {
                File launcherProfiles = new File(LauncherDetector.getMinecraftLauncherPath(), "launcher_profiles.json");
                ArrayList<String> lines1 = new ArrayList<>();
                ArrayList<String> lines2 = new ArrayList<>();

                try (FileReader fileReader = new FileReader(launcherProfiles); BufferedReader bufferedReader = new BufferedReader(fileReader))
                {
                    int i = 1;
                    String line;

                    while ((line = bufferedReader.readLine()) != null)
                    {
                        if (i == 1 || i == 0)
                        {
                            if (i == 0 && !line.contains(","))
                            {
                                lines1.add(line + ",");
                                temp = true;
                            }

                            else
                            {
                                lines1.add(line);
                            }

                            if (i == 0)
                            {
                                i = -1;
                            }

                            if (line.trim().equals("\"type\" : \"latest-release\""))
                            {
                                i = 0;
                            }
                        }

                        else
                        {
                            lines2.add(line);
                        }
                    }
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                hasClient = lines2.stream().filter(line -> line.toLowerCase().contains("apple") && line.toLowerCase().contains("client")).findFirst().orElse(null) != null;

                if (!hasClient)
                {
                    ArrayList<String> finalLines = new ArrayList<>(lines1);
                    String profile = "    \"appleclient\" : {\n" +
                            "      \"created\" : \"1970-01-01T00:00:00.000Z\",\n" +
                            "      \"icon\" : \"Ice_Packed\",\n" +
                            "      \"lastUsed\" : \"" + ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT) + "\",\n" +
                            "      \"lastVersionId\" : \"appleclient\",\n" +
                            "      \"name\" : \"Apple Client 1.8.9\",\n" +
                            "      \"type\" : \"custom\"\n" +
                            "    }";

                    finalLines.add(profile + (temp ? "" : ","));
                    finalLines.addAll(lines2);

                    try (FileWriter fileWriter = new FileWriter(launcherProfiles, false))
                    {
                        for (String line : finalLines)
                        {
                            fileWriter.write(line.equals("\n") ? line : (line + "\n"));
                        }
                    }

                    catch (Exception e)
                    {
                        ;
                    }
                }
            }

            if (exists && hasClient)
            {
                guiInstaller.status = "Apple Client is already installed!";
            }

            else
            {
                guiInstaller.status = "Done!";
            }
        }).start();
    }

    public static void install17(GUIInstaller guiInstaller)
    {
        new Thread(() ->
        {
            guiInstaller.status = "Downloading Apple Client";
            File appleClientZip = new File(LauncherDetector.getMinecraftLauncherPath(), "versions" + File.separator + "appleclient_1.7.zip");
            File appleClient = new File(LauncherDetector.getMinecraftLauncherPath(), "versions" + File.separator + "appleclient_1.7");
            boolean exists = appleClient.exists();
            boolean fileDownloaded = exists || downloadFile("https://github.com/AppleClient/AppleClient/releases/download/Apple-Client/appleclient.zip", appleClientZip);
            boolean fileExtracted = exists || FileUtil.unzip(appleClientZip, appleClient);

            if (!fileDownloaded || !fileExtracted)
            {
                String prevStatus = guiInstaller.status;
                guiInstaller.status = "Error " + String.valueOf(prevStatus.charAt(0)).toLowerCase() + prevStatus.substring(1);
                return;
            }

            if (appleClientZip.exists())
            {
                appleClientZip.delete();
            }

            boolean hasClient = true, temp = false;

            if (LauncherDetector.minecraftLauncherIsInstalled())
            {
                File launcherProfiles = new File(LauncherDetector.getMinecraftLauncherPath(), "launcher_profiles.json");
                ArrayList<String> lines1 = new ArrayList<>();
                ArrayList<String> lines2 = new ArrayList<>();

                try (FileReader fileReader = new FileReader(launcherProfiles); BufferedReader bufferedReader = new BufferedReader(fileReader))
                {
                    int i = 1;
                    String line;

                    while ((line = bufferedReader.readLine()) != null)
                    {
                        if (i == 1 || i == 0)
                        {
                            if (i == 0 && !line.contains(","))
                            {
                                lines1.add(line + ",");
                                temp = true;
                            }

                            else
                            {
                                lines1.add(line);
                            }

                            if (i == 0)
                            {
                                i = -1;
                            }

                            if (line.trim().equals("\"type\" : \"latest-release\""))
                            {
                                i = 0;
                            }
                        }

                        else
                        {
                            lines2.add(line);
                        }
                    }
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                hasClient = lines2.stream().filter(line -> line.toLowerCase().contains("appleclient_1.7")).findFirst().orElse(null) != null;

                if (!hasClient)
                {
                    ArrayList<String> finalLines = new ArrayList<>(lines1);
                    String profile = "    \"appleclient_1.7\" : {\n" +
                            "      \"created\" : \"1970-01-01T00:00:00.000Z\",\n" +
                            "      \"icon\" : \"Ice_Packed\",\n" +
                            "      \"lastUsed\" : \"" + ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT) + "\",\n" +
                            "      \"lastVersionId\" : \"appleclient_1.7\",\n" +
                            "      \"name\" : \"Apple Client 1.7\",\n" +
                            "      \"type\" : \"custom\"\n" +
                            "    }";

                    finalLines.add(profile + (temp ? "" : ","));
                    finalLines.addAll(lines2);

                    try (FileWriter fileWriter = new FileWriter(launcherProfiles, false))
                    {
                        for (String line : finalLines)
                        {
                            fileWriter.write(line.equals("\n") ? line : (line + "\n"));
                        }
                    }

                    catch (Exception e)
                    {
                        ;
                    }
                }
            }

            if (exists && hasClient)
            {
                guiInstaller.status = "Apple Client is already installed!";
            }

            else
            {
                guiInstaller.status = "Done!";
            }
        }).start();
    }

    public static boolean downloadFile(String website, File output)
    {
        try
        {
            if (!output.exists())
            {
                if (!output.getParentFile().exists())
                {
                    output.getParentFile().mkdirs();
                }

                output.createNewFile();
            }

            URL url = new URL(website);
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(output);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
            readableByteChannel.close();
            return true;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
