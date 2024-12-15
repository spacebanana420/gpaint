package gpaint.themes;

public class RGB {
  public byte[] rgb = new byte[]{0, 0, 0};
  public String hex = "";
  
  public RGB(String color_hex) {
    hex = color_hex;
    rgb = getRGB();
  }
  
  public void adjustContrast(byte percentage, boolean increase) { //fix contrast lowering
    if (percentage <= 0) {return;} else if (percentage > 100) {percentage = 100;}
    short increment = (short)(255 * (percentage/100));
    if (!increase) {increment = (short)(increment * -1);} //Lower contrast
    if ((rgb[0] + rgb[1] + rgb[2]) / 3 < 128) {increment = (short)(increment * -1);} //Whether increase or lower brightness
    
    short unsigned;
    for (int i = 0; i < rgb.length; i++) {
      unsigned = byteToUnsigned(rgb[i]);
      if (unsigned == 0 || unsigned == 255) {continue;}
      
      unsigned += increment;
      if (unsigned < 0) {unsigned = 0;}
      else if (unsigned > 255) {unsigned = 255;}
      rgb[i] = (byte)unsigned;
    }
  }
  
  public void adjustSaturation(byte percentage, boolean increase) {
    if (percentage > 100) {percentage = 100;}
    else if (percentage <= 0) {return;}
    
    short[] unsigned_rgb = new short[3];
    for (int i = 0; i < unsigned_rgb.length; i++) {unsigned_rgb[i] = byteToUnsigned(rgb[i]);}
    
    short highest = 0;
    short lowest = 255;
    for (int i = 0; i < unsigned_rgb.length; i++) {
      if (unsigned_rgb[i] > highest) {highest = unsigned_rgb[i];}
      else if (unsigned_rgb[i] < lowest) {lowest = unsigned_rgb[i];}
    }
    
    for (int i = 0; i < unsigned_rgb.length; i++)
    {
      if (unsigned_rgb[i] == highest) {continue;}
      if (increase) {
        unsigned_rgb[i] = (short)(unsigned_rgb[i] - (unsigned_rgb[i] * (1-(unsigned_rgb[i] / highest)) * (percentage/100)));
      }
      else {
        unsigned_rgb[i] = (short)(unsigned_rgb[i] + ((highest-unsigned_rgb[i]) * (percentage/100)));
      }
      rgb[i] = (byte)unsigned_rgb[i];
    }
  }
  
  public void invertColors() {
    for (int i = 0; i < rgb.length; i++) {
      rgb[i] = (byte)(255 - byteToUnsigned(rgb[i]));
    }
  }
  
  //this might slow down things but it makes it simpler, i wish java had unsigned data types holy shit
  private short byteToUnsigned(byte b) {return (b >= 0) ? b : (short)(256 - (b * -1));}
  
  private byte[] getRGB() {
    String hex_value = "";
    if (hex.length() < 2) {return null;}
    //int = 1 to ignore first # character
    for (int i = 1; i < hex.length(); i++) {hex_value += hex.charAt(i);}
    while (hex_value.length() < 6) {hex_value += '0';}
    return new byte[]
    {
      channelToRGB(hex_value.charAt(0), hex_value.charAt(1)),
      channelToRGB(hex_value.charAt(2), hex_value.charAt(3)),
      channelToRGB(hex_value.charAt(4), hex_value.charAt(5))
    };
  }
  
  private byte channelToRGB(char hex1, char hex2) {
    char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    byte[] values = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    
    byte hex1b = getValueFromChar(hex1, chars, values);
    byte hex2b = getValueFromChar(hex2, chars, values);
    
    return (byte)(hex1b * 16 + hex2b);
  }
  private byte getValueFromChar(char hexdigit, char[] chars, byte[] values) {
    for (int i = 0; i < chars.length; i++) {
      if (hexdigit == chars[i]) {return values[i];}
    }
    return 0;
  }
}
