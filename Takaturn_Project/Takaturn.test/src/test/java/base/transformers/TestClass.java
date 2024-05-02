package base.transformers;

import Utils.ExcelReaders;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static Utils.DataProvider.doAddition;

public class TestClass {

    public static void main(String[] args) throws IOException, InvalidFormatException {

        String fullString = "grossPrice{+10}";
        String dollar = "$ 22,500.00";
        String exp = fullString.substring(fullString.indexOf("{")+1, fullString.indexOf("}"));
        Double i = Double.valueOf(dollar.replaceAll("[^\\d.]",""));
        System.out.println("EXP::::: "+i + exp+"  ----   "+fullString.substring(0,fullString.indexOf("{")));


        Double ui = doAddition(i + exp);
        System.out.println("INTEGER:::: "+ui);



    }

    public static StringBuilder readPDFfromUrl(String url){
        try {
            URL url1 =
                    new URL(url);
            PDFTextStripper strip=new PDFTextStripper();

            BufferedInputStream file = new BufferedInputStream(url1.openStream());
            PDDocument doc=PDDocument.load(file);
            strip.setStartPage(1);

            strip.setEndPage(2);
            String pdfContent=strip.getText(doc);
            int pages=doc.getNumberOfPages();

            System.out.println("The total number of pages "+pages);

            StringBuilder stringBuilder = new StringBuilder(strip.getText(doc));

            doc.close();

            return stringBuilder;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> fileHeaders1(String filePath, String sheetname, String headername){
        ExcelReaders reader = new ExcelReaders.ExcelReaderBuilder()
                .setFileLocation(filePath)
                .setSheet(sheetname).setRowNeeded(1)
                .build();

        try {
            List<String> excelData = reader.getNeededHeaderRows();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<List<String>> fileHeaders(String filePath, String sheetname) throws IOException, InvalidFormatException {
        ExcelReaders reader = new ExcelReaders.ExcelReaderBuilder()
                .setFileLocation(filePath)
                .setSheet(sheetname).setRowNeeded(1)
                .build();

        List<List<String>> excelData = reader.getRowDataAt();

        for(List<String> l:excelData){
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            for(String s:l){
                System.out.println("-------- "+s+" ---------------------");
            }
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        }

        return excelData;

    }
}
