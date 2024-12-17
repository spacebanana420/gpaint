package gpaint.themes;

public class RGB {
  public byte[] rgb = new byte[]{0, 0, 0};
  public short[] rgb_u = new short[]{0, 0, 0};
  public String hex = "";
  
  public RGB(String color_hex) {
    hex = color_hex.toLowerCase();
    rgb = getRGB();
    for (int i = 0; i < rgb.length; i++) {rgb_u[i] = byteToUnsigned(rgb[i]);}
  }
  
  public String getHexValue() {
    String r = RGBtoHex(rgb_u[0]);
    String g = RGBtoHex(rgb_u[1]);
    String b = RGBtoHex(rgb_u[2]);    
    return "#" + r + g + b;
  }
  
  public void adjustContrast(float percentage, boolean increase) { //fix contrast lowering
    if (percentage <= 0) {return;}
    else if (percentage > 100) {percentage = 100;}
    
    float increment = (float)(255 * (percentage/100));
    float average = (rgb_u[0] + rgb_u[1] + rgb_u[2]) / 3;
    if (!increase) {increment = (short)(increment * -1);} //Lower contrast
    if (average < 128) {increment = (short)(increment * -1);} //Whether increase or lower brightness
    else if (average == 128) {return;}

    short highest = 0;
    for (int i = 0; i < rgb_u.length; i++) {
      if (rgb_u[i] > highest) {highest = rgb_u[i];}
    }
    float factor = (highest == 0) ? 1 : highest;

    for (int i = 0; i < rgb_u.length; i++) {
      rgb_u[i] += increment * (rgb_u[i]/factor);
      if (rgb_u[i] < 0) {rgb_u[i] = 0;}
      else if (rgb_u[i] > 255) {rgb_u[i] = 255;}
      rgb[i] = (byte)rgb_u[i];
    }
  }
  
  public void adjustSaturation(float percentage, boolean increase) {
    if (percentage > 100) {percentage = 100;}
    else if (percentage <= 0) {return;}
    if (rgb_u[0] == rgb_u[1] && rgb_u[0] == rgb_u[2]) {return;}
    
    short highest = 0;
    short lowest = 255;
    
    for (int i = 0; i < rgb_u.length; i++) {
      if (rgb_u[i] > highest) {highest = rgb_u[i];}
      else if (rgb_u[i] < lowest) {lowest = rgb_u[i];}
    }
    if (highest == 0) {return;}
    
    float percentage_factor = percentage/100;
    for (int i = 0; i < rgb_u.length; i++)
    {
      if (rgb_u[i] == highest) {continue;}
      if (increase) {
        rgb_u[i] = (short)(rgb_u[i] - (rgb_u[i] * (1-(rgb_u[i] / highest)) * percentage_factor));
      }
      else {
        rgb_u[i] = (short)(rgb_u[i] + ((highest - rgb_u[i]) * percentage_factor));
      }
      rgb[i] = (byte)rgb_u[i];
    }
  }
  
  public void invertColors() {
    for (int i = 0; i < rgb_u.length; i++) {rgb_u[i] = (short)(255 - rgb_u[i]);}
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
  
  private char getCharFromValue(byte digit, char[] chars, byte[] values) {
    for (int i = 0; i < chars.length; i++) {
      if (digit == values[i]) {return chars[i];}
    }
    return 0;
  }
  
  private String RGBtoHex(short value) {
    byte digit0;
    byte digit1;
    char digit0c;
    char digit1c;
    char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    byte[] values = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    
    digit1 = (byte)(value % 16);
    digit0 = (byte)((value / 16) % 16);
    
    digit0c = getCharFromValue(digit0, chars, values);
    digit1c = getCharFromValue(digit1, chars, values);
    
    return "" + digit0c + digit1c;
  }
}
