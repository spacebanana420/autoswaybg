package autoswaybg;

import autoswaybg.cli.cli;
import autoswaybg.io.stdout;
import autoswaybg.io.process;
import java.util.ArrayList;
import java.io.File;

//Class that can set a single wallpaper or cycle between many in a path
public class wallpaper {
  public static void setWallpaper() {
    String wallpaperPath = cli.getWallpaperPath();
    if (wallpaperPath == null) {
      stdout.print("No path to an image file or directory was provided, ignoring.");
      return;
    }
    
    File f = new File(wallpaperPath);
    boolean isFile = f.isFile();
    boolean isDir = f.isDirectory();
    if (!isFile && !isDir) {
      stdout.error("The wallpaper path "+wallpaperPath+" does not lead to a file or directory!");
      return;
    }
    if (isDir) setWallpaperDirectory(wallpaperPath);
    else setWallpaper(wallpaperPath, true);
  }

  //Set a single image as wallpaper
  private static void setWallpaper(String path, boolean checkFormat) {
    if (checkFormat || supportedFormat(path)) {
      stdout.print("Setting wallpaper at path "+path);
      process.swaybg_setWallpaper(path);
      return;
    }
    stdout.error(
       "The image format for the wallpaper at "+path+" is unsupported!"
      +"\nSupported values: png, jpg, gif, tga, tiff"
    );
  }

  //Parse the files of a directory, the ones that are valid images are set as wallpapers
  private static void setWallpaperDirectory(String path) {
    var wallpapers = getWallpapersFromPath(path);
    int time = cli.getWallpaperTime();
    if (time == -1) {
      stdout.print("Using default time of 30 seconds.");
      time = 30000;
    }
    else {
      stdout.print("Cycling between wallpapers every "+time+" seconds.");
      time = time * 1000;
    }
    while (true) {
      for (String wallpaper : wallpapers) {
        setWallpaper(wallpaper, false);
        try {Thread.sleep(time);}
        catch (InterruptedException e) {stdout.error("Process was unexpectedly interrupted.");}
      }
    }
  }

  //swaybg supports PNG out of the box, but with potential to support the other formats specified below if pixbuf is installed
  private static boolean supportedFormat(String path) {
    int start_i = -1; //Where the file extension starts
    for (int i = path.length()-1; i >= 0; i--) { //Find the start of the extension in the file name by looking for "."
      if (path.charAt(i) == '.') {start_i = i; break;}
    }
    if (start_i == -1) return false;

    var extension = new StringBuilder();
    for (int i = start_i; i < path.length(); i++) {extension.append(path.charAt(i));}
    String extension_str = extension.toString().toLowerCase();

    for (String format : new String[]{".png", ".jpg", ".jpeg", ".gif", ".tga", ".tiff"}) {if (format.equals(extension_str)) return true;}
    return false;
  }

  //Retrieves the files from a path that count as valid images that swaybg can read
  private static ArrayList<String> getWallpapersFromPath(String path) {
    var images = new ArrayList<String>();
    String[] subpaths = new File(path).list();
    for (String file : subpaths) {
      File f = new File(path+"/"+file);
      if (!f.isFile()) continue;
      if (!supportedFormat(file)) continue;
      
      String fullPath = f.getAbsolutePath();
      images.add(fullPath);
      stdout.print_verbose("Retrieved image file "+fullPath);
    }
    return images;
  }
}
