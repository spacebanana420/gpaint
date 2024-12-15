public class signed {
  //0 to 127 and -128 to -1
  public static void main(String[] args) {
    byte value = 127;
    value +=1;
    System.out.println(value);
    
    value = -50;
    System.out.println(255 - value);
    
    short unsigned = 230;
    System.out.println((byte)unsigned);
  }
}
