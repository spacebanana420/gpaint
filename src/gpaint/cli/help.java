package gpaint.cli;

public class help {
  static String version() {return "[GPaint v0.2.1]";}
  
  public static void printHelp() {
    System.out.println
    (
      version()
      + "\nUsage: gpaint [options] [theme files...]"
      + "\n"
      + "\n==Options=="
      + "\n  * -h (--help) - opens this menu"
      + "\n  * -l (--list) - lists available Geany themes"
      + "\n  * -o (--output) - set a custom output filename"
      + "\n  * -n (--name) - sets a custom theme name, if available"
      + "\n  * +contrast [percentage] - raises contrast from 0 to 100"
      + "\n  * -contrast [percentage] - lowers contrast from 0 to 100"
      + "\n  * +sat [percentage] - raises saturation from 0 to 100"
      + "\n  * -sat [percentage] - lowers saturation from 0 to 100"
      + "\n  * +bright [percentage] - raises brightness from 0 to 100"
      + "\n  * -bright [percentage] - lowers brightness from 0 to 100"
      + "\n  * -temp [percentage] - lowers color temperature from 0 to 100"
      + "\n  * -invert - inverts the theme colors"
    );
  }
  
  public static void printThemeLack() {
    System.out.println
    (
      version()+"\n"
      + "\nYou have not specified any Geany theme!"
      + "\nRun \"gpaint -h\" to see what you can do"
      + "\nRun \"gpaint -l\" to list the available Geany themes in your user config"
    );
  }
  
  public static void printTaskLack() {
    System.out.println
    (
      version()+"\n"
      + "\nYou have not specified any task to perform on the themes!"
      + "\nRun \"gpaint -h\" to see what you can do"
    );
  }
}
