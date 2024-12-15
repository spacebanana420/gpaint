package gpaint.themes;

//import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;

public class fileio {
  public static String[] readFile(String file, boolean ignore_comments) {
    if (!new File(file).isFile()) {return null;}
    try {
      byte[] file_data = Files.readAllBytes(Path.of(file));
      String file_string = new String(file_data);
      var lines = new ArrayList<String>();
      String line = "";
      for (int i = 0; i < file_string.length(); i++)
      {
        char c = file_string.charAt(i);
        if (c == '\n') {
          boolean ignore_line = line.length() == 0 || (ignore_comments && line.charAt(0) == '#');
          if (!ignore_line) {lines.add(line);}
          line = "";
          continue;
        }
        else {line += c;}
      }
      return lines.toArray(new String[0]);
    }
    catch (IOException e) {return null;}
  }
}
