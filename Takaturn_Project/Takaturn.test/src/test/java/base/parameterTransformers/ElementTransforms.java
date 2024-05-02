package base.parameterTransformers;

import Common.ConfigLoader;
import base.transformers.CustomString;
import base.transformers.CustomTransforms;
import io.cucumber.java.ParameterType;

public class ElementTransforms  {

    @ParameterType(value=".*")
    public String Transform(String val) {
        try {
//            System.out.println("VAL:::"+val+" VALUE:"+ConfigLoader.config.getString(val));
            return ConfigLoader.config.getString(val);
        }catch (Exception e) {
            e.getMessage();
            return val;
        }
    }

    @ParameterType(value=".*")
    public CustomString CustomStringTransform(String val) {
        return CustStringTransformer(val);
    }

    public static CustomString CustStringTransformer(String val) {
        if(val.startsWith("REF_") || val.startsWith("Config_"))
            val = (ConfigLoader.config.getString(val) !=null)?ConfigLoader.config.getString(val):val;

        if(!(val.contains(":"))){
            return new CustomString(val,val);
        }
        String dynamic = new CustomTransforms().getDynamicValue(val);
        return new CustomString(val,dynamic);
    }

}
