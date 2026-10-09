package autoswaybg.io;

import autoswaybg.cli.cli;

import java.io.IOException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Files;

public class fileio {
  //Read the wayfire INI config, find the autostart option that has the swaybg or autoswaybg command, replace it with one that uses the new wallpaper
  public static void updateWayfireConfig(String command) {
    if (cli.disableConfigUpdate()) {stdout.print_verbose("Wayfire config update was disabled, ignoring config file"); return;}

    String configPath = cli.getConfigPath();
    if (configPath == null) configPath = System.getProperty("user.home")+"/.config/wayfire.ini"; //Also needs checking if file exists
    stdout.print("Updating Wayfire config at "+configPath);
    stdout.print_debug("Using autostart wallpaper command "+command);

    String[] config = readLines(configPath);
    int config_start = -1; //Right after the [autostart] line in Wayfire
    int config_end = config.length; //When [autostart] ends
    for (int i = 0; i < config.length; i++) { //Limit the lines to parse, only the config inside [autostart] matters for Wayfire
      String line = config[i].trim();
      if (config_start == -1 && line.toLowerCase().equals("[autostart]")) config_start = i+1;
      else if (line.length() >= 2 && line.charAt(0) == '[' && line.charAt(line.length()-1) == ']') config_end = i; //Check for the next category in Wayfire config, for example [core]
    }

    boolean foundSetting = false;
    for (int i = config_start; i < config_end; i++) { //Find the autostart setting that runs swaybg or autoswaybg
      if (config[i].contains("swaybg") || config[i].contains("autoswaybg")) {
        String newLine = wayfire_replaceLine(config[i], command);
        stdout.print_debug("Replacing line in Wayfire config\n * Before: "+config[i]+"\n * Now: "+newLine);
        config[i] = newLine;
        foundSetting = true;
        break;
      }
    }
    if (!foundSetting) { //The Wayfire INI didn't have any wallpaper command on autostart yet, add one
      config[config_start] = "wallpaper = "+command+"\n"+config[config_start];
    }
    writeFile(configPath, config);
  }

  //Replace the value of a line, for example changing "setting = a" to "setting = b"
  private static String wayfire_replaceLine(String line, String cmd) {
    var name = new StringBuilder(); //The name of the setting is preserved
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (c == '=') break;
      name.append(c);
    }
    return name.toString().trim() + " = " + cmd; //Creates the new setting, for example "wallpaper = swaybg -i image.png"
  }

  //Read the file's contents where each string is a line
  private static String[] readLines(String path) {
    byte[] file = readFile(path);
    if (file == null) return null;
    var lines = new ArrayList<String>();
    var line = new StringBuilder();
    for (int i = 0; i < file.length; i++) {
      char c = (char)file[i];
      if (c == '\n') {
        if (line.length() > 0) {lines.add(line.toString()); line = new StringBuilder();}
        continue;
      }
      line.append(c);
    }
    if (line.length() > 0) lines.add(line.toString());
    return lines.toArray(new String[0]);
  }

  private static void writeFile(String path, String[] lines) {
    var grouped = new StringBuilder();
    for (String line : lines) {grouped.append(line).append('\n');}
    String file_str = grouped.toString();
    
    try {
      stdout.print_debug("Writing file to "+path+"\nNumber of lines: "+lines.length+"\nNumber of characters: "+file_str.length());
      new FileOutputStream(path).write(file_str.getBytes());
    }
    catch (IOException e) {stdout.error("Failed to write the file at path "+path+"\nMake sure you have write access to this file.");}
  }
  
  private static byte[] readFile(String path) {
    Path p = Path.of(path);
    try {return Files.readAllBytes(p);}
    catch (IOException e) {stdout.error("Failed to read file at path "+path+"\nMake sure you have read access to this file."); return null;}
  }
}
