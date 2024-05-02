package base;

import Common.BaseTestContext;
import base.transformers.CustomString;

public class TestContext extends BaseTestContext {

    public TestContext(){

    }

    public CustomString updateFromCustomString(CustomString customString){
        hasTestData=true;
        if(testData.containsKey(customString.getKey())){
            return new CustomString(customString.getKey(), testData.get(customString.getKey()));
        }
        testData.put(customString.getKey(), customString.getValue());
        return customString;
    }
}
