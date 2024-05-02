package Common;

import java.util.HashMap;
import java.util.Map;

public class BaseTestContext {

    public Boolean isOpened;
    public Boolean isSimulated;
    public Boolean hasTestData;
    public Map<String, String> testData;
    public BaseTestContext(){
        isOpened=false;
        isSimulated=false;
        hasTestData=false;
        testData=new HashMap<>();

    }
}
