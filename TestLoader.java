package warehousing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestLoader {
  Loader loader;
  private PickingRequest pr1;

  @Before
  public void setUp() {
    loader = new Loader("Ann");
  }

  @Test
  public void testloadTruck() throws IOException {
    final Truck t1 = new Truck();
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
    loader.setCurrWork(pr1);
    loader.loadTruck(t1);
    assertEquals(t1.getLastREid(), 1);

  }

  @Test
  public void testCheckFasciaOrder() throws IOException {
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
    loader.setCurrWork(pr1);
    loader.setCheckResult(false);
    for (int i = 0; i < loader.getCurrWork().getBackSkus().size(); i++) {
      String input = loader.getCurrWork().getAllSkus().get(i);
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);
      loader.checkFasciaOrder();
      assertFalse(loader.getCheckResult());
      loader.checkFasciaOrder();
    }
    for (int i = 0; i < loader.getCurrWork().getFrontSkus().size(); i++) {
      String input = loader.getCurrWork().getAllSkus().get(i);
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);
      loader.checkFasciaOrder();
      assertFalse(loader.getCheckResult());
      loader.checkFasciaOrder();
    }
  }
}
