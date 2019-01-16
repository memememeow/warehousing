package warehousing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TestSystemManage {
  SystemManage sm;
  private PickingRequest fakePr;
  private ArrayList<String> frontSku;
  private ArrayList<String> backSku;
  private PickingRequest fakePr2;

  /**
   * Set up necessary classes before tests.
   * 
   * @throws IOException may throw the exceptions when pickingRequest is created
   */
  @Before
  public void setUp() throws IOException {
    sm = new SystemManage();
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
    fakePr = new PickingRequest(o1, 563);
    fakePr2 = new PickingRequest(o1, 2);
    frontSku = new ArrayList<String>();
    frontSku.add("sku001");
    frontSku.add("sku003");
    frontSku.add("sku005");
    frontSku.add("sku007");
    backSku = new ArrayList<String>();
    backSku.add("sku002");
    backSku.add("sku004");
    backSku.add("sku006");
    backSku.add("sku008");

  }

  @Test
  public void testCreateOrderAndCreatePickingRequest() throws IOException {
    sm.createOrder("S", "White");
    assertEquals(sm.getInputOrders().size(), 1);
    assertEquals(sm.getInputOrders().get(0).getOrderNum(), 1);
    assertEquals(sm.getInputOrders().get(0).getFrontSku(), "1");
    assertEquals(sm.getInputOrders().get(0).getBackSku(), "2");
    sm.createOrder("SA", "White");
    sm.createOrder("SE", "White");
    sm.createOrder("SES", "White");
    assertEquals(sm.getInputPickingRequest().size(), 0);
    sm.createOrder("SEL", "White");
    assertEquals(sm.getInputPickingRequest().size(), 1);
    assertEquals(sm.getInputPickingRequest().get(0).getId(), 1);
    assertEquals(sm.getPickingList().size(), 1);
    assertEquals(sm.getPickingList().get(0), sm.getInputPickingRequest().get(0));
  }

  @Test
  public void testSetWork() throws IOException {
    sm.createOrder("S", "White");
    assertEquals(sm.getInputOrders().size(), 1);
    assertEquals(sm.getInputOrders().get(0).getOrderNum(), 1);
    assertEquals(sm.getInputOrders().get(0).getFrontSku(), "1");
    assertEquals(sm.getInputOrders().get(0).getBackSku(), "2");
    sm.createOrder("SA", "White");
    sm.createOrder("SE", "White");
    sm.createOrder("SES", "White");
    assertEquals(sm.getInputPickingRequest().size(), 0);
    sm.createOrder("SEL", "White");
    assertEquals(sm.getInputPickingRequest().size(), 1);
    assertEquals(sm.getInputPickingRequest().get(0).getId(), 1);
    assertEquals(sm.getPickingList().size(), 1);
    assertEquals(sm.getPickingList().get(0), sm.getInputPickingRequest().get(0));
    sm.setWork("Picker", "Picker1"); // correct
    assertNotNull(sm.getWorkerFactory().getPickerList().get(0).getCurrWork());
    assertEquals(sm.getWorkerFactory().getPickerList().get(0).getCurrWork().getId(), 1);
    sm.setWork("Picker", "Picker2"); // no more work
    // sm.setWork("Picker", "Picker3");
    sm.pickFascia("Picker1"); // correct
    assertEquals(29, (int) sm.getSystemHelper().getWarehouse().get("A000"));
    assertEquals(
        sm.getWorkerFactory().getPickerList().get(0).getCurrWork().getUnsequencedFascia().size(),
        1);
    sm.pickFascia("Picker1");
    assertEquals(
        sm.getWorkerFactory().getPickerList().get(0).getCurrWork().getUnsequencedFascia().size(),
        2);
    sm.pickFascia("Picker1");
    sm.pickFascia("Picker1");
    sm.pickFascia("Picker1");
    sm.pickFascia("Picker1");
    sm.sendFasciaSequence("Picker1"); // not finish picking
    sm.pickFascia("Picker1");
    sm.pickFascia("Picker1");
    assertEquals(0, sm.getSequencingList().size());
    sm.pickFascia("Picker1");
    assertEquals(0, sm.getSequencingList().size());
    sm.pickFascia("Picker2");
    sm.sendFasciaSequence("Picker1"); // correct
    assertNull(sm.getWorkerFactory().getPickerList().get(0).getCurrWork());
    assertEquals(sm.getSequencingList().size(), 1);
    assertEquals(sm.getSequencingList().get(0).getId(), 1);
    sm.sendFasciaSequence("Picker2");
    sm.setWork("Sequencer", "Sequencer1"); // correct
    assertNotNull(sm.getWorkerFactory().getSequencerList().get(0).getCurrWork());
    assertEquals(sm.getWorkerFactory().getSequencerList().get(0).getCurrWork().getId(), 1);
    sm.setWork("Sequencer", "Sequencer2"); // no more work
    ((Sequencer) sm.getWorkerFactory().getSequencerList().get(0)).setCheckResult(true);
    sm.sequenceFascia("Sequencer1");
    assertEquals(sm.getSequencerFinishWork().size(), 1);
    assertEquals(sm.getSequencerFinishWork().get(0).getName(), "Sequencer1");
    assertEquals(sm.getSequencerFinishWork().get(0).getCurrWork().getId(), 1);
    Sequencer sequencer2 = new Sequencer("Sequencer2");
    sequencer2.setCurrWork(fakePr);
    sequencer2.setCheckResult(false);
    sm.getWorkerFactory().addSequencer(sequencer2);
    sm.sequenceFascia("Sequencer2"); // pick wrong
    assertEquals(sm.getSequencerFinishWork().size(), 1);
    assertEquals(sm.getPickingList().size(), 1);
    assertNull(sm.getWorkerFactory().getSequencerList().get(1).getCurrWork());
    sm.sequenceFascia("Sequencer3");
    sm.setWork("Loader", "Loader1"); // correct
    assertEquals(sm.getSequencerFinishWork().size(), 0);
    assertNotNull(sm.getWorkerFactory().getLoaderList().get(0).getCurrWork());
    assertEquals(sm.getWorkerFactory().getLoaderList().get(0).getCurrWork().getId(), 1);
    sm.setWork("Loader", "Loader2"); // no work
    Sequencer sequencer3 = new Sequencer("sequencer3"); // test getNextLoadingFascia()
    sequencer3.setCurrWork(fakePr);
    sm.setSequencerFinishWork(sequencer3);
    assertNull(sm.getNextLoadingFascia());
    sm.getWorkerFactory().getLoaderList().get(0).setCheckResult(true); // for correct loading case
    sm.loadFascia("Loader1"); // correct
    Loader loader2 = new Loader("Loader2");
    sm.getWorkerFactory().addLoader(loader2);
    sm.loadFascia("Loader2"); // no such loader
    Loader loader3 = new Loader("Loader3");
    sm.loadFascia("Loader3"); // null pr not ready
    loader3.setCurrWork(fakePr); // incorrect pr id
    sm.getWorkerFactory().addLoader(loader3);
    sm.loadFascia("Loader3");
    loader3.setCurrWork(fakePr2); // correct pr id
    sm.getWorkerFactory().addLoader(loader3);
    loader3.setCheckResult(false); // wrong fascia
    sm.loadFascia("Loader3");
    sm.setWork("Whatever", "whatever");
    sm.setLoadedPickingRequest(fakePr);
    sm.writeOrders();
    String line;
    BufferedReader orderbuffer = new BufferedReader(new FileReader("orders.csv"));
    assertNotNull(line = orderbuffer.readLine());
    String[] order1 = line.split(",");
    String model1 = order1[0];
    String colour1 = order1[1];
    assertEquals(model1, "S");
    assertEquals(colour1, "Blue");
    assertNotNull(line = orderbuffer.readLine());
    String[] order2 = line.split(",");
    String model2 = order2[0];
    String colour2 = order2[1];
    assertEquals(model2, "S");
    assertEquals("RED", colour2);
    orderbuffer.close();
    sm.writeFinal();
    HashMap<String, Integer> finalMap = new HashMap<String, Integer>();
    BufferedReader finalReader = new BufferedReader(new FileReader("final.csv"));
    while ((line = finalReader.readLine()) != null) {
      String[] temp = line.split(",");
      String location = temp[0] + temp[1] + temp[2] + temp[3];
      int amount = Integer.parseInt(temp[4]);
      finalMap.put(location, amount);
    }
    finalReader.close();
    assertEquals(22, (int) finalMap.get("A000"));
  }

  @Test
  public void testSequencerRescan() {
    Sequencer sequencer1 = new Sequencer("Sequencer1");
    sequencer1.setCurrWork(fakePr);
    sequencer1.setCheckResult(true);
    sm.setSequencerFinishWork(sequencer1);
    Sequencer sequencer2 = new Sequencer("Sequencer2");
    sequencer2.setCurrWork(fakePr);
    sequencer2.setCheckResult(false);
    sm.setSequencerFinishWork(sequencer2);
    sm.sequencerRescan("Sequencer1");
    assertEquals(sm.getPickingList().size(), 0);
    sm.sequencerRescan("Sequencer2");
    assertEquals(sm.getPickingList().size(), 1);
    assertNull(sequencer2.getCurrWork());
    sm.sequencerRescan("Sequencer3");
  }

  @Test
  public void testLoaderRescan() {
    Loader loader1 = new Loader("Loader1");
    loader1.setCurrWork(fakePr);
    loader1.setCheckResult(false);
    sm.getWorkerFactory().addLoader(loader1);
    Loader loader2 = new Loader("Loader2");
    loader2.setCurrWork(fakePr);
    loader2.setCheckResult(true);
    sm.getWorkerFactory().addLoader(loader2);
    sm.loaderRescan("Loader1");
    assertEquals(sm.getLoadedPickingRequest().size(), 0);
    assertEquals(sm.getCurrTruck().getContentTruck().size(), 0);
    assertNull(loader1.getCurrWork());
    sm.loaderRescan("Loader2");
    assertEquals(sm.getLoadedPickingRequest().size(), 1);
    assertEquals(sm.getCurrTruck().getContentTruck().size(), 2);
    assertNull(loader2.getCurrWork());
    sm.loaderRescan("Loader2"); // no more fascia to load
    sm.loaderRescan("Loader3");
  }

  @Test
  public void testReplenishFascia() {
    sm.replenishFascia("A000");
    assertEquals(30, (int) sm.getSystemHelper().getWarehouse().get("A000"));
    sm.replenishFascia("A010");
    assertEquals(30, (int) sm.getSystemHelper().getWarehouse().get("A000"));
  }

  @Test
  public void testCheckTruckStatus() {
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.checkTruckStatus();
    assertEquals(sm.getCurrTruck().getContentTruck().size(), 2);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.getCurrTruck().loadFascia(fakePr, frontSku, backSku);
    sm.checkTruckStatus();
    assertEquals(sm.getCurrTruck().getContentTruck().size(), 0);
  }
}
