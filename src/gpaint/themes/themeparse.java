package gpaint.themes;

import gpaint.cli.cli;
import java.util.ArrayList;

public class themeparse {
  public static void convertTheme(String[] lines, String[] args) {
    for (int i = 0; i < lines.length; i++) {
      lines[i] = convertLine(lines[i], args);
    }
  }
  
  private static String convertLine(String line, String[] args) {
    if (line.charAt(0) == '#' || !line.contains("=")) {return line;}
    
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
    
    for (int i = 0; i < line_values.size(); i++) {
      String newvalue = processHexValue(line_values.get(i), args);
      if (i == line_values.size()-1) {newline+=newvalue;}
      else {newline+=newvalue+';';}
    }
    return newline;
  }
 
  //replace with floats later
  private static String processHexValue(String value, String[] args) {
    String hex = value.trim();
    if (hex == null || hex.length() < 2 || hex.length() > 7 || hex.charAt(0) != '#') {return value;}
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
    if (cli.invertColors(args)) {color.invertColors();}
    
    return color.getHexValue();
  }
}
