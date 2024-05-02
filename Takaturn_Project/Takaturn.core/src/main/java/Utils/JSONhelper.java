package Utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class JSONhelper {

    public SimpleFilterProvider getFilter(String except){
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
         filterProvider.addFilter("jsonFilter",
                SimpleBeanPropertyFilter.serializeAllExcept(except));
        return filterProvider;
    }

    public static ObjectMapper getMapper(){
        return getMapper(new ArrayList<>());
    }
    public static ObjectMapper getMapper(List<String> exceptJsonField){
        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();

        filterProvider.addFilter("jsonFilter",
                SimpleBeanPropertyFilter.serializeAllExcept(new HashSet<String>(exceptJsonField)));

        return mapper.setFilterProvider(filterProvider);
    }

    public static String test_UpdateJsonString(String jsonStr, String targetJsonStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        jsonStr = "{\"type\":\"animal\",\"configurationData\": {\"type\":\"herbivorous\",\"address\":\"123, Windmill Road\"}}";
//        targetJsonStr ="{\"type\":\"human\",\"configurationData\":{\"type\":\"vegeterian\",\"address\":\"876, Borough Street\"}}";

//        System.out.println("START:::: "+jsonStr);
//        System.out.println("TARGET:::: "+targetJsonStr);
        Map<String, Object> mapJson = objectMapper.readValue(jsonStr,new TypeReference<Map<String,Object>>(){});
        Map<String, Object> mapTargetJson = objectMapper.readValue(targetJsonStr,new TypeReference<Map<String,Object>>(){});

        // either update all values or a particular property with mapJson.get(fieldName)
        for (Map.Entry<String,Object> keyVal: mapTargetJson.entrySet()) {
            mapJson.replace(keyVal.getKey(),keyVal.getValue());
        }

        jsonStr = objectMapper.writeValueAsString(mapJson);
        JsonNode jsonNode = objectMapper.readTree(jsonStr);
//        System.out.println("OUTPUT ::::: "+jsonNode.asText());
        return jsonNode.asText();
    }

    public static String test_UpdateJsonString(Object objJson, Map<String,String> columnValue) {

        Map<String, Object> mapJson;
        try {
            mapJson = getMapper().readValue(getMapper().writeValueAsString(objJson), new TypeReference<Map<String, Object>>() {
            });
            // either update all values or a particular property with mapJson.get(fieldName)
            for (String key : columnValue.keySet()) {
                System.out.println("test_UpdateJsonString list:: key:"+key+" , Value:"+columnValue.get(key));
                if (mapJson.containsKey(key))
                    mapJson.replace(key, columnValue.get(key));
            }
            return getMapper().writeValueAsString(mapJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public static <T> T getMapperReadValue(InputStream src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {

        return getMapper().readValue(src, valueType);

    }
}
