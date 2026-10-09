package autoswaybg;

import autoswaybg.io.stdout;
import autoswaybg.cli.*;

public class main {
  public static void main(String[] args) {
    init(args);
    if (cli.hasNoArguments()) return;
  }

  //Defines static variables throughout different classes
  private static void init(String[] args) {
    cli.setArgs(args);
    stdout.setPrintLevel();
  }
}
