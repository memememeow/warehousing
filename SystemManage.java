package warehousing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import WarehousePicking.java;

/**
 * A system for a warehousing company to handle orders for minivan bumper fascia.
 *
 */
public class SystemManage implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * All orders received by system.
   */
  private ArrayList<Order> inputOrders;
  /**
   * All picking request received by system.
   */
  private ArrayList<PickingRequest> inputPickingRequest;
  /**
   * All picking request loaded on the truck.
   */
  private ArrayList<PickingRequest> loadedPickingRequest;
  /**
   * The truck where loaders working on now.
   */
  private Truck currTruck;
  /**
   * The picking requests that are waiting for pick.
   */
  private ArrayList<PickingRequest> pickingList;
  /**
   * The picking request that are waiting for sequence.
   */
  private ArrayList<PickingRequest> sequencingList;
  /**
   * The sequencers who finish current work.
   */
  private ArrayList<Sequencer> sequencerFinishWork;
  /**
   * System helper for system to read files and change the status of warehouse.
   */
  private Systemhelper systemHelper;
  /**
   * Create or chose worker for system.
   */
  private WorkerFactory workerFactory;
  /**
   * Log all the status of system.
   */
  protected static final Logger logger = Logger.getLogger(SystemManage.class.getName());
  /**
   * Print information for logger.
   */
  private static final Handler consoleHandler = new ConsoleHandler();
   /**
   * Store sku number for corresponding location.
   */
  private HashMap<String, String> locationMap;
  /**
   * Store sku number for corresponding color and model.
   */
  private HashMap<String, ArrayList<String>> orderTable;



  /**
   * Initialize a system.
   * 
   * @throws IOException
   * 
   * @throws IOException .
   * @throws SecurityException .
   **/
  protected SystemManage() throws IOException {
    inputOrders = new ArrayList<Order>();
    inputPickingRequest = new ArrayList<PickingRequest>();
    loadedPickingRequest = new ArrayList<PickingRequest>();
    currTruck = new Truck();
    pickingList = new ArrayList<PickingRequest>();
    sequencingList = new ArrayList<PickingRequest>();
    sequencerFinishWork = new ArrayList<Sequencer>();
    systemHelper = new Systemhelper(30, 5);
    systemHelper.initialize();
    systemHelper.locationMap();
    systemHelper.orderReader();
    workerFactory = new WorkerFactory();

    locationMap = this.systemHelper.getLocationMap();
    orderTable = this.systemHelper.getOrderTable();

    logger.setLevel(Level.ALL);
    consoleHandler.setLevel(Level.ALL);
    consoleHandler.setFormatter(new SimpleFormatter());
    FileHandler fileHandler = new FileHandler("log.txt");
    fileHandler.setFormatter(new SimpleFormatter());
    fileHandler.setLevel(Level.ALL);
    logger.addHandler(consoleHandler);
    logger.addHandler(fileHandler);
  }

  /**
   * Create the Order and store this order in system. If it is an invalid order, system do nothing.
   * 
   * @param model The model of this order.
   * @param colour The color of this order.
   * @throws IOException
   */
  protected void createOrder(String model, String colour) throws IOException {
    ArrayList<String> skus;
    skus = this.orderTable.get(model + " " + colour);
    if (skus != null) {
      int lastId = this.inputOrders.size();
      Order order = new Order(colour, model, skus, lastId + 1);
      this.inputOrders.add(order);
      SystemManage.logger.log(Level.FINE,
          "New order " + model + " " + colour + " is stored into the system");
      if (this.inputOrders.size() % 4 == 0 && this.inputOrders.size() > 0) {
        this.createPickingRequest();
      }
    } else {
      SystemManage.logger.log(Level.SEVERE,
          "Order " + model + " " + colour + " cannot be found inside the warehouse");
    }
  }

  /**
   * Create a picking request and store this picking request in system.
   * 
   * @throws IOException
   */
  protected void createPickingRequest() throws IOException {
    int size = this.inputOrders.size();
    ArrayList<Order> temp = new ArrayList<Order>();
    for (int i = size - 4; i < size; i++) {
      temp.add(this.inputOrders.get(i));
    }
    int lastId = this.inputPickingRequest.size();
    PickingRequest pickingRequest = new PickingRequest(temp, lastId + 1);
    this.inputPickingRequest.add(pickingRequest);
    this.pickingList.add(pickingRequest);
  }

  /**
   * Create a worker for system and give him a current work(PickingRequest). If there is no work for
   * this worker, system will do nothing.
   * 
   * @param type The type of this worker.
   * @param name The name of this worker.
   */
  protected void setWork(String type, String name) {
    if (type.equals("Picker")) {
      if (this.pickingList.size() > 0) {
        PickingRequest curr = this.pickingList.remove(0);
        ArrayList<String> pickingLocations = WarehousePicking.optimize(curr.getAllSkus())；
        curr.setLocations(pickingLocations);
        workerFactory.getWorker(type, name).setCurrWork(curr);
        SystemManage.logger.log(Level.FINE, "Picker " + name + " is ready.");
      } else {
        SystemManage.logger.log(Level.WARNING, "No more work for picker " + name);
      }
    } else if (type.equals("Sequencer")) {
      if (this.sequencingList.size() > 0) {
        PickingRequest curr = this.sequencingList.remove(0);
        workerFactory.getWorker(type, name).setCurrWork(curr);
        SystemManage.logger.log(Level.FINE, "Sequencer " + name + " is ready.");
      } else {
        SystemManage.logger.log(Level.WARNING, "No more work for sequencer " + name);
      }
    } else if (type.equals("Loader")) {
      Sequencer temp = this.getNextLoadingFascia();
      if (temp instanceof Sequencer) {
        PickingRequest curr = temp.getCurrWork();
        this.sequencerFinishWork.remove(temp);
        workerFactory.getWorker(type, name).setCurrWork(curr);
        SystemManage.logger.log(Level.FINE, "Loader " + name + " is ready.");
      } else {
        SystemManage.logger.log(Level.WARNING, "No more work for loader " + name);
      }
    } else {
      SystemManage.logger.log(Level.WARNING, "There is no such type of worker");
    }
  }

  /**
   * Let a picker pick 8 fascia one by one. If this picker is not ready, system will do nothing.
   * 
   * @param name The name of this picker.
   */
  protected void pickFascia(String name) {
    boolean check = false;
    for (BarcodeReader picker : this.workerFactory.getPickerList()) {
      if (picker.getName().equals(name)) {
        check = true;
        int currWorkNum = picker.getCurrWork().getUnsequencedFascia().size();
        if (currWorkNum < 8) {
           String pickLocation = picker.getCurrWork().getLocations().get(currWorkNum);
           String sku = this.locationMap.get(pickLocation);
          SystemManage.logger.log(Level.FINE,
              "Picker " + picker.getName() + " go to " + pickLocation);
          ((Picker) picker).setCheckResult(true);
          ((Picker) picker).pickSingleFascia(pickLocation, sku);
          this.systemHelper.changeFasciaAmount(pickLocation, "pick");
          picker.getCurrWork().addUnsequencedFascia(sku);
        } else {
          SystemManage.logger.log(Level.WARNING,
              "No more fascia for " + picker.getName() + " to pick.");
        }
      }
    }
    if (!check) {
      SystemManage.logger.log(Level.WARNING, "No such picker exists!");
    }
  }

  /**
   * Let one picker send 8 fascia to marshalling area to be sequenced. If this picker not exists or
   * not finish picking work, system will do nothing.
   * 
   * @param name The name of this picker.
   */
  protected void sendFasciaSequence(String name) {
    boolean check = false;
    for (BarcodeReader picker : this.workerFactory.getPickerList()) {
      if (picker.getName().equals(name)) {
        check = true;
        if (picker.getCurrWork().getUnsequencedFascia().size() == 8) {
          this.sequencingList.add(picker.getCurrWork());
          SystemManage.logger.log(Level.FINE, "Picker " + picker.getName()
              + " send picking request " + picker.getCurrWork().getId() + " to marshalling area.");
          picker.setCurrWork(null);
        } else {
          SystemManage.logger.log(Level.WARNING,
              "Picker " + picker.getName() + " not finish picking.");
        }
      }
    }
    if (!check) {
      SystemManage.logger.log(Level.WARNING, "No such picker exist.");
    }
  }


  /**
   * Let one sequencer sequence the 8 fascia. If this sequencer not ready, system will do nothing.
   * 
   * @param name The name of this sequencer.
   */
  protected void sequenceFascia(String name) {
    boolean check = false;
    for (BarcodeReader sequencer : this.workerFactory.getSequencerList()) {
      if (sequencer.getName().equals(name)) {
        check = true;
        // ((Sequencer) sequencer).sequenceCheck();
        ((Sequencer) sequencer).setCheckResult(true);
        if (((Sequencer) sequencer).getCheckResult()) { 
          SystemManage.logger.log(Level.FINE, "Finish sequencing.");
          this.sequencerFinishWork.add((Sequencer) sequencer);
        } else {
          this.pickingList.add(0, sequencer.getCurrWork());
          SystemManage.logger.log(Level.SEVERE, "Find wrong fascia. Processing to repick.");
          sequencer.setCurrWork(null);
        }
      }
    }
    if (!check) {
      SystemManage.logger.log(Level.WARNING, "No such sequencer exist.");
    }
  }

  /**
   * Let a sequencer rescan the 8 fascia. If this sequencer not finish his sequencing work or not
   * exist, system will do nothing.
   * 
   * @param name The name of this sequencer.
   */
  protected void sequencerRescan(String name) {
    boolean check = false;
    for (Sequencer sequencer : this.sequencerFinishWork) {
      if (sequencer.getName().equals(name)) {
        check = true;
        // sequencer.sequenceCheck(); 
        // sequencer.setCheckResult(true);
        if (sequencer.getCheckResult()) {
          SystemManage.logger.log(Level.FINE, "Finish rescan " + sequencer.getCurrWork().getId());
        } else {
          this.pickingList.add(0, sequencer.getCurrWork());
          SystemManage.logger.log(Level.SEVERE, "Find wrong fascia. Processing to repick.");
          sequencer.setCurrWork(null);
        }
      }
    }
    if (!check) {
      SystemManage.logger.log(Level.WARNING, "No such sequencer exist.");
    }
  }

  /**
   * Check if there is a expected picking request ready to be loaded.
   * 
   * @return A sequencer whose current work picking request is ready to be loaded.
   */
  protected Sequencer getNextLoadingFascia() {
    boolean check = false;
    for (int i = 0; i < this.sequencerFinishWork.size(); i++) {
      Sequencer temp = this.sequencerFinishWork.get(i);
      if (temp.getCurrWork().getId() == this.currTruck.getLastREid() + 1) {
        check = true;
        return temp;
      }
    }
    if (!check) {
      int expectedId = this.currTruck.getLastREid() + 1;
      SystemManage.logger.log(Level.WARNING,
          "Waiting for the correct Picking Request " + expectedId);
    }
    return null;
  }


  /**
   * Let a loader first check 8 fascia. If this loader is not ready or not exists, system will do
   * nothing.
   * 
   * @param name The name of this loader.
   */
  protected void loadFascia(String name) {
    this.checkTruckStatus();
    boolean check = false;
    for (BarcodeReader loader : this.workerFactory.getLoaderList()) {
      if (loader.getName().equals(name)) {
        check = true;
        if (loader.getCurrWork() instanceof PickingRequest) {
          if (loader.getCurrWork().getId() == this.currTruck.getLastREid() + 1){
            loader.setCheckResult(true); 
            // ((Loader) loader).checkFasciaOrder();
            if (loader.getCheckResult()) {
              SystemManage.logger.log(Level.FINE,
                  "First check " + loader.getCurrWork().getId() + " okay.");
            } else {
              this.pickingList.add(0, loader.getCurrWork());
              SystemManage.logger.log(Level.SEVERE, "Find wrong fascia, Processing repick.");
              loader.setCurrWork(null);
            }
          } else{
            SystemManage.logger.log(Level.WARNING, "Loader " + loader.getName() + " can not load this picking request");
          }
        } else {
          SystemManage.logger.log(Level.WARNING, "Loader " + loader.getName() + " is not ready");
        }
      }
    }
    if (!check) {
      SystemManage.logger.log(Level.WARNING, "No such loader exist.");
    }
  }

  /**
   * Let a loader rescan the 8 fascia and finally load them.
   * 
   * @param name The name of this loader.
   */
  protected void loaderRescan(String name) {
    boolean check = false;
    for (BarcodeReader loader : this.workerFactory.getLoaderList()) {
      if (loader.getName().equals(name)) {
        check = true;
        if (loader.getCurrWork() instanceof PickingRequest) {
          // loader.setCheckResult(true); 
          // ((Loader) loader).checkFasciaOrder();
          if (loader.getCheckResult()) {
            ((Loader) loader).loadTruck(this.currTruck);
            SystemManage.logger.log(Level.FINE, "Finish load " + loader.getCurrWork().getId());
            this.loadedPickingRequest.add(loader.getCurrWork());
            loader.setCurrWork(null);
          } else {
            this.pickingList.add(0, loader.getCurrWork());
            SystemManage.logger.log(Level.SEVERE,
                "Fascia be placed in wrong order. Proceeding re-sequencing.");
            loader.setCurrWork(null);
          }
        } else {
          SystemManage.logger.log(Level.WARNING,
              "No fascia for loader " + loader.getName() + " to load");
        }
      }
    }
    if (!check) {
      SystemManage.logger.log(Level.WARNING, "No such loader exist.");
    }
  }


  /**
   * Replenish the fascia at one location.
   * 
   * @param location The location that be replenished.
   */
  protected void replenishFascia(String location) {
    this.systemHelper.changeFasciaAmount(location, "replenish");
  }

  /**
   * Check current working truck status. If the truck is full, let this truck leave.
   */
  protected void checkTruckStatus() {
    if (!currTruck.getStatus()) {
      currTruck.leaveTruck();
      currTruck = new Truck();
    }
  }


  /**
   * Writing the orders in currTruck to orders.csv.
   * 
   * @throws IOException
   */
  protected void writeOrders() throws IOException {

    File ordersFile = new File("orders.csv");

    BufferedWriter ordersWriter = new BufferedWriter(new FileWriter(ordersFile));

    for (PickingRequest pickingRequest : this.loadedPickingRequest) {
      for (Order order : pickingRequest.getOrdersList()) {
        String colour = order.getColour();
        String model = order.getModel();

        ordersWriter.write(model + "," + colour);
        ordersWriter.newLine();
      }
    }
    SystemManage.logger.log(Level.FINE, "The system is generating the order.csv.");
    ordersWriter.close();
  }


  /**
   * Writes the final.csv at the end of the program
   * 
   * @throws IOException
   */
  protected void writeFinal() throws IOException {
    String row;
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter("final.csv"));
    for (Map.Entry<String, Integer> entries : this.systemHelper.getWarehouse().entrySet()) {
      String location = entries.getKey();
      int amount = entries.getValue();
      if (amount != this.systemHelper.getDefaultAmount()) {
        row = location.charAt(0) + "," + location.charAt(1) + "," + location.charAt(2) + ","
            + location.charAt(3) + "," + Integer.toString(amount);
        fileWriter.write(row);
        fileWriter.newLine();
      }
    }
    SystemManage.logger.log(Level.FINE, "The system is generating the final.csv.");
    fileWriter.close();
  }


  /**
   * Get the system helper of system.
   * 
   * @return the systemHelper
   */
  protected Systemhelper getSystemHelper() {
    return this.systemHelper;
  }

  /**
   * Get the list of all the input orders (just used for testing).
   * 
   * @return the inputOrders
   */
  protected ArrayList<Order> getInputOrders() {
    return inputOrders;
  }

  /**
   * Get the list of all picking requests (just used for testing).
   * 
   * @return the inputPickingRequest
   */
  protected ArrayList<PickingRequest> getInputPickingRequest() {
    return inputPickingRequest;
  }

  /**
   * Get the list of loaded picking request (just used for testing).
   * 
   * @return the loadedPickingRequest
   */
  protected ArrayList<PickingRequest> getLoadedPickingRequest() {
    return loadedPickingRequest;
  }

  /**
   * Add a new loaded picking request into the list (just used for testing).
   * 
   * @return the loadedPickingRequest
   */
  protected void setLoadedPickingRequest(PickingRequest pickingR) {
    this.loadedPickingRequest.add(pickingR);
  }

  /**
   * Get the list of the current truck (just used for testing).
   * 
   * @return the currTruck
   */
  protected Truck getCurrTruck() {
    return currTruck;
  }

  /**
   * Get the list of the picking requests which is waiting for picking (just used for testing).
   * 
   * @return the pickingList
   */
  protected ArrayList<PickingRequest> getPickingList() {
    return pickingList;
  }

  /**
   * Get the list of the picking requests which is waiting for sequencing (just used for testing).
   * 
   * @return the sequencingList
   */
  protected ArrayList<PickingRequest> getSequencingList() {
    return sequencingList;
  }

  /**
   * Get the list of the sequencers who finished the work (just used for testing).
   * 
   * @return the sequencerFinishWork
   */
  protected ArrayList<Sequencer> getSequencerFinishWork() {
    return sequencerFinishWork;
  }

  /**
   * Get the WorkerFactory of the system (just used for testing).
   * 
   * @return the workerFactory
   */
  protected WorkerFactory getWorkerFactory() {
    return workerFactory;
  }

  /**
   * Add a sequencer into the sequencerFinishWork list (just used for testing).
   */
  protected void setSequencerFinishWork(Sequencer sequencer) {
    this.sequencerFinishWork.add(sequencer);
  }

}
