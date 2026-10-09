package autoswaybg;

import autoswaybg.cli.cli;
import autoswaybg.io.stdout;
import autoswaybg.io.process;
import java.util.ArrayList;
import java.io.File;

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
    if (isDir) {
      setWallpaperDirectory(wallpaperPath);
      return;
    }
    if (!supportedFormat(wallpaperPath)) {
      stdout.error(
        "The image format for the wallpaper at "+wallpaperPath+" is unsupported!"
        +"\nSupported values: png, jpg, gif, tga, tiff"
      );
      return;
    }
    process.swaybg_setWallpaper(wallpaperPath);
  }

  private static void setWallpaperDirectory(String path) {
    var wallpapers = new ArrayList<String>();
  }
  
  private static boolean supportedFormat(String path) {
    int start_i = -1; //Where the file extension starts
    for (int i = path.length()-1; i >= 0; i--) {
      if (path.charAt(i) == '.') {start_i = i; break;}
    }
    if (start_i == -1) return false;

    var extension = new StringBuilder();
    for (int i = start_i; i < path.length(); i++) {extension.append(path.charAt(i));}
    String extension_str = extension.toString().toLowerCase();

    for (String format : new String[]{".png", ".jpg", ".jpeg", ".gif", ".tga", ".tiff"}) {if (format.equals(extension_str)) return true;}
    return false;
  }
}
