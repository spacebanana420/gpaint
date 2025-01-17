package gpaint.themes;

import gpaint.cli.cli;
import java.util.ArrayList;

public class themeparse {
  public static void convertTheme(String[] lines, String[] args) {
    changeThemeName(lines);
    for (int i = 0; i < lines.length; i++)
    {
      lines[i] = convertLine(lines[i], args);
    }
  }
  
  private static void changeThemeName(String[] lines) {
    for (int i = 0; i < lines.length; i++)
    {
      if (lines[i].contains("name=") || lines[i].contains("name ="))
      {
        lines[i] += " (GPaint version)";
        return;
      }
    }
  }
  
  private static String convertLine(String line, String[] args) {
    if (line.equals("") || line.charAt(0) == '#' || !line.contains("=")) {return line;}
    
    String newline = "";
    var line_values = new ArrayList<String>();
    int values_start = 0;
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      newline += c;
      if (c == '=') {values_start = i+1; break;}
    }
    
    String value_buffer = "";
    for (int i = values_start; i < line.length(); i++) { //add the values to a list to process them flexibly
      char c = line.charAt(i);
      if (c == ';' && value_buffer.length() > 0) {
        line_values.add(value_buffer);
        value_buffer = "";
      }
      else {value_buffer += c;}
    }
    if (value_buffer.length() > 0) {line_values.add(value_buffer);}
    
    int values_size = line_values.size();
    for (int i = 0; i < values_size-1; i++) {
      newline += processHexValue(line_values.get(i), args) + ';';
    }
    newline += processHexValue(line_values.get(values_size-1), args); //last element doesn't require a semicolon ;
    return newline;
  }
 
  //replace with floats later
  private static String processHexValue(String value, String[] args) {
    String hex = value.trim();
    if (hex == null || (hex.length() != 4 && hex.length() != 7) || hex.charAt(0) != '#') {return value;}
    RGB color = new RGB(hex);
    
    byte percentage = cli.getContrastAdd(args);
    if (percentage != -1) {color.adjustContrast((float)percentage, true);}
    else {
      percentage = cli.getContrastSub(args);
      if (percentage != -1) {color.adjustContrast((float)percentage, false);}
    }
    
    percentage = cli.getSaturationAdd(args);
    if (percentage != -1) {color.adjustSaturation((float)percentage, true);}
    else {
      percentage = cli.getSaturationSub(args);
      if (percentage != -1) {color.adjustSaturation((float)percentage, false);}
    }
    
    percentage = cli.getBrightnessAdd(args);
    if (percentage != -1) {color.adjustBrightness((float)percentage, true);}
    else {
      percentage = cli.getBrightnessSub(args);
      if (percentage != -1) {color.adjustBrightness((float)percentage, false);}
    }
    
    percentage = cli.getTemperatureSub(args);
    if (percentage != -1) {color.lowerTemperature((float)percentage);}
    
    if (cli.invertColors(args)) {color.invertColors();}
    
    return color.getHexValue();
  }
}
