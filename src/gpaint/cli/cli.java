package gpaint.cli;

import java.util.ArrayList;
import java.io.File;

public class cli {
  
  public static boolean askedForHelp(String[] args) {return hasArgument(args, "-h") || hasArgument(args, "--help");}
  
  public static boolean listGeanyThemes(String[] args) {return hasArgument(args, "-l") || hasArgument(args, "--list");}
  
  public static String getCustomFilename(String[] args) {
    return getname_generic(args, "-o", "--output");
  }
  
  public static String getCustomThemeName(String[] args) {
    return getname_generic(args, "-n", "--name");
  }
  
  private static String getname_generic(String[] args, String... arguments) {
    int i = findArgument(args, arguments);
    if (i == -1 || i == args.length-1) {return null;}
    String name = args[i+1];
    if (name == null || name.length() == 0 || name.charAt(0) == '-') {return null;}
    return name;
  }
  
  public static String[] getThemeFiles(String[] args, String theme_path) {
    var files = new ArrayList<String>();
    for (int i = 0; i < args.length; i++)
    {
      String a = args[i];
      String full_path = theme_path + "/" + a;
      var f = new File(a);
      var f_conf = new File(full_path);
      
      boolean pathIsCorrect = f.isFile() && f.canRead();
      boolean isInGeanyConf = f_conf.isFile() && f_conf.canRead();
      boolean notOutputName = i == 0 || (!args[i-1].equals("-o") && !args[i-1].equals("--output") && !args[i-1].equals("-n") && !args[i-1].equals("--name"));
      boolean isFile = pathIsCorrect || isInGeanyConf;

      if (!isArgument(a) && isFile && notOutputName) {
        if (pathIsCorrect) {files.add(a);}
        else {files.add(full_path);}
      }
    }
    return files.toArray(new String[0]);
  }
  
  public static byte getContrastAdd(String[] args) {
    String contrast = getArgumentValue(args, "+contrast");
    return getPercentage(contrast);
  }
  
  public static byte getContrastSub(String[] args) {
    String contrast = getArgumentValue(args, "-contrast");
    return getPercentage(contrast);
  }
  
  public static byte getSaturationAdd(String[] args) {
    String sat = getArgumentValue(args, "+sat");
    return getPercentage(sat);
  }
  
  public static byte getSaturationSub(String[] args) {
    String sat = getArgumentValue(args, "-sat");
    return getPercentage(sat);
  }
  
  public static byte getBrightnessAdd(String[] args) {
    String b = getArgumentValue(args, "+bright");
    return getPercentage(b);
  }
  
  public static byte getBrightnessSub(String[] args) {
    String b = getArgumentValue(args, "-bright");
    return getPercentage(b);
  }
  
  public static byte getTemperatureSub(String[] args) {
    String temp = getArgumentValue(args, "-temp");
    return getPercentage(temp);
  }
  
  public static boolean invertColors(String[] args) {return hasArgument(args, "-invert");}
  
  public static boolean hasColorAdjustments(String[] args) {
    return
      hasArgument(args, "+sat") || hasArgument(args, "-sat")
      || hasArgument(args, "+contrast") || hasArgument(args, "-contrast")
      || hasArgument(args, "-invert")
      || hasArgument(args, "+bright") || hasArgument(args, "-bright")
      || hasArgument(args, "-temp")
    ;
  } 
  
  private static byte getPercentage(String value) {
    if (value == null || value.length() > 3) {return -1;}
    try {
      byte percentage = Byte.parseByte(value);
      if (percentage >= 0 && percentage <= 100) {return percentage;}
      else {return -1;}
    }
    catch (NumberFormatException e) {return -1;}
  }
  
  private static String getArgumentValue(String[] args, String argument) {
    if (args.length < 2) {return null;}
    int i = findArgument(args, argument);
    if (i == -1 || i == args.length-1) {return null;}
    return args[i+1];
  }

  private static boolean hasArgument(String[] args, String argument) {return findArgument(args, argument) != -1;}
  
  private static int findArgument(String[] args, String... arguments) {
    int i = -1;
    for (String a : arguments) {
      i = findArgument(args, a);
      if (i != -1) {return i;}
    }
    return i;
  }
  
  private static int findArgument(String[] args, String argument) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals(argument)) {return i;}
    }
    return -1;
  }

  private static boolean isArgument(String arg) {return arg != null && arg.length() > 1 && arg.charAt(0) == '-';}
}
