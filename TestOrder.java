package warehousing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class TestOrder {
  Order o1;
  Order o2;

  /**
   * Set up classes for the tests.
   */
  @Before
  public void setUp() {
    ArrayList<String> skus = new ArrayList<String>();
    skus.add("a24");
    skus.add("b25");
    o1 = new Order("Blue", "S", skus, 1);
    ArrayList<String> skus2 = new ArrayList<String>();
    skus2.add("ewq2");
    skus2.add("ewq1");
    o2 = new Order("Blue", "S", skus2, 2);
  }

  @Test
  public void testgetColour() {
    assertEquals("Blue", o1.getColour());
  }

  @Test
  public void testgetModel() {
    assertEquals("S", o1.getModel());
  }

  @Test
  public void testgetOrderNum() {
    assertEquals(1, o1.getOrderNum());
    assertEquals(2, o2.getOrderNum());
  }

  @Test
  public void testgetBack() {
    assertEquals("b25", o1.getBackSku());
    assertEquals("ewq1", o2.getBackSku());
  }

  @Test
  public void testgetFront() {
    assertEquals("a24", o1.getFrontSku());
    assertEquals("ewq2", o2.getFrontSku());
  }
}
