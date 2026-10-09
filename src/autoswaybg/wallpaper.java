package autoswaybg;

import autoswaybg.io.stdout;
import java.io.File;

public class wallpaper {
  public static void setWallpaper(String wallpaperPath) {
    File f = new File(wallpaperPath);
    boolean isFile = f.isFile();
    boolean isDir = f.isDirectory();
    if (!isFile && !isDir) {
      stdout.error("The wallpaper path "+wallpaperPath+" does not lead to a file or directory!");
      return;
    }
    if (isFile && !supportedFormat(wallpaperPath)) {
      stdout.error(
        "The image format for the wallpaper at "+wallpaperPath+" is unsupported!"
        +"\nSupported values: png, jpg, gif, tga, tiff"
      );
      return;
    }
  }
  private static boolean supportedFormat(String path) {
    int start_i = -1; //Where the file extension starts
    for (int i = path.length(); i >= 0; i--) {
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
