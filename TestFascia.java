package warehousing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestFascia {
  Fascia f1;
  Fascia f2;

  @Before
  public void setUp() throws IOException {
    f1 = new Fascia("Blue", "S", "33", false);
    f2 = new Fascia("Red", "SE", "20", true);
  }

  @Test
  public void testgetColour1() {
    assertEquals("Blue", f1.getColour());
  }

  @Test
  public void testgetColour2() {
    assertEquals("Red", f2.getColour());
  }

  @Test
  public void testgetModel1() {
    assertEquals("S", f1.getModel());
  }

  @Test
  public void testgetModel2() {
    assertEquals("SE", f2.getModel());
  }

  @Test
  public void testgetSku1() {
    assertEquals("33", f1.getSku());
  }

  @Test
  public void testgetSku2() {
    assertEquals("20", f2.getSku());
  }

  @Test
  public void testSetandGerLocation1() throws IOException {
    f1.setLocation();
    assertEquals("B020", f1.getLocation());
  }

  @Test
  public void testSetandGetLocation2() throws IOException {
    f2.setLocation();
    assertEquals("A113", f2.getLocation());
  }

  @Test
  public void testGetOrientation1() {
    assertFalse(f1.getOrientation());
  }

  @Test
  public void testGetOrientation2() {
    assertTrue(f2.getOrientation());
  }

}
