package gpaint.themes;

public class RGB {
  public byte[] rgb = new byte[]{0, 0, 0};
  public String hex = "";
  
  public RGB(String color_hex) {
    hex = color_hex;
    rgb = getRGB();
  }
  
  public void AdjustContrast() {
    
  }
  
  public void AdjustSaturation() {
  
  }
  
  public void InvertColors() {
    
  }
  
  private byte[] getRGB() {
    String hex_value = "";
    if (hex.length() < 2) {return null;}
    //int = 1 to ignore first # character
    for (int i = 1; i < hex.length(); i++) {hex_value += hex.charAt(i);}
    while (hex_value.length() < 6) {hex_value += '0';}
    return new byte[]
    {
      getChannelValue(hex_value.charAt(0), hex_value.charAt(1)),
      getChannelValue(hex_value.charAt(2), hex_value.charAt(3)),
      getChannelValue(hex_value.charAt(4), hex_value.charAt(5))
    };
  }
  
  private byte getChannelValue(char hex1, char hex2) {
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
