package abcd.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Utility to read data from an Excel file.
 */
public class ExcelUtils {

    public static Object[][] getTestData(String filePath, String sheetName) {
        Object[][] data = null;
        try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();

            data = new Object[rows][cols];

            for (int i = 0; i < rows; i++) {
                Row row = sheet.getRow(i + 1); // Skip header
                for (int j = 0; j < cols; j++) {
                    Cell cell = row.getCell(j);
                    data[i][j] = cell != null ? cell.toString() : "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
