package autoswaybg.cli;

import autoswaybg.io.stdout;

//The program's help screens
public class help {
  private static final String version = "0.1";
  
  public static void printHelp() {
    String text =
      "autoswaybg version " + version
      +"\nUsage: autoswaybg <options> <path to image>"

      +"\n\n[Examples]"
      +"\nSet a wallpaper and save it in Wayfire: autoswaybg /path/to/image.png"
      +"\nCycle between wallpapers inside a path every 30 seconds: autoswaybg -t 30 /path/to/wallpaper-directory/"
  
      +"\n\n[Available options]"
      +"\n  * -h (--help)            prints this screen"
      +"\n  * -t <seconds>           how many seconds to change to the next wallpaper"
      +"\n  * -nc (--no-config)      do not update Wayfire's config"
      +"\n  * -c (--config) <path>   specify a custom path to the Wayfire INI config"
      +"\n  * -v (--verbose)         prints more runtime information"
      +"\n  * -d (--debug)           prints all runtime information"
      +"\n  * -q (--quiet)           does not print any message or error"
    ;
    stdout.print(text);
  }
  public static void printSmallHelp() {
    String text =
      "autoswaybg version " + version
      +"\nUsage: autoswaybg <options> <path to image>"
      +"\nRun \"autoswaybg -h\" or \"autoswaybg --help\" to see the full list of options"
    ;
    stdout.print(text);
  }
}
