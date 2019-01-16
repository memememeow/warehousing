package warehousing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Simulate the fascia for minivan bumper. Store all information of the real fascia in the
 * warehouse.
 *
 */
public class Fascia {
  /**
   * The color of this fascia.
   */
  private String colour;
  /**
   * The model of this fascia.
   */
  private String model;
  /**
   * The sku of this fascia.
   */
  private String sku;
  /**
   * The orientation of this fascia. False represents front, true represents back.
   */
  private boolean orientation;
  /**
   * The location of this fascia in warehouse.
   */
  private String location;

  /**
   * Initialize a fascia.
   * 
   * @param colour The colour of this fascia.
   * @param model The model of this fascia.
   * @param sku The sku number of this fascia.
   * @boolean orientation The orientation of this fascia.
   * @throws IOException .
   */
  protected Fascia(String colour, String model, String sku, boolean orientation)
      throws IOException {
    this.colour = colour;
    this.model = model;
    this.sku = sku;
    this.orientation = orientation;
    this.setLocation();
  }

  /**
   * Get the colour of this fascia.
   * 
   * @return the colour.
   */
  protected String getColour() {
    return colour;
  }

  /**
   * Get the model of this fascia.
   * 
   * @return the model.
   */
  protected String getModel() {
    return model;
  }

  /**
   * Get the sku of this fascia.
   * 
   * @return the sku.
   */
  protected String getSku() {
    return sku;
  }

  // Delete this method when last submit.
  /**
   * Set the location of this fascia in warehouse zone.
   * 
   * @throws IOException .
   */
  protected void setLocation() throws IOException {
    String line;
    BufferedReader buffer = new BufferedReader(new FileReader("traversal_table.csv"));
    while ((line = buffer.readLine()) != null) {
      String[] order = line.split(",");
      String currLocation = order[0] + order[1] + order[2] + order[3];
      String currSku = order[4];
      if (sku.equals(currSku)) {
        this.location = currLocation;
      }
    }
    buffer.close();
  }

  /**
   * Get the location of this fascia in the warehouse zone.
   * 
   * @return the location of fascia.
   */
  protected String getLocation() {
    return this.location;
  }

  /**
   * Get the orientation of this fascia.
   * 
   * @return the orientation of fascia.
   */
  protected boolean getOrientation() {
    return this.orientation;
  }
}
