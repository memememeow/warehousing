package warehousing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestWorkerFactory {
  WorkerFactory wf = new WorkerFactory();
  BarcodeReader picker1;
  BarcodeReader picker2;
  BarcodeReader picker3;
  BarcodeReader sequencer1;
  BarcodeReader sequencer2;
  BarcodeReader sequencer3;
  BarcodeReader loader1;
  BarcodeReader loader2;
  BarcodeReader loader3;

  @Test
  public void testGetWorker() {
    picker1 = wf.getWorker("Picker", "Picker1");
    picker2 = wf.getWorker("Picker", "Picker2");
    sequencer1 = wf.getWorker("Sequencer", "Sequencer1");
    sequencer2 = wf.getWorker("Sequencer", "Sequencer2");
    loader1 = wf.getWorker("Loader", "Loader1");
    loader2 = wf.getWorker("Loader", "Loader2");
    picker3 = wf.getWorker("Picker", "Picker1");
    sequencer3 = wf.getWorker("Sequencer", "Sequencer1");
    loader3 = wf.getWorker("Loader", "Loader1");
    final BarcodeReader none = wf.getWorker("Whatever", "we");
    assertEquals("Picker1", picker1.getName());
    assertEquals(null, picker1.getCurrWork());
    assertEquals("Picker2", picker2.getName());
    assertEquals("Sequencer1", sequencer1.getName());
    assertEquals(null, sequencer1.getCurrWork());
    assertEquals("Sequencer2", sequencer2.getName());
    assertEquals("Loader1", loader1.getName());
    assertEquals(null, loader1.getCurrWork());
    assertEquals("Loader2", loader2.getName());
    assertEquals(null, wf.getWorker("Whatever", "kkk"));
    assertEquals("Picker1", picker3.getName());
    assertEquals(null, picker3.getCurrWork());
    assertEquals("Sequencer1", sequencer3.getName());
    assertEquals(null, sequencer3.getCurrWork());
    assertEquals("Loader1", loader3.getName());
    assertEquals(null, loader3.getCurrWork());
    assertEquals(2, wf.getPickerList().size());
    assertEquals(null, none);
    assertEquals("Picker1", wf.getPickerList().remove(0).getName());
    assertEquals(1, wf.getPickerList().size());
    assertEquals("Picker2", wf.getPickerList().remove(0).getName());
    assertEquals(0, wf.getPickerList().size());
    assertEquals(2, wf.getSequencerList().size());
    assertEquals("Sequencer1", wf.getSequencerList().remove(0).getName());
    assertEquals(1, wf.getSequencerList().size());
    assertEquals("Sequencer2", wf.getSequencerList().remove(0).getName());
    assertEquals(0, wf.getSequencerList().size());
    assertEquals(2, wf.getLoaderList().size());
    assertEquals("Loader1", wf.getLoaderList().remove(0).getName());
    assertEquals(1, wf.getLoaderList().size());
    assertEquals("Loader2", wf.getLoaderList().remove(0).getName());
    assertEquals(0, wf.getLoaderList().size());
  }
}
