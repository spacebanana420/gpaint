import gpaint.themes.RGB;

public class rgbtest {
  public static void main(String[] args) {
    String hex = "#f0af71";
    System.out.println("RGB: 240  175  113\nHex: " + hex);
    var color = new RGB(hex);
    printRGB(color.rgb_u);
  }
  
  static void printRGB(short[] rgb) {
    String txt = "Processed RGB:  ";
    for (short channel : rgb) {txt += channel + "  ";}
    System.out.println(txt);
  }
}
