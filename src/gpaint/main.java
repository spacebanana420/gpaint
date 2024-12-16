package gpaint;

import gpaint.cli.cli;
import gpaint.cli.help;
import gpaint.themes.themeparse;
import gpaint.themes.fileio;

import java.io.File;

public class main {
  public static void main(String[] args) {
    if (cli.askedForHelp(args)) {
      help.printHelp();
      return;
    }
    if (!cli.hasColorAdjustments(args)) {
      help.printDefaultMessage();
      return;
    }
    
    String themes_dir = getThemeDirectory();
    if (cli.listGeanyThemes(args)) {
      String[] themes = getGeanyThemes(themes_dir);
      if (themes == null || themes.length == 0) {
        System.out.println
        (
          "No themes found!"
          +"\nMake sure the directory " + themes_dir + " exists and contains theme files inside!"
          +"Otherwise you can manually specify the path to a theme file"
        );
      }
      else {
        String txt = "Available Geany themes in " + themes_dir + ":\n";
        for (String theme : themes) {
          txt+= "  * " + theme + "\n";
        }
        System.out.println(txt);
      }
      return;
    }
    for (String themeFile : cli.getThemeFiles(args, themes_dir)) {
      String[] lines = fileio.readFile(themeFile);
      themeparse.convertTheme(lines, args);
      //finish
    }
  }
  
  static String getThemeDirectory() {
    String platform = System.getProperty("os.name");
    String home = System.getProperty("user.home");
    if (platform.contains("Windows")) {return home+"\\AppData\\Roaming\\geany\\colorschemes";}
    else {return home+"/.config/geany/colorschemes";}
  }
  
  static String[] getGeanyThemes(String path) {
    var f = new File(path);
    if (!f.isDirectory() || !f.canRead()) {return null;}
    return f.list();
  }
}
