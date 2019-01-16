package warehousing;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class TestMain {
  @Test
  public void testMain() throws SecurityException, IOException {
    String[] raodPath = new String[2];
    raodPath[1] = "perfectEvents.txt";
    raodPath[0] = "errorEvents.txt";
    Main.main(raodPath);
    BufferedReader finalReader = new BufferedReader(new FileReader("final.csv"));
    finalReader.readLine();
    assertNotNull(finalReader.readLine());
    assertNotNull(finalReader.readLine());
    finalReader.close();
  }
}
