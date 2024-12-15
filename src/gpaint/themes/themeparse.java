package gpaint.themes;

public class themeparse {
  public static void convertTheme(String[] lines) {
    for (int i = 0; i < lines.length; i++) {
      lines[i] = convertline(lines[i]);
    }
  }
  
  private static String convertLine(String line) {
    if (line.charAt(0) == '#') {return line;}
    String newline = "";
    boolean copy = false;
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (c == '=') {
        newline += c;
        copy = true;
      }
      else if (copy && c == '#') { //character # indicates the start of a hexadecimal value
        String new_hex = processHexValue(line, i);
        newline += new_hex;
        i += new_hex.length();
      }
      else {newline += c;}
    }
  }
  //unfinished, requires CLI implementation
  private static String processHexValue(String line, int start_i) { //this is where the fun begins
    String hex = "";
    for (int i = start_i; i < line.length(); i++) {
      char c = line.charAt(i);
      if (c == ';') {break;}
      else {hex += c;}
    }
    hex = hex.trim();
    if (hex.length() > 7) {return hex;} //invalid
    RGB color = new RGB(hex);
  }
}
