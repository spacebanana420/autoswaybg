package autoswaybg.io;

import autoswaybg.cli.cli;
import java.util.ArrayList;

//Class for printing messages and errors to standard output
public class stdout {
  private static byte PRINT_LEVEL = 1; //Value is assigned in main.java using setPrintLevel()
  
  public static void setPrintLevel() {
    if (cli.quietOutput()) PRINT_LEVEL = 0;
    else if (cli.verboseOutput()) PRINT_LEVEL = 2;
    else if (cli.debugOutput()) PRINT_LEVEL = 3;
    else PRINT_LEVEL = 1;
  }
  
  public static void print(String message) {
    if (PRINT_LEVEL > 0) {System.out.println(message);}
  }

  public static void print(String title, String[] contents) {
    if (PRINT_LEVEL > 0) {printSeq(title, contents);}
  }

  public static void print_verbose(String message) {
    if (PRINT_LEVEL > 1) {System.out.println(message);}
  }

  public static void print_verbose(String title, String[] contents) {
    if (PRINT_LEVEL > 1) {printSeq(title, contents);}
  }

  public static void print_debug(String message) {
    if (PRINT_LEVEL > 2) {System.out.println(message);}
  }

  public static void print_debug(String title, String[] contents) {
    if (PRINT_LEVEL > 2) {printSeq(title, contents);}
  }
  
  public static void error(String message) {
    if (PRINT_LEVEL > 0) {System.err.println(message);}
  }

  private static void printSeq(String title, String[] contents) {
    String txt = title;
    for (String c : contents) {txt += "\n  * " + c;}
    
    System.out.println(txt);
  }
  
  private static void printSeq(String title, ArrayList<String> contents) {
    String txt = title;
    for (String c : contents) {txt += "\n  * " + c;}
    
    System.out.println(txt);
  }
}
