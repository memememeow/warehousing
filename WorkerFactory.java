package warehousing;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Create workers for the warehouse.
 */
public class WorkerFactory {

  /**
   * All the Picker in warehousing company.
   */
  private ArrayList<BarcodeReader> pickerList;
  /**
   * All the Sequencer in warehousing company.
   */
  protected ArrayList<BarcodeReader> sequencerList;
  /**
   * All the Loader in warehousing company.
   */
  protected ArrayList<BarcodeReader> loaderList;

  /**
   * Initialize a WorkerFactory.
   */
  protected WorkerFactory() {
    pickerList = new ArrayList<BarcodeReader>();
    sequencerList = new ArrayList<BarcodeReader>();
    loaderList = new ArrayList<BarcodeReader>();
  }

  /**
   * Find the work with corresponding name and type. If there is no such worker, create one.
   * 
   * @param type The type of this worker.
   * @param name The name of this worker.
   * @return This worker.
   */
  protected BarcodeReader getWorker(String type, String name) {
    ArrayList<BarcodeReader> currWorkers = null;
    BarcodeReader currFindWorker = null;
    if (type.equals("Picker")) {
      currWorkers = this.pickerList;
    } else if (type.equals("Sequencer")) {
      currWorkers = this.sequencerList;
    } else if (type.equals("Loader")) {
      currWorkers = this.loaderList;
    } else {
      SystemManage.logger.log(Level.WARNING, "There is no such type of worker");
      return null;
    }
    boolean check = false;
    for (BarcodeReader worker : currWorkers) {
      String currName = worker.getName();
      if (currName.equals(name)) {
        check = true;
        SystemManage.logger.log(Level.FINE, "Worker " + name + " already existed in the system. ");
        currFindWorker = worker;
      }
    }
    if (!check) {
      if (type.equals("Picker")) {
        Picker picker = new Picker(name);
        this.pickerList.add(picker);
        currFindWorker = picker;
      } else if (type.equals("Sequencer")) {
        Sequencer sequencer = new Sequencer(name);
        this.sequencerList.add(sequencer);
        currFindWorker = sequencer;
      } else if (type.equals("Loader")) {
        Loader loader = new Loader(name);
        this.loaderList.add(loader);
        currFindWorker = loader;
      }
    }
    return currFindWorker;
  }

  /**
   * Get all Picker in warehousing company.
   * 
   * @return the pickerList.
   */
  protected ArrayList<BarcodeReader> getPickerList() {
    return pickerList;
  }

  /**
   * Get all Sequencer in warehousing company.
   * 
   * @return the sequencerList.
   */
  protected ArrayList<BarcodeReader> getSequencerList() {
    return sequencerList;
  }

  /**
   * Get all Loader in warehousing company.
   * 
   * @return the loaderList.
   */
  protected ArrayList<BarcodeReader> getLoaderList() {
    return loaderList;
  }

  /**
   * Add a leader into the list (just used for testing).
   */
  protected void addLoader(Loader loader) {
    this.loaderList.add(loader);
  }

  /**
   * Add a sequencer into the list (just used for testing).
   */
  protected void addSequencer(Sequencer sequencer) {
    this.sequencerList.add(sequencer);

  }

}
