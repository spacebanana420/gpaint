package gpaint.cli;

public class help {
  public static void printHelp() {
    System.out.println
    (
      "==GPaint v0.1=="
      + "\nUsage: gpaint [options] [theme files...]"
      + "\n"
      + "\n==Options=="
      + "\n  * -h - opens this menu"
      + "\n  * -list - lists available Geany themes"
      + "\n  * +contrast [percentage] - raises contrast from 0 to 100"
      + "\n  * -contrast [percentage] - lowers contrast from 0 to 100"
      + "\n  * +sat [percentage] - raises saturation from 0 to 100"
      + "\n  * -sat [percentage] - lowers saturation from 0 to 100"
      + "\n  * -invert - inverts the theme colors"
    );
  }
}
