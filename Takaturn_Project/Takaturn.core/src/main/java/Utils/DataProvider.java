package Utils;

import static extensions.IReporter.addStepLog;

public class DataProvider {

    public static Double doAddition(String str){
        String i1= str.substring(0, str.indexOf("+"));
        String i2= str.substring(str.indexOf("+"), str.length());
        Double i=(Double.valueOf(i1)
                + Double.valueOf(i2));
//        System.out.println("i1:"+i1+" - i2:"+i2+" Total:"+i);
        addStepLog(String.format("Adding exp: %s %s + %s = %s",str,i1,i2,i));
        return i;
    }

}
