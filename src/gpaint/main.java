package gpaint;

import gpaint.cli.cli;
import gpaint.cli.help;
import gpaint.themes.themeparse;
import gpaint.themes.fileio;

import java.io.File;

public class main {
  public static void main(String[] args) {
    if (cli.askedForHelp(args)) {help.printHelp(); return;}
    
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
        for (String theme : themes) {txt+= "  * " + theme + "\n";}
        System.out.println(txt);
      }
      return;
    }
    
    String[] themeFiles = cli.getThemeFiles(args, themes_dir);
    if (themeFiles.length == 0) {help.printThemeLack(); return;}
    if (!cli.hasColorAdjustments(args)) {help.printTaskLack(); return;}
    
    for (String themeFile : themeFiles)
    {
      String[] lines = fileio.readFile(themeFile);
      themeparse.convertTheme(lines, args, cli.getCustomThemeName(args));
      String new_path = generateNewFilename(themeFile, cli.getCustomFilename(args));
      fileio.writeFile(lines, new_path);
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
  
  static String generateNewFilename(String path, String customname) {
    if (customname != null) {
      String parent = new File(path).getParent();
      if (parent == null) {return customname;}
      else {return parent + System.getProperty("file.separator") + customname;}
    }
    String name = "";
    String extension = "";
    int extension_i = -1;
    for (int i = path.length()-1; i >= 0; i--)
    {
      char c = path.charAt(i);
      if (c == '.') {extension_i = i; break;}
    }
    if (extension_i == -1) {name = path; extension = ".conf";}
    else {
      for (int i = 0; i < extension_i; i++) {name += path.charAt(i);}
      for (int i = extension_i; i < path.length(); i++) {extension += path.charAt(i);}
    }
    int i = 0;
    while (true)
    {
      String new_filename = name + "-GPaint-" + i + extension;
      if (!new File(new_filename).isFile()) {break;}
      i++;
    }
    return name + "-GPaint-" + i + extension;
  }
}
