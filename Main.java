package warehousing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Read events that happened in real world.
 */
public class Main {

  /**
   * Read events.
   * 
   * @throws IOException .
   * @throws SecurityException .
   */
  public static void main(String[] args) throws SecurityException, IOException {

    String filePath = args[0];
    SystemManage system = new SystemManage();
    String line;

    try {
      BufferedReader buffer = new BufferedReader(new FileReader(filePath));

      while ((line = buffer.readLine()) != null) {

        String[] order = line.split(" ");
        SystemManage.logger.log(Level.INFO, line);

        if (order[0].equals("Order")) {
          system.createOrder(order[1], order[2]);
        } else if (order[0].equals("Picker")) {
          if (order[2].equals("ready")) {
            system.setWork(order[0], order[1]);
          } else if (order[2].equals("pick")) {
            system.pickFascia(order[1]);
          } else if (order[3].equals("Marshalling")) {
            system.sendFasciaSequence(order[1]);
          }
        } else if (order[0].equals("Sequencer")) {
          if (order[2].equals("ready")) {
            system.setWork(order[0], order[1]);
          } else if (order[2].equals("sequence")) {
            system.sequenceFascia(order[1]);
          } else if (order[2].equals("rescan")) {
            system.sequencerRescan(order[1]);
          }
        } else if (order[0].equals("Loader")) {
          if (order[2].equals("ready")) {
            system.setWork(order[0], order[1]);
          } else if (order[2].equals("load")) {
            system.loadFascia(order[1]);
          } else if (order[2].equals("rescan")) {
            system.loaderRescan(order[1]);
          }
        } else if (order[0].equals("Replenisher")) {
          if (order[2].equals("ready")) {
            System.out.println("Replenisher " + order[1] + " is ready.");
          } else if (order[2].equals("replenish")) {
            String location = order[3] + order[4] + order[5] + order[6];
            system.replenishFascia(location);
          }
        } else {
          SystemManage.logger.log(Level.WARNING, "Invalid conmand.");
        }
      }
      buffer.close();
    } catch (IOException error) {
      error.printStackTrace();
    }
    system.writeOrders();
    system.writeFinal();

  }

}
