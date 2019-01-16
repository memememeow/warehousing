package warehousing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Read traversal_table.csv, translation.csv and initial.csv and store all the information in the
 * SystemManage.
 *
 */
public class Systemhelper {

  private String traverse = "traversal_table.csv";
  private String translate = "translation.csv";
  private String initial = "initial.csv";
  private HashMap<String, Integer> warehouse; // store location and amount
  private HashMap<String, String> locationMap; // store location and sku
  private HashMap<String, ArrayList<String>> orderTable; // store color, model and skus
  private int defaultAmount;
  private int replaceAmount;

  /**
   * Initialize a new Systemhelper.
   */
  protected Systemhelper(int defaultAmount, int replaceAmount) {
    this.defaultAmount = defaultAmount;
    this.replaceAmount = replaceAmount;
  }

  /**
   * Read translation.csv and initial.csv and store location and the amount of the fascias inside
   * warehouse.
   */
  protected void initialize() throws IOException {

    this.warehouse = new HashMap<String, Integer>();
    String line;
    String location;
    BufferedReader traverseReader = new BufferedReader(new FileReader(this.traverse));
    while ((line = traverseReader.readLine()) != null) {
      String[] temp = line.split(",");
      location = temp[0] + temp[1] + temp[2] + temp[3];
      warehouse.put(location, defaultAmount);
    }
    traverseReader.close();
    BufferedReader initialReader = new BufferedReader(new FileReader(this.initial));

    while ((line = initialReader.readLine()) != null) {
      String[] initialRow = line.split(",");
      location = initialRow[0] + initialRow[1] + initialRow[2] + initialRow[3];
      int amount = Integer.parseInt(initialRow[4]);
      if (warehouse.containsKey(location)) {
        warehouse.put(location, amount);
      }
    }
    initialReader.close();
  }

  /**
   * Read traversal_table.csv and store the location and sku inside LocationMap.
   */
  protected void locationMap() throws IOException {
    this.locationMap = new HashMap<String, String>();
    String line;
    BufferedReader buffer = new BufferedReader(new FileReader(this.traverse));
    while ((line = buffer.readLine()) != null) {
      String[] order = line.split(",");
      String location = order[0] + order[1] + order[2] + order[3];
      String sku = order[4];
      locationMap.put(location, sku);
    }
    buffer.close();
  }

  /**
   * Read translation.csv and store the model and colour of a fascia as a key and the sku of the
   * front and back fascia as the value inside OrderTable.
   *
   */
  protected void orderReader() throws IOException {
    this.orderTable = new HashMap<String, ArrayList<String>>();
    String line;
    BufferedReader buffer = new BufferedReader(new FileReader(this.translate));
    line = buffer.readLine();
    while ((line = buffer.readLine()) != null) {
      String[] fascia = line.split(",");
      String modandcol = fascia[1] + " " + fascia[0];
      ArrayList<String> sku = new ArrayList<String>();
      sku.add(fascia[2]);
      sku.add(fascia[3]);
      orderTable.put(modandcol, sku);
    }
    buffer.close();
  }

  /**
   * Change the state of the warehouse when the picker picks or replenisher replenishes.
   **/
  protected void changeFasciaAmount(String location, String action) {
    if (this.warehouse.get(location) != null) {
      if (action.equals("pick")) {
        int newAmount = this.warehouse.get(location) - 1;
        SystemManage.logger.log(Level.FINE, "Amount of fascia delete 1 at " + location);
        if (newAmount == this.replaceAmount) {
          SystemManage.logger.log(Level.FINE,
              "Need replenish fascia at " + location + "Replenish fascia at " + location);
          newAmount = this.defaultAmount;
        }
        this.warehouse.put(location, newAmount);
      } else if (action.equals("replenish")) {
        int newAmount = this.warehouse.get(location);
        newAmount = this.defaultAmount;
        this.warehouse.put(location, newAmount);
        SystemManage.logger.log(Level.FINE, "Replenish fascia at " + location);
      }
    } else {
      SystemManage.logger.log(Level.WARNING, "Invalid loaction.");
    }
  }

  /**
   * Change the path of the traversal_table.csv
   * 
   * @param traverse the traverse to set
   */
  protected void setTraverse(String traverse) {
    this.traverse = traverse;
  }

  /**
   * Change the path of the translation.csv
   * 
   * @param translate the translate to set
   */
  protected void setTranslate(String translate) {
    this.translate = translate;
  }


  /**
   * Change the path of the initial.csv
   * 
   * @param initial the initial to set
   */
  protected void setInitial(String initial) {
    this.initial = initial;
  }

  /**
   * Return the warehouse.
   * 
   * @return the warehouse
   */
  protected HashMap<String, Integer> getWarehouse() {
    return warehouse;
  }

  /**
   * Return the LocaMap.
   * 
   * @return the locaMap
   */
  protected HashMap<String, String> getLocationMap() {
    return locationMap;
  }

  /**
   * Return the LocaMap.
   * 
   * @return the locaMap
   */
  protected HashMap<String, ArrayList<String>> getOrderTable() {
    return orderTable;
  }

  /**
   * Return the default Amount of the fascias.
   * 
   * @return the defaultAmount
   */
  protected int getDefaultAmount() {
    return defaultAmount;
  }

  /**
   * Set the default amount.
   * 
   * @param defaultAmount the defaultAmount to set
   */
  protected void setDefaultAmount(int defaultAmount) {
    this.defaultAmount = defaultAmount;
  }

  /**
   * Return the minimal amount for replenishing.
   * 
   * @return the replaceAmount
   */
  protected int getReplaceAmount() {
    return replaceAmount;
  }

  /**
   * Set the minimal amount for replenishing.
   * 
   * @param replaceAmount the replaceAmount to set
   */
  protected void setReplaceAmount(int replaceAmount) {
    this.replaceAmount = replaceAmount;
  }
}
