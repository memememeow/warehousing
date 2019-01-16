package warehousing;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Consisted by 4 orders. Store information of all 8 fascia in those 4 orders.
 */
public class PickingRequest {
  /**
   * The id of this PickingRequest.
   */
  private int id;
  /**
   * The 4 orders stored in this PickingRequest.
   */
  private ArrayList<Order> ordersList;
  /**
   * The 4 front sku of 4 orders stored in this PickingRequest with correct order.
   */
  private ArrayList<String> frontSkus = new ArrayList<String>();
  /**
   * The 4 back sku of 4 orders stored in this PickingRequest with correct order.
   */
  private ArrayList<String> backSkus = new ArrayList<String>();
  /**
   * The 8 sku of 4 orders stored in this PickingRequest in correct order.
   */
  private ArrayList<String> allSkus = new ArrayList<String>();
  /**
   * The 8 fascia in the correct order.
   */
  private ArrayList<Fascia> fasciaLists = new ArrayList<Fascia>();
  /**
   * The fascia picked by Picker.
   */
  private ArrayList<String> unsequencedFascia;
  /**
   * The locations where the Picker should go to pick fascia.
   */
  private ArrayList<String> locations = new ArrayList<String>();

  /**
   * Initialize a PickingRequest.
   * 
   * @param orders The orders should be stroed in this PickingRequest.
   * @param id The id of this PickingRequest.
   * @throws IOException .
   */
  protected PickingRequest(ArrayList<Order> orders, int id) throws IOException {

    this.ordersList = orders;
    for (Order order : orders) {
      String colour = order.getColour();
      String model = order.getModel();
      String frontSku = order.getFrontSku();
      String backSku = order.getBackSku();
      Fascia fascia1 = new Fascia(colour, model, frontSku, false);
      Fascia fascia2 = new Fascia(colour, model, backSku, true);
      this.fasciaLists.add(fascia1);
      this.frontSkus.add(frontSku);
      this.fasciaLists.add(fascia2);
      this.backSkus.add(backSku);
      this.allSkus.add(frontSku);
      this.allSkus.add(backSku);
    }
    this.id = id;
    this.unsequencedFascia = new ArrayList<String>();
  }

  /**
   * Get the id of this PickingRequest.
   * 
   * @return the id.
   */
  protected int getId() {
    return id;
  }


  /**
   * Get the 4 orders stored in this PickingRequest.
   * 
   * @return the ordersList.
   */
  protected ArrayList<Order> getOrdersList() {
    return ordersList;
  }

  /**
   * Get the 8 sku of orders in this PickingRequest.
   * 
   * @return the allSkus.
   */
  protected ArrayList<String> getAllSkus() {
    return allSkus;
  }

  /**
   * Get the 4 front sku of orders stored in this PickingRequest.
   * 
   * @return the frontSkus.
   */
  protected ArrayList<String> getFrontSkus() {
    return frontSkus;
  }



  /**
   * Get the 4 back sku of orders stored in this PickingRequest.
   * 
   * @return the backSkus.
   */
  protected ArrayList<String> getBackSkus() {
    return backSkus;
  }


  /**
   * Get the 8 fascia in the perfect order.
   * 
   * @return the fasciaLists.
   */
  protected ArrayList<Fascia> getFasciaLists() {
    return fasciaLists;
  }

  /**
   * Get the fascia picked by Picker.
   * 
   * @return the unsequencedFascia.
   */
  protected ArrayList<String> getUnsequencedFascia() {
    return unsequencedFascia;
  }


  /**
   * Picker pick one fascia and add its sku to this PickingRequest.
   * 
   * @param sku of picked fascia.
   */
  protected void addUnsequencedFascia(String sku) {
    this.unsequencedFascia.add(sku);
  }

  /**
   * Delete all fascia picked by Picker.
   */
  protected void renew() {
    unsequencedFascia = new ArrayList<String>();
  }

  /**
   * Get all locations where Picker should go to pick fascia.
   * 
   * @return All locations.
   */
  protected ArrayList<String> getLocations() {
    return this.locations;
  }

  /**
   * Set the Locations of all 8 fascia.
   */
   protected void setLocations(ArrayList<String> location){
   this.locations = location;
   }
}
