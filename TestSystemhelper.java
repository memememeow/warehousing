package warehousing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TestSystemhelper {
  Systemhelper sh = new Systemhelper(30, 5);
  HashMap<String, Integer> warehouseTest;
  HashMap<String, String> locationTest;
  HashMap<String, ArrayList<String>> orderTest;

  /**
   * Set up the path of the files for testing before testing.
   */
  @Before
  public void setUp() {
    sh.setInitial("Testing/initialT.csv");
    sh.setTranslate("Testing/translationT.csv");
    sh.setTraverse("Testing/traversal_tableT.csv");
  }

  @Test
  public void testSystemhelper() {
    assertEquals(30, sh.getDefaultAmount());
    assertEquals(5, sh.getReplaceAmount());
  }

  @Test
  public void testInitialize() throws ClassNotFoundException, IOException {
    sh.initialize();
    warehouseTest = sh.getWarehouse();
    assertNotNull(warehouseTest);
    assertEquals(5, warehouseTest.size());
    assertTrue(warehouseTest.containsKey("A000"));
    assertEquals((int) warehouseTest.get("A000"), 6);
    assertTrue(warehouseTest.containsKey("A001"));
    assertEquals((int) warehouseTest.get("A001"), 30);
    assertTrue(warehouseTest.containsKey("A002"));
    assertEquals((int) warehouseTest.get("A002"), 30);
    assertTrue(warehouseTest.containsKey("A003"));
    assertEquals((int) warehouseTest.get("A003"), 23);
    assertTrue(warehouseTest.containsKey("A010"));
    assertEquals((int) warehouseTest.get("A010"), 13);
  }

  @Test
  public void testLocationMap() throws ClassNotFoundException, IOException {
    sh.locationMap();
    locationTest = sh.getLocationMap();
    assertNotNull(locationTest);
    assertEquals(5, locationTest.size());
    assertTrue(locationTest.containsKey("A000"));
    assertEquals(locationTest.get("A000"), "1");
    assertTrue(locationTest.containsKey("A001"));
    assertEquals(locationTest.get("A001"), "2");
    assertTrue(locationTest.containsKey("A002"));
    assertEquals(locationTest.get("A002"), "3");
    assertTrue(locationTest.containsKey("A003"));
    assertEquals(locationTest.get("A003"), "4");
    assertTrue(locationTest.containsKey("A010"));
    assertEquals(locationTest.get("A010"), "5");
  }

  @Test
  public void testOrderReader() throws ClassNotFoundException, IOException {
    sh.orderReader();
    orderTest = sh.getOrderTable();
    assertNotNull(orderTest);
    assertEquals(4, orderTest.size());
    assertTrue(orderTest.containsKey("S White"));
    ArrayList<String> sku1 = new ArrayList<String>();
    sku1.add("SKU1");
    sku1.add("SKU2");
    assertEquals(orderTest.get("S White"), sku1);
    assertTrue(orderTest.containsKey("SE White"));
    ArrayList<String> sku2 = new ArrayList<String>();
    sku2.add("SKU3");
    sku2.add("SKU4");
    assertEquals(orderTest.get("SE White"), sku2);
    assertTrue(orderTest.containsKey("SES White"));
    ArrayList<String> sku3 = new ArrayList<String>();
    sku3.add("SKU5");
    sku3.add("SKU6");
    assertEquals(orderTest.get("SES White"), sku3);
    assertTrue(orderTest.containsKey("SEL White"));
    ArrayList<String> sku4 = new ArrayList<String>();
    sku4.add("SKU7");
    sku4.add("SKU8");
    assertEquals(orderTest.get("SEL White"), sku4);
  }

  @Test(expected = java.io.IOException.class)
  public void initializeshouldthrowioexception() throws IOException {
    sh.setInitial("Testing/initial.csv");
    sh.initialize();
  }

  @Test(expected = java.io.IOException.class)
  public void locationmapshouldthrowioexception() throws IOException {
    sh.setTraverse("Testing/traversal_table.csv");
    sh.locationMap();
  }

  @Test(expected = java.io.IOException.class)
  public void orderreadershouldthrowioexception() throws IOException {
    sh.setTranslate("Testing/translation.csv");
    sh.orderReader();
  }

  @Test
  public void testChangeFasciaAmoun() throws ClassNotFoundException, IOException {
    sh.initialize();
    warehouseTest = sh.getWarehouse();
    sh.changeFasciaAmount("B000", "pick");
    sh.changeFasciaAmount("A000", "pick");
    assertEquals((int) warehouseTest.get("A000"), 30);
    sh.changeFasciaAmount("A003", "pick");
    assertEquals((int) warehouseTest.get("A003"), 22);
    sh.changeFasciaAmount("A010", "replenish");
    assertEquals((int) warehouseTest.get("A010"), 30);
  }

  @Test
  public void testSetDefaultAmountAndReplaceAmount() {
    sh.setDefaultAmount(40);
    sh.setReplaceAmount(10);
    assertEquals(40, sh.getDefaultAmount());
    assertEquals(10, sh.getReplaceAmount());
  }
}
