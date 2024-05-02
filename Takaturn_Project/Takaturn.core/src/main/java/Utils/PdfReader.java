package Utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import static extensions.IReporter.addStepLog;

public class PdfReader {

    public static StringBuilder readPDFfromUrl(String url){
        try {
            URL url1 =
                    new URL(url);
            addStepLog(String.format("URL:: %s",url));
            PDFTextStripper strip=new PDFTextStripper();

            BufferedInputStream file = new BufferedInputStream(url1.openStream());
            PDDocument doc=PDDocument.load(file);
            strip.setStartPage(1);

            strip.setEndPage(2);
            String pdfContent=strip.getText(doc);
            int pages=doc.getNumberOfPages();

//            System.out.println("The total number of pages "+pages);

            StringBuilder stringBuilder = new StringBuilder(strip.getText(doc));

            doc.close();

            return stringBuilder;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
