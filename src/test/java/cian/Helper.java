package cian;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.*;

public class Helper {

    static WebDriver driver;
    static String generalSite = "https://www.cian.ru";
    static WebDriverWait wait;
    static String mainWindowHandle;
    private static int rowNum = 1;
    private static File writeFile;
    private static File readFile;

    protected static List<Map<String, String>> readFile() throws Exception {
        List<Map<String, String>> data = new LinkedList<>();

        readFile = new File(Paths.get("").toAbsolutePath() + "/target/Test cian.xlsx");

        Workbook workbook = WorkbookFactory.create(readFile);

        Sheet sheet = workbook.getSheetAt(0);
        Row firstRow = null;

        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Map<String, String> cases = new HashMap<>();
            org.apache.poi.ss.usermodel.Row row = rowIterator.next();

            //get title row
            if (firstRow == null) {
                firstRow = row;
                continue;
            }

            Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                if (cell.getStringCellValue() != null) {
                    cases.put(firstRow.getCell(cell.getColumnIndex()).getStringCellValue(), cell.getStringCellValue());
                }
            }

            data.add(cases);

        }
        workbook.close();

        return data;
    }

    protected static void writeToExcel(List<Map<String, String>> writeData) throws Exception {
        String[] columns = {"Option", "Type", "Main info", "Prices", "Address", "Phone"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Cian");

        // Create a Font
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells for header
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        for (Map<String, String> data : writeData) {
            // Create Other rows and cells with data
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(data.get("Option"));

            row.createCell(1)
                    .setCellValue(data.get("Type"));

            row.createCell(2)
                    .setCellValue(data.get("Main info"));

            row.createCell(3)
                    .setCellValue(data.get("Prices"));

            row.createCell(4)
                    .setCellValue(data.get("Address"));

            row.createCell(5)
                    .setCellValue(data.get("Phone"));
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++)

        {
            sheet.autoSizeColumn(i);
        }

        writeFile = new File(Paths.get("").toAbsolutePath() + "/target/Report cian.xlsx");
        // Write the output to file
        FileOutputStream fileOut = new FileOutputStream(writeFile);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }

}
