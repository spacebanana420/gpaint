import gpaint.themes.RGB;

public class rgbtest {
  public static void main(String[] args) {
    String hex = "#f0af71";
    System.out.println("RGB: 240  175  113\nHex: " + hex);
    
    RGB color = new RGB(hex);
    printRGB(color.rgb_u);
    color.adjustSaturation((byte)100, true);
    printRGB(color.rgb_u);
    color.invertColors();
    printRGB(color.rgb_u);
    
    String new_hex = color.getHexValue();
    System.out.println("New hex: " + new_hex);
  }
  
  static void printRGB(short[] rgb) {
    String txt = "Processed RGB:  ";
    for (short channel : rgb) {txt += channel + "  ";}
    System.out.println(txt);
  }
}
