package autobg.io;

import java.io.IOException;

//Class for running external processes, in this case swaybg and pkill
public class process {
  public static boolean swaybg_setWallpaper(String wallpaperPath) {
    exec(true, "pkill", "-x", "swaybg");
    Process p = exec(false, "swaybg", "-i", wallpaperPath);
    return p != null;
  }
  
  private static Process exec(boolean awaitCompletion, String... args) {
    if (stdout.isDebugMode()) {stdout.printSeq("Executing process with arguments:", args);}
    try {
      var pb = new ProcessBuilder(args);
      Process p = pb.start();
      if (awaitCompletion) p.waitFor();
      return p;
    }
    catch (IOException e) {stdout.error("Error running process "+args[0]+"\nThe respective program might not be present in your system"); return null;}
    catch (InterruptedException e) {stdout.error("Error running process"+args[0]+"\nUnexpected program interruption!"); return null;}
  }
}
