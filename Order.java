package warehousing;

import java.util.ArrayList;

/**
 * Stimulate the real order that sent to warehouse. Store all information of real orders.
 */
public class Order {
  /**
   * The color of this order.
   */
  private String colour;
  /**
   * The model of this order.
   */
  private String model;
  /**
   * The order id of this order.
   */
  private int orderNum;
  /**
   * The two sku of this order. [front sku, back sku].
   */
  private ArrayList<String> skus;

  /**
   * Initialize an order.
   * 
   * @param colour The color of this order.
   * @param model The model of this order.
   * @param skus The two sku of this order.
   * @param id The order id of this order.
   */
  protected Order(String colour, String model, ArrayList<String> skus, int id) {
    this.orderNum = id;
    this.colour = colour;
    this.model = model;
    this.skus = skus;
  }

  /**
   * Get the color of this order.
   * 
   * @return The color.
   */
  protected String getColour() {
    return colour;
  }

  /**
   * Get the model ordered.
   * 
   * @return The model of this order.
   */
  protected String getModel() {
    return model;
  }

  /**
   * Get front sku of this order.
   * 
   * @return front sku.
   */
  protected String getFrontSku() {
    return skus.get(0);
  }

  /**
   * Get back sku of this order.
   * 
   * @return The back sku.
   */
  protected String getBackSku() {
    return skus.get(1);
  }

  /**
   * Get the order id of this order.
   * 
   * @return the order id.
   */
  protected int getOrderNum() {
    return orderNum;
  }
}
