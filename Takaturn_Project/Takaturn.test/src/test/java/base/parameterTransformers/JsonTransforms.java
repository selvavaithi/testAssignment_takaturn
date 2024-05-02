package base.parameterTransformers;

import Common.ConfigLoader;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.ParameterType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class JsonTransforms {

    @ParameterType(value=".*")
    public Map<String, String> JsonTransforms(String value){
        String filePath = "testdata/"+value;

        String app = ConfigLoader.app;
        String env = ConfigLoader.env;

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());

        try {
            FileInputStream inputStream = new FileInputStream(file);
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Map<String, String>> jsonData = mapper.readValue(inputStream,
                    new TypeReference<Map<String, Map<String, String>>>() {});

            inputStream.close();
            return jsonData.get(env);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
