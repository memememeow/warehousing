package warehousing;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPicker {
  Picker picker;

  @Before
  public void setUp() throws IOException {
    picker = new Picker("Tom");
  }

  @After
  public void tearDown() {

  }

  @Test
  public void testPickSingleFascia1() throws IOException {
    picker.setCheckResult(false);
    String input1 = "aw2";
    InputStream in1 = new ByteArrayInputStream(input1.getBytes());
    System.setIn(in1);
    picker.pickSingleFascia("A000", "aw2");
    assertTrue(picker.getCheckResult());
    String input2 = "aw3";
    InputStream in2 = new ByteArrayInputStream(input2.getBytes());
    System.setIn(in2);
    picker.pickSingleFascia("A000", "aw2");
  }

  @Test
  public void testPickSingleFascia2() throws IOException {
    picker.setCheckResult(false);
    String input = "ce2";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    picker.pickSingleFascia("A000", "aw2");
    assertFalse(picker.getCheckResult());
  }

}
