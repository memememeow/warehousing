package warehousing;

import java.util.logging.Level;

/**
 * The Picker-BarcodeReader. In charge of picking fascia.
 */
public class Picker extends BarcodeReader {

  /**
   * Initialize a Picker.
   * 
   * @param name The name of this Picker.
   */
  protected Picker(String name) {
    super(name);
  }

  /**
   * Let this picker pick one fascia.
   * 
   * @param pickLocation The location where this picker should go to pick fascia.
   * @param sku The sku of fascia that this picker should pick.
   */
  protected void pickSingleFascia(String pickLocation, String sku) {
    if (!this.getCheckResult()) {
      String inputContent = this.scanSku(); // rescan this fasica.
      if (sku.equals(inputContent)) {
        this.setCheckResult(true);
      } else {
        SystemManage.logger.log(Level.SEVERE, "Pick the wrong fascia.");
      }
    }
    SystemManage.logger.log(Level.FINE, "Picker " + this.getName() + " pick the correct fascia.");
  }

}
