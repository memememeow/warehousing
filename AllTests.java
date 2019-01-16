package warehousing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestBarcodeReader.class, TestFascia.class, TestLoader.class, TestMain.class,
    TestOrder.class, TestPicker.class, TestPickingRequest.class, TestSequencer.class,
    TestSystemhelper.class, TestSystemManage.class, TestTruck.class, TestWorkerFactory.class})

public class AllTests {

}
