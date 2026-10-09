package autoswaybg.cli;

import autoswaybg.io.stdout;

//CLI parsing class
public class cli {
  private static String[] args; //The CLI arguments assigned by main()

  public static void setArgs(String[] cliArgs) {args = cliArgs;}

  public static boolean hasNoArguments() {return args.length == 0;}
  public static boolean getHelp() {return argumentExists("-h", "--help");}
  public static boolean verboseOutput() {return argumentExists("-v", "--verbose");}
  public static boolean debugOutput() {return argumentExists("-d", "--debug");}
  public static boolean quietOutput() {return argumentExists("-q", "--quiet");}
  public static boolean disableConfigUpdate() {return argumentExists("-nc", "--no-config");}

  public static int getWallpaperTime() {return getArgumentInt(false, "-t", "--time");}
  public static String getConfigPath() {return getArgumentValue("-c", "--config");}
  public static String getWallpaperPath() {
    String firstArgument = args[0];
    String lastArgument = args.length > 1 ? args[args.length-1] : null;
    if (firstArgument.charAt(0) != '-') return firstArgument;
    if (lastArgument != null && lastArgument.charAt(0) != '-') return lastArgument;
    return null;
  }

  private static int getArgumentIndex(String... searchArgs) {
    for (String searchArg : searchArgs) {
      for (int i = 0; i < args.length; i++) {if (args[i].equals(searchArg)) return i;}
    }
    return -1;
  }
  private static boolean argumentExists(String... searchArgs) {return getArgumentIndex(searchArgs) != -1;}
  private static String getArgumentValue(String... searchArgs) {
    int i = getArgumentIndex(searchArgs);
    if (i == -1) return null;

    String arg = args[i];
    if (i == args.length) {
      stdout.error("The argument "+arg+" must precede a value!");
      return null;
    }
    String value = args[i+1];
    if (value.charAt(0) == '-') {
      stdout.error("Invalid value "+value+" for argument "+arg);
      return null;
    }
    return value;
  }
  private static int getArgumentInt(boolean rejectNegativeNumbers, String... searchArgs) {
    String value = getArgumentValue(searchArgs);
    if (value == null) return -1;
    try {
      int value_i = Integer.parseInt(value);
      if (rejectNegativeNumbers && value_i < 0) {
        stdout.error("The specified numerical value "+value_i+" is invalid because it's negative!");
        return -1;
      }
      return value_i;
    }
    catch (NumberFormatException e) {stdout.error("Invalid argument value "+value+" does not represent a valid numerical value!"); return -1;}
  }
}
