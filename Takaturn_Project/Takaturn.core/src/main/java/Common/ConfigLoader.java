package Common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigLoader {
    public static final String env = System.getProperty("env","test");
    public static final String propertiesFile = "properties/"+env+"/_env.properties";
    public static final Config config = ConfigFactory.load(propertiesFile)
            .withFallback(ConfigFactory.load("properties/general.properties"))
            .withFallback(ConfigFactory.load("properties/apigeneral.properties"))
            .withFallback(ConfigFactory.load("properties/"+env+"_env.properties"))
            .withFallback(ConfigFactory.load("uiMap/ui_map.properties"));

    public static final String app = System.getProperty("app", "test");
    public static final int plsWaitTimeOut = config.getInt("plsWaitTimeOut");
    public static final int hourglassTimeoutSec = config.getInt("hourglassTimeoutSec");

    public static final String screenShotsFolder = config.getString("screenshots_folder");


    public static final String API_BASE_URL = config.getString("api_base_url");
    public static final String API_JSON_INPUT_FOLDER=config.getString("api_json_input_folder");
    public static final String API_JSON_DEFAULT_DATA_FILE=config.getString("api_json_data_default_fill_loc");

}
