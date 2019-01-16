package warehousing;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Stimulate the real truck of the warehousing company. Stored all information of real truck.
 */
public class Truck {
  /** The volume of the Truck. */
  private int volume = 160;

  /** The whole content of the Truck. */
  private ArrayList<ArrayList<String>> contentTruck = new ArrayList<ArrayList<String>>();

  /** All PickingRequest in this Truck. */
  private ArrayList<PickingRequest> pickingRequests = new ArrayList<PickingRequest>();

  /** The status of this Truck. */
  private boolean status;

  /** The id of Picking Request which is currently last one loading on the truck. */
  private int lastREid;

  /**
   * Initialize a Truck.
   */
  protected Truck() {
    this.status = true;
    this.lastREid = 0;
  }

  /**
   * Load 8 fascia with correct order .
   * 
   * @param currPickingRequest A picking request be loaded to this Truck.
   * @param frontFascia All front fascia be loaded to this Truck.
   * @param backFascia All back fascia be loaded to this Truck.
   */
  protected void loadFascia(PickingRequest currPickingRequest, ArrayList<String> frontFascia,
      ArrayList<String> backFascia) {
    this.pickingRequests.add(currPickingRequest);
    this.contentTruck.add(backFascia);
    this.contentTruck.add(frontFascia);
    this.lastREid = currPickingRequest.getId();
    int currAmont = this.contentTruck.size() * 4;
    if (currAmont >= this.volume) {
      this.status = false;
    }
  }

  /**
   * Get all sku of fascia loaded in this Truck.
   * 
   * @return the contentTruck
   */
  protected ArrayList<ArrayList<String>> getContentTruck() {
    return contentTruck;
  }

  /**
   * Get if this Truck is full or not.
   * 
   * @return the status
   */
  protected boolean getStatus() {
    return status;
  }

  /**
   * Get the PickingRequest's id which is the currently last one be loaded on this Truck.
   * 
   * @return the last loaded picking request id.
   */
  protected int getLastREid() {
    return lastREid;
  }

  /**
   * Get all PickingRquest be stored in this Truck.
   * 
   * @return all PickingRequests.
   */
  protected ArrayList<PickingRequest> getPickingContent() {
    return this.pickingRequests;
  }

  /**
   * Let this Truck leave warehouse. If this truck is not full, it will stay at warehouse.
   */
  protected void leaveTruck() {
    if (!(this.status)) {
      SystemManage.logger.log(Level.FINE, "A truck leaves.");
    } else {
      SystemManage.logger.log(Level.WARNING, "This Truck is not full!");
    }
  }
}
