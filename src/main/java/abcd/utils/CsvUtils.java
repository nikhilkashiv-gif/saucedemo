package abcd.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility to read data from a CSV file for DataDriven testing.
 */
public class CsvUtils {

    public static Object[][] getCsvData(String csvFilePath) {
        List<Object[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Assuming comma separated values
                String[] data = line.split(",");
                rows.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows.toArray(new Object[0][0]);
    }
}
