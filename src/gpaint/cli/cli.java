package gpaint.cli;

import java.util.ArrayList;
import java.io.File;

public class cli {
  
  public static boolean listGeanyThemes(String[] args) {return hasArgument(args, "-list");}
  
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
  
  public static boolean invertColors(String[] args) {return hasArgument(args, "-invert");}
  
  public static boolean hasColorAdjustments(String[] args) {
    return
      hasArgument(args, "+sat") || hasArgument(args, "-sat")
      || hasArgument(args, "+contrast") || hasArgument(args, "-contrast")
      || hasArgument(args, "-invert")
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
  
  private static int findArgument(String[] args, String argument) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals(argument)) {return i;}
    }
    return -1;
  }

  private static boolean isArgument(String arg) {return arg != null && arg.length() > 1 && arg.charAt(0) == '-';}
}
