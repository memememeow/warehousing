package warehousing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestBarcodeReader {
  BarcodeReader br1;
  private PickingRequest pr1;


  @Before
  public void setUp() {
    br1 = new BarcodeReader("Lily");
  }

  @Test
  public void testSetCurrWork() throws IOException {
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
    pr1 = new PickingRequest(orderlist1, 1);
    br1.setCurrWork(pr1);

    Order order1 = br1.getCurrWork().getOrdersList().get(0);
    assertEquals(1, order1.getOrderNum());
    Order order2 = br1.getCurrWork().getOrdersList().get(1);
    assertEquals(2, order2.getOrderNum());
    Order order3 = br1.getCurrWork().getOrdersList().get(2);
    assertEquals(3, order3.getOrderNum());
    Order order4 = br1.getCurrWork().getOrdersList().get(3);
    assertEquals(4, order4.getOrderNum());
    assertTrue(order4.getFrontSku().contains("c27"));
    assertTrue(order4.getBackSku().contains("d27"));
    assertEquals("Blue", order1.getColour());
    assertEquals("Red", order2.getColour());
    assertEquals("Blue", order3.getColour());
    assertEquals("Silver", order4.getColour());
    assertTrue(order1.getFrontSku().contains("a24"));
    assertTrue(order1.getBackSku().contains("b24"));
    assertTrue(order2.getFrontSku().contains("c21"));
    assertTrue(order2.getBackSku().contains("d21"));
    assertTrue(order3.getFrontSku().contains("a25"));
    assertTrue(order3.getBackSku().contains("b25"));

  }

  @Test
  public void testIsAvailable() throws IOException {
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
    pr1 = new PickingRequest(orderlist1, 1);
    br1.setCurrWork(pr1);
    assertFalse(br1.isAvailable());
  }

  @Test
  public void testGetName() {
    assertEquals("Lily", br1.getName());
  }

  @Test
  public void testScanSku() {
    String input = "qr5";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    assertEquals("qr5", br1.scanSku());
  }

  @Test
  public void testGetCheckResult() {
    br1.setCheckResult(true);
    assertTrue(br1.getCheckResult());
  }
}
