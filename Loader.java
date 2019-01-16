package warehousing;

import java.util.logging.Level;

/**
 * The Loader-BarcodeReader. In charge of loading fascia following the instruction of system.
 */
public class Loader extends BarcodeReader {
  /**
   * Initialize a Loader.
   * 
   * @param name The name of this Loader.
   */
  protected Loader(String name) {
    super(name);
  }

  /**
   * Let this loader load 8 fascia to Truck.
   * 
   * @param currTruck The Truck loaders work now.
   */
  protected void loadTruck(Truck currTruck) {
    currTruck.loadFascia(this.getCurrWork(), this.getCurrWork().getFrontSkus(),
        this.getCurrWork().getBackSkus());
  }

  /**
   * Let this loader check the order of fascia at the first time. And change the status of check
   * result of this Loader depends on its scan result.
   */
  protected void checkFasciaOrder() {
    for (int i = 0; i < 4; i++) {
      SystemManage.logger.log(Level.INFO,
          "Please scan the" + i + "th pair of fascia.- Front, Back.");
      String inputFront = this.scanSku();
      if (!(inputFront.equals(this.getCurrWork().getFrontSkus().get(i)))) {
        SystemManage.logger.log(Level.SEVERE, "Wrong fascia or fascia in wrong order");
        this.setCheckResult(false);
      }
      String inputBack = this.scanSku();
      if (!(inputBack.equals(this.getCurrWork().getBackSkus().get(i)))) {
        SystemManage.logger.log(Level.SEVERE, "Wrong fascia or fascia in wrong order");
        this.setCheckResult(false);
      }
    }
  }

}
