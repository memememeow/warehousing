package warehousing;

import java.util.logging.Level;

public class Sequencer extends BarcodeReader {

  public Sequencer(String name) {
    super(name);

  }


  /**
   * First check the eight fascia.
   */
  protected void sequenceCheck() {
    this.setCheckResult(true);
    for (int i = 0; i < 4; i++) {
      SystemManage.logger.log(Level.INFO, "Please scan the four front fascia in order");
      String inputFront = this.scanSku();
      if (!(inputFront.equals(this.getCurrWork().getFrontSkus().get(i)))) {
        SystemManage.logger.log(Level.SEVERE, "Wrong fascia");
        this.setCheckResult(false);
      }
    }
    for (int i = 0; i < 4; i++) {
      SystemManage.logger.log(Level.INFO, "Please scan the four back fascia in order");
      String inputBack = this.scanSku();
      if (!(inputBack.equals(this.getCurrWork().getBackSkus().get(i)))) {
        SystemManage.logger.log(Level.SEVERE, "Wrong fascia");
        this.setCheckResult(false);
      }
    }
  }
}
