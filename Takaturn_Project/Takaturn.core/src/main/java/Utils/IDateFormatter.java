package Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IDateFormatter {

    public String getDateFormatted(String val, String formate){
        Calendar c = Calendar.getInstance();

        if(val.equalsIgnoreCase("Today")){
            c.add(Calendar.HOUR, 0);
        }else if(val.contains("day")){
            val = (val.contains("today"))?(val.split("today"))[0]:(val.split("day"))[0];
            c.add(Calendar.DATE, Integer.valueOf(val));
        }

        /**
         * eg: "MM/dd/yyyy", "dd/MM/yyyy"
         */
        SimpleDateFormat DateFor = new SimpleDateFormat(formate);
        String stringDate = DateFor.format(c.getTime());
//        System.out.println("Date Format with MM/dd/yyyy : "+stringDate);
        return stringDate;
    }
}
