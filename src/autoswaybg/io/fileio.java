package autoswaybg.io;

import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Files;

public class fileio {
  public static String[] readLines(String path) {
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

  public static void writeFile(String path, String[] lines) {
    var grouped = new StringBuilder();
    for (String line : lines) {grouped.append(line).append('\n');}
    String file_str = grouped.toString();
    //todo finish function
  }
  
  private static byte[] readFile(String path) {
    Path p = Path.of(path);
    try {return Files.readAllBytes(p);}
    catch (IOException e) {stdout.error("Failed to read file at path "+path+"\nMake sure you have read access to this file"); return null;}
  }
}
