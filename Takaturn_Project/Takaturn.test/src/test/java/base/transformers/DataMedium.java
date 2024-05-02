package base.transformers;

import base.TestContext;
import io.restassured.response.Response;

public class DataMedium {

    static TestContext testContext;
    static String auth_token;
    static Response mediumResponse;

//    static Map<String, String> mediumMap = new HashMap<>();

    public static TestContext getTestContext() {
        return testContext;
    }

    public static void setTestContext(TestContext ctx) {
        testContext = ctx;
    }

    public static String getAuth_token() {
        return auth_token;
    }

    public static void setAuth_token(String auth_token) {
        DataMedium.auth_token = auth_token;
    }

//    public static Map<String, String> getMediumMap() {
//        return mediumMap;
//    }
//
//    public static void setMediumMap(Map<String, String> mediumMap) {
//        DataMedium.mediumMap = mediumMap;
//    }

    public static Response getMediumResponse() {
        return mediumResponse;
    }

    public static void setMediumResponse(Response mediumResponse) {
        DataMedium.mediumResponse = mediumResponse;
    }
}
