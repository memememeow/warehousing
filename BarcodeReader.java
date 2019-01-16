package warehousing;

import java.util.Scanner;
import java.util.logging.Level;

/**
 * This is the place where the system connected with the real world barcode reader. It will scan the
 * sku number.
 */
public class BarcodeReader {
  /**
   * The name of the barcode reader.
   */
  private String name;
  /**
   * The current work of the barcode reader.
   */
  private PickingRequest currWork;
  /**
   * The check result of this BarcodeReader's work.
   */
  private boolean checkResult;

  /**
   * Initialize a Barcode reader.
   * 
   * @param name The name of this barcode reader.
   */
  protected BarcodeReader(String name) {
    this.name = name;
    this.currWork = null;
    this.checkResult = false;
  }

  /**
   * Set the current work(PickingRequest) of this barcode reader.
   * 
   * @param currPickingRequest The current work for this barcode reader.
   */
  protected void setCurrWork(PickingRequest currPickingRequest) {
    this.currWork = currPickingRequest;
  }

  /**
   * Get the current work of this barcode reader.
   * 
   * @return the current work of barcode reader.
   */
  protected PickingRequest getCurrWork() {
    return this.currWork;
  }

  /**
   * Get the status of this barcode reader.
   * 
   * @return The working status of this barcode reader.
   */
  protected boolean isAvailable() {
    return this.currWork.equals(null);
  }

  /**
   * Get the name of this barcode reader.
   * 
   * @return The name of this barcode reader.
   */
  protected String getName() {
    return name;
  }

  /**
   * Scan the sku number of fascia.
   * 
   * @return The sku number of input fascia.
   */
  protected String scanSku() {
    Scanner input = new Scanner(System.in);
    SystemManage.logger.log(Level.INFO, "Enter the SKU be scanned by barcode reader");
    String inputContent = "";
    if (input.hasNext()) {
      inputContent = input.next();
    }
    input.close();
    return inputContent;
  }

  /**
   * Set the check result of this BarcodeReader for testing.
   * 
   * @param result Supposed check result of this Picker.
   */
  protected void setCheckResult(boolean result) {
    this.checkResult = result;
  }

  /**
   * Get the check result of this BarcodeReader.
   * 
   * @return result Supposed check result of this Picker.
   */
  protected boolean getCheckResult() {
    return this.checkResult;
  }
}
