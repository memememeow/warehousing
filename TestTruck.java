package warehousing;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestTruck {
  Truck t1 = new Truck();
  private ArrayList<String> frontSku1;
  private ArrayList<String> backSku1;
  private ArrayList<String> frontSku2;
  private PickingRequest pr1;
  private PickingRequest pr2;
  private ArrayList<String> backSku2;

  /**
   * Set up all the classes needed for the test.
   * 
   * @throws IOException may throw exception when a new picking request is created
   */
  @Before
  public void setUp() throws IOException {
    ArrayList<String> sku1 = new ArrayList<String>();
    sku1.add("sku001");
    sku1.add("sku002");
    final Order order1 = new Order("Blue", "S", sku1, 1);
    ArrayList<String> sku2 = new ArrayList<String>();
    sku2.add("sku003");
    sku2.add("sku004");
    final Order order2 = new Order("RED", "S", sku2, 2);
    ArrayList<String> sku3 = new ArrayList<String>();
    sku3.add("sku005");
    sku3.add("sku006");
    final Order order3 = new Order("GREEN", "S", sku3, 3);
    ArrayList<String> sku4 = new ArrayList<String>();
    sku4.add("sku007");
    sku4.add("sku008");
    final Order order4 = new Order("YELLOW", "S", sku4, 4);
    ArrayList<Order> o1 = new ArrayList<Order>();
    o1.add(order1);
    o1.add(order2);
    o1.add(order3);
    o1.add(order4);
    frontSku1 = new ArrayList<String>();
    frontSku1.add("sku001");
    frontSku1.add("sku003");
    frontSku1.add("sku005");
    frontSku1.add("sku007");
    backSku1 = new ArrayList<String>();
    backSku1.add("sku002");
    backSku1.add("sku004");
    backSku1.add("sku006");
    backSku1.add("sku008");
    ArrayList<String> sku5 = new ArrayList<String>();
    sku5.add("sku009");
    sku5.add("sku010");
    final Order order5 = new Order("Blue", "SE", sku5, 5);
    ArrayList<String> sku6 = new ArrayList<String>();
    sku6.add("sku011");
    sku6.add("sku012");
    final Order order6 = new Order("RED", "SE", sku6, 6);
    ArrayList<String> sku7 = new ArrayList<String>();
    sku7.add("sku013");
    sku7.add("sku014");
    final Order order7 = new Order("GREEN", "SE", sku7, 7);
    ArrayList<String> sku8 = new ArrayList<String>();
    sku8.add("sku015");
    sku8.add("sku016");
    final Order order8 = new Order("YELLOW", "SE", sku8, 8);
    ArrayList<Order> o2 = new ArrayList<Order>();
    o2.add(order5);
    o2.add(order6);
    o2.add(order7);
    o2.add(order8);
    frontSku2 = new ArrayList<String>();
    frontSku2.add("sku009");
    frontSku2.add("sku011");
    frontSku2.add("sku013");
    frontSku2.add("sku015");
    backSku2 = new ArrayList<String>();
    backSku2.add("sku010");
    backSku2.add("sku012");
    backSku2.add("sku014");
    backSku2.add("sku016");
    pr1 = new PickingRequest(o1, 1);
    pr2 = new PickingRequest(o2, 2);
  }

  @Test
  public void testLoadFascia() {

    assertTrue(t1.getStatus());
    assertEquals(t1.getLastREid(), 0);
    t1.loadFascia(pr1, frontSku1, backSku1);
    assertEquals(t1.getPickingContent().size(), 1);
    assertEquals(t1.getContentTruck().size(), 2);
    for (int i = 0; i < backSku1.size(); i++) {
      assertEquals(t1.getContentTruck().get(0).get(i), backSku1.get(i));
    }
    for (int i = 0; i < frontSku1.size(); i++) {
      assertEquals(t1.getContentTruck().get(1).get(i), frontSku1.get(i));
    }
    assertEquals(t1.getLastREid(), 1);
    t1.loadFascia(pr2, frontSku2, backSku2);
    assertEquals(t1.getPickingContent().size(), 2);
    assertEquals(t1.getContentTruck().size(), 4);
    assertEquals(t1.getLastREid(), 2);
    for (int i = 0; i < backSku2.size(); i++) {
      assertEquals(t1.getContentTruck().get(2).get(i), backSku2.get(i));
    }
    for (int i = 0; i < frontSku2.size(); i++) {
      assertEquals(t1.getContentTruck().get(3).get(i), frontSku2.get(i));
    }
    t1.leaveTruck();
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    t1.loadFascia(pr2, frontSku2, backSku2);
    assertFalse(t1.getStatus());
    t1.leaveTruck();
  }
}
