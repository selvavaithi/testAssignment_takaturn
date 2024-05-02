package Utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExcelReaders {

    private String fileName;
    private String sheetName;
    private int sheetIndex;
    private XSSFWorkbook book;
    private int rowNeeded;
    private String neededHeader;
    private int HfirstCol;
    private int HlastCol;

    private ExcelReaders(ExcelReaderBuilder excelReaderBuilder) {
        this.fileName = excelReaderBuilder.fileName;
        this.sheetIndex = excelReaderBuilder.sheetIndex;
        this.sheetName = excelReaderBuilder.sheetName;
        this.rowNeeded = excelReaderBuilder.rowNeeded;
        this.neededHeader = excelReaderBuilder.neededHeader;
    }

    public static class ExcelReaderBuilder {

        private String fileName;
        private String sheetName;
        private int sheetIndex;

        private int rowNeeded;
        private String neededHeader;

        public ExcelReaderBuilder setFileLocation(String location) {
            this.fileName = location;
            return this;
        }

        public ExcelReaderBuilder setSheet(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public ExcelReaderBuilder setSheet(int index) {
            this.sheetIndex = index;
            return this;
        }

        public ExcelReaders build() {
            return new ExcelReaders(this);
        }

        public ExcelReaderBuilder setRowNeeded(int i) {
            this.rowNeeded = i;
            return this;
        }

        public ExcelReaderBuilder getRowMergedHeadersFor(String neededHeaders) {
            this.neededHeader = neededHeaders;
            return this;
        }
    }

    private XSSFWorkbook getWorkBook(String filePath) throws InvalidFormatException, IOException {
        return new XSSFWorkbook(new File(filePath));
    }

    private XSSFSheet getWorkBookSheet(String fileName, String sheetName) throws InvalidFormatException, IOException {
        this.book = getWorkBook(fileName);
        return this.book.getSheet(sheetName);
    }

    private XSSFSheet getWorkBookSheet(String fileName, int sheetIndex) throws InvalidFormatException, IOException {
        this.book = getWorkBook(fileName);
        return this.book.getSheetAt(sheetIndex);
    }

    public List<List<String>> getSheetData() throws IOException {
        XSSFSheet sheet;
        List<List<String>> outerList = new LinkedList<>();

        try {
            sheet = getWorkBookSheet(fileName, sheetName);
            outerList = getSheetData(sheet);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.book.close();
        }
        return outerList;
    }

    public List<List<String>> getSheetDataAt() throws InvalidFormatException, IOException {

        XSSFSheet sheet;
        List<List<String>> outerList = new LinkedList<>();

        try {
            sheet = getWorkBookSheet(fileName, sheetIndex);
            outerList = getSheetData(sheet);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.book.close();
        }
        return outerList;
    }

    public List<List<String>> getRowDataAt() throws InvalidFormatException, IOException {

        XSSFSheet sheet;
        List<List<String>> outerList = new LinkedList<>();

        try {
            sheet = getWorkBookSheet(fileName, sheetIndex);
            outerList = getRowData(sheet, rowNeeded);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.book.close();
        }
        return outerList;
    }

    public List<String> getNeededHeaderRows() throws InvalidFormatException, IOException {

        XSSFSheet sheet;
        List<List<String>> outerList = new LinkedList<>();

        try {
            sheet = getWorkBookSheet(fileName, sheetIndex);
            outerList = getMergColHeaderData(sheet, this.neededHeader);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.book.close();
        }
        return outerList.get(0);
    }

    public List<List<String>> getRowDataUnderNeededHeader() throws IOException, InvalidFormatException {
        XSSFSheet sheet;
        List<List<String>> outerList = new LinkedList<>();
        sheet = getWorkBookSheet(fileName, sheetIndex);
        prepareOutterList(sheet.getRow(rowNeeded), outerList, HfirstCol, HlastCol);
        return Collections.unmodifiableList(outerList);
    }

    private List<List<String>> getSheetData(XSSFSheet sheet) {
        List<List<String>> outerList = new LinkedList<>();
        prepareOutterList(sheet, outerList);
        return Collections.unmodifiableList(outerList);
    }

    private List<List<String>> getRowData(XSSFSheet sheet, int rowNeeded) {
        List<List<String>> outerList = new LinkedList<>();
        prepareOutterList(sheet, outerList, rowNeeded);
        return Collections.unmodifiableList(outerList);
    }

    private List<List<String>> getMergColHeaderData(XSSFSheet sheet, String headerName) {
        List<List<String>> outerList = new LinkedList<>();
        prepareOutterList(sheet, outerList, headerName);
        return Collections.unmodifiableList(outerList);
    }

    private void prepareOutterList(XSSFSheet sheet, List<List<String>> outerList, String headername) {
        List<String> innerList = new LinkedList<>();
        List<CellRangeAddress> mergedRegins = sheet.getMergedRegions();

        for (CellRangeAddress c : mergedRegins) {
            int firstCol = c.getFirstColumn();
            int lastCol = c.getLastColumn();
            for (int i = firstCol; i <= lastCol; i = lastCol + 1) {
                XSSFCell cell1 = sheet.getRow(0).getCell(i);

                switch (cell1.getCellType()) {
                    case BLANK -> {
                        System.out.println("RANGE COL:::" + i);
                    }
                    case STRING -> {
                        //System.out.println("Col:: - " + i + " - HEADERCELL::" + cell1.getStringCellValue());
                        if (cell1.getStringCellValue().equalsIgnoreCase(headername)) {
//                            System.out.println("Found Header: \"" + headername + "\" StartCol:" + c.getFirstColumn() + " End Col:" + c.getLastColumn());

                            XSSFRow xssfRow = sheet.getRow(1);
                            for (int j = c.getFirstColumn(); j <= c.getLastColumn(); j++) {
                                HfirstCol = c.getFirstColumn();
                                HlastCol = c.getLastColumn();
                                prepareInnerList(innerList, xssfRow, j);
                            }

                        }
                    }
                }
            }
        }

        outerList.add(Collections.unmodifiableList(innerList));

    }

    private void prepareOutterList(XSSFSheet sheet, List<List<String>> outerList, int rowNeeded) {
        List<String> innerList = new LinkedList<>();
        XSSFRow xssfRow = sheet.getRow(rowNeeded);

        for (int j = xssfRow.getFirstCellNum(); j < xssfRow.getLastCellNum(); j++) {
            prepareInnerList(innerList, xssfRow, j);
        }
        outerList.add(Collections.unmodifiableList(innerList));

    }

    private void prepareOutterList(XSSFRow xssfRow, List<List<String>> outerList, int colStart, int colEnd) {
        List<String> innerList = new LinkedList<>();

        for (int j = colStart; j <= colEnd; j++) {
            prepareInnerList(innerList, xssfRow, j);
        }
        outerList.add(Collections.unmodifiableList(innerList));

    }

    private void prepareOutterList(XSSFSheet sheet, List<List<String>> outerList) {
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            List<String> innerList = new LinkedList<>();
            XSSFRow xssfRow = sheet.getRow(i);

            for (int j = xssfRow.getFirstCellNum(); j < xssfRow.getLastCellNum(); j++) {
                prepareInnerList(innerList, xssfRow, j);
            }
            outerList.add(Collections.unmodifiableList(innerList));
        }
    }

    private void prepareInnerList(List<String> innerList, XSSFRow xssfRow, int j) {
        switch (xssfRow.getCell(j).getCellType()) {

            case BLANK:
                innerList.add("");
                break;

            case STRING:
                innerList.add(xssfRow.getCell(j).getStringCellValue());
                break;

            case NUMERIC:
                innerList.add(xssfRow.getCell(j).getNumericCellValue() + "");
                break;

            case BOOLEAN:
                innerList.add(xssfRow.getCell(j).getBooleanCellValue() + "");
                break;

            default:
                throw new IllegalArgumentException("Cannot read the column : " + j);
        }
    }

    public static List<String> fileHeaders(String filePath, String sheetname) throws IOException, InvalidFormatException {
        ExcelReaders reader = new ExcelReaderBuilder()
                .setFileLocation(filePath)
                .setSheet(sheetname).setRowNeeded(1)
                .build();

        List<String> excelHeader = new ArrayList<String>();

        for (List<String> l : reader.getRowDataAt()) {
            for (String s : l) {
                excelHeader.add(s);
            }
        }

        return excelHeader;

    }
}
