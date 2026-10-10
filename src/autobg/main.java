package autobg;

import autobg.io.stdout;
import autobg.cli.*;

public class main {
  public static void main(String[] args) {
    init(args);
    if (cli.hasNoArguments()) {help.printSmallHelp(); return;}
    if (cli.getHelp()) {help.printHelp(); return;}
    wallpaper.setWallpaper();
  }

  //Defines static variables throughout different classes
  private static void init(String[] args) {
    cli.setArgs(args);
    stdout.setPrintLevel();
  }
}
