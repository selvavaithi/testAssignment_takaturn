package base.transformers;

import Utils.IDateFormatter;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomTransforms {

    public String getDynamicValue(String value) {

        Pattern pattern = Pattern.compile("(\\w?:?.+)");
        Matcher matcher = pattern.matcher(value);
        String dynamicData = "";
//        System.out.println("DYNAMIC::::: "+value);
        if (matcher.find()) {
            int min = 100000000;
            String type = value;
            if(value.contains(":"))
            type = value.substring(0, value.indexOf(":"));
//            System.out.println("DYNAMIC:::: TYPE:"+type);
            switch (type) {
                case "number5D": {
                    dynamicData = Integer.toString(new Random().nextInt(10000));
                    break;
                }
                case "today":
                case "Today": {
                    dynamicData = new IDateFormatter().getDateFormatted(type,"dd-MM-yyyy");
                    break;
                }
                default: {
                    dynamicData = value;
                    break;
                }
            }
        }

        return dynamicData;
    }
}
