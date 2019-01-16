package warehousing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPickingRequest {
  PickingRequest pr1;

  /**
   * Set up classes for the tests.
   * 
   * @throws IOException exception may be thrown when creating a picking request.
   */
  @Before
  public void setUp() throws IOException {

    ArrayList<String> sku1 = new ArrayList<String>();
    sku1.add("a24");
    sku1.add("b24");
    Order o1 = new Order("Blue", "S", sku1, 1);
    ArrayList<Order> orderlist1 = new ArrayList<Order>();
    orderlist1.add(o1);
    ArrayList<String> sku2 = new ArrayList<String>();
    sku2.add("c21");
    sku2.add("d21");
    Order o2 = new Order("Red", "S", sku2, 2);
    orderlist1.add(o2);
    ArrayList<String> sku3 = new ArrayList<String>();
    sku3.add("a25");
    sku3.add("b25");
    Order o3 = new Order("Blue", "SEL", sku3, 3);
    orderlist1.add(o3);
    ArrayList<String> sku4 = new ArrayList<String>();
    sku4.add("c27");
    sku4.add("d27");
    Order o4 = new Order("Silver", "S", sku4, 4);
    orderlist1.add(o4);
    ArrayList<String> locations = new ArrayList<String>();
    String location1 = "A001";
    String location2 = "A002";
    String location3 = "B001";
    String location4 = "B002";
    String location5 = "C001";
    String location6 = "C002";
    String location7 = "D001";
    String location8 = "D002";
    locations.add(location1);
    locations.add(location2);
    locations.add(location3);
    locations.add(location4);
    locations.add(location5);
    locations.add(location6);
    locations.add(location7);
    locations.add(location8);
    pr1 = new PickingRequest(orderlist1, 1);
    pr1.addUnsequencedFascia("a00");
    pr1.addUnsequencedFascia("a11");
    // pr1.setLocations(locations);

  }

  @After
  public void tearDown() {

  }

  @Test
  public void testGetId() {
    assertEquals(1, pr1.getId());
  }

  @Test
  public void testGetOrdersList() {
    Order order0 = pr1.getOrdersList().get(0);
    assertEquals("Blue", order0.getColour());
    Order order1 = pr1.getOrdersList().get(1);
    assertEquals("Red", order1.getColour());
    Order order2 = pr1.getOrdersList().get(2);
    assertEquals("Blue", order2.getColour());
    Order order3 = pr1.getOrdersList().get(3);
    assertEquals("Silver", order3.getColour());
    assertEquals(1, order0.getOrderNum());
    assertEquals(2, order1.getOrderNum());
    assertEquals(3, order2.getOrderNum());
    assertEquals(4, order3.getOrderNum());
    assertTrue(order0.getFrontSku().contains("a24"));
    assertTrue(order0.getBackSku().contains("b24"));
    assertTrue(order1.getFrontSku().contains("c21"));
    assertTrue(order1.getBackSku().contains("d21"));
    assertTrue(order2.getFrontSku().contains("a25"));
    assertTrue(order2.getBackSku().contains("b25"));
    assertTrue(order3.getFrontSku().contains("c27"));
    assertTrue(order3.getBackSku().contains("d27"));

  }

  @Test
  public void testGetFrontSkus() {
    assertTrue(pr1.getFrontSkus().contains("a24"));
    assertTrue(pr1.getFrontSkus().contains("c21"));
    assertTrue(pr1.getFrontSkus().contains("a25"));
    assertTrue(pr1.getFrontSkus().contains("c27"));
  }

  @Test
  public void testGetBackSkus() {
    assertTrue(pr1.getBackSkus().contains("b24"));
    assertTrue(pr1.getBackSkus().contains("d21"));
    assertTrue(pr1.getBackSkus().contains("b25"));
    assertTrue(pr1.getBackSkus().contains("d27"));

  }

  @Test
  public void testGetAllSkus() {
    assertTrue(pr1.getAllSkus().contains("a24"));
    assertTrue(pr1.getAllSkus().contains("c21"));
    assertTrue(pr1.getAllSkus().contains("a25"));
    assertTrue(pr1.getAllSkus().contains("c27"));
    assertTrue(pr1.getAllSkus().contains("b24"));
    assertTrue(pr1.getAllSkus().contains("d21"));
    assertTrue(pr1.getAllSkus().contains("b25"));
    assertTrue(pr1.getAllSkus().contains("d27"));
  }

  @Test
  public void testGetFasciaLists() {
    Fascia f1 = pr1.getFasciaLists().get(0);
    assertEquals("Blue", f1.getColour());
    Fascia f2 = pr1.getFasciaLists().get(1);
    assertEquals("S", f2.getModel());
    assertFalse(f1.getOrientation());
    assertTrue(f2.getOrientation());
    Fascia f3 = pr1.getFasciaLists().get(2);
    assertEquals("Red", f3.getColour());
    Fascia f4 = pr1.getFasciaLists().get(3);
    assertEquals("S", f4.getModel());
    assertFalse(f3.getOrientation());
    assertTrue(f4.getOrientation());
    Fascia f5 = pr1.getFasciaLists().get(4);
    assertEquals("Blue", f5.getColour());
    Fascia f6 = pr1.getFasciaLists().get(5);
    assertEquals("SEL", f6.getModel());
    assertFalse(f5.getOrientation());
    Fascia f7 = pr1.getFasciaLists().get(6);
    assertTrue(f6.getOrientation());
    assertEquals("Silver", f7.getColour());
    Fascia f8 = pr1.getFasciaLists().get(7);
    assertEquals("S", f8.getModel());
    assertFalse(f7.getOrientation());
    assertTrue(f8.getOrientation());
  }

  @Test
  public void testGetUnsequencedFascia() {

    assertTrue(pr1.getUnsequencedFascia().contains("a00"));
    assertTrue(pr1.getUnsequencedFascia().contains("a11"));

  }

  @Test
  public void testAddUnsequencedFascia() {
    pr1.addUnsequencedFascia("a22");

    assertTrue(pr1.getUnsequencedFascia().contains("a00"));
    assertTrue(pr1.getUnsequencedFascia().contains("a11"));
    assertTrue(pr1.getUnsequencedFascia().contains("a22"));

  }

  @Test
  public void testRenew() {
    pr1.renew();
    assertTrue(pr1.getUnsequencedFascia().isEmpty());
  }

  public void testGetLocations() {
    assertTrue(pr1.getLocations().get(0).equals("A001"));
    assertTrue(pr1.getLocations().get(1).equals("A002"));
    assertTrue(pr1.getLocations().get(2).equals("B001"));
    assertTrue(pr1.getLocations().get(3).equals("B002"));
    assertTrue(pr1.getLocations().get(4).equals("C001"));
    assertTrue(pr1.getLocations().get(5).equals("C002"));
    assertTrue(pr1.getLocations().get(6).equals("D001"));
    assertTrue(pr1.getLocations().get(7).equals("D002"));
  }
}
