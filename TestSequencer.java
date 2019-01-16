package warehousing;

import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestSequencer {
  Sequencer sequencer;
  private PickingRequest pr1;

  @Before
  public void setUp() {
    sequencer = new Sequencer("Alice");
  }

  @Test
  public void testSequence() throws IOException {
    ArrayList<String> sku1 = new ArrayList<String>();
    sku1.add("a24");
    sku1.add("b24");
    Order o1 = new Order("Blue", "S", sku1, 1);
    ArrayList<Order> order = new ArrayList<Order>();  
    order.add(o1);
    ArrayList<String> sku2 = new ArrayList<String>();
    sku2.add("c21");
    sku2.add("d21");
    Order o2 = new Order("Red", "S", sku2, 2);
    order.add(o2);
    ArrayList<String> sku3 = new ArrayList<String>();
    sku3.add("a25");
    sku3.add("b25");
    Order o3 = new Order("Blue", "SEL", sku3, 3);
    order.add(o3);
    ArrayList<String> sku4 = new ArrayList<String>();
    sku4.add("c27");
    sku4.add("d27");
    Order o4 = new Order("Silver", "S", sku4, 4);
    order.add(o4);
    pr1 = new PickingRequest(order, 1);
    sequencer.setCurrWork(pr1);
    sequencer.setCheckResult(false);
    for (int i = 0; i < sequencer.getCurrWork().getBackSkus().size(); i++) {
      String input = sequencer.getCurrWork().getAllSkus().get(i);
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in); 
      sequencer.sequenceCheck();
      assertFalse(sequencer.getCheckResult());
      sequencer.sequenceCheck();
    }
    for (int i = 0; i < sequencer.getCurrWork().getFrontSkus().size(); i++) {
      String input = sequencer.getCurrWork().getAllSkus().get(i);
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in); 
      sequencer.sequenceCheck();
      assertFalse(sequencer.getCheckResult());
      sequencer.sequenceCheck();
    }
  }

}
