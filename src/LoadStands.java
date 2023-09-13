import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class LoadStands {
    //static String filePath = "files/AEA.xlsx";

    public static ArrayList<Stand> loadData(String filePath) {
        ArrayList<Stand> stands = new ArrayList<>();

        try (InputStream inputStream = SetNumFlights.class.getResourceAsStream(filePath); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet("STD");
            if (sheet != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#");
                int i = 0;
                for (Row row : sheet) {
                    String numStand = null;
                    String type = null;
                    String terminal = null;

                    if(i>0) {
                        for (Cell cell : row) {
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    if (cell.getCellType() == CellType.NUMERIC) {
                                        double numericValue = cell.getNumericCellValue();
                                        String stringValue = decimalFormat.format(numericValue);
                                        numStand = stringValue;
                                    } else if (cell.getCellType() == CellType.STRING) {
                                        String cellValue = cell.getStringCellValue();
                                        if (!cellValue.isEmpty()) {
                                            numStand = cellValue;
                                        }
                                    }
                                    break;
                                case 1:
                                    if (cell.getCellType() == CellType.NUMERIC) {
                                        double numericValue = cell.getNumericCellValue();
                                        String stringValue = decimalFormat.format(numericValue);
                                        type = stringValue;
                                    } else if (cell.getCellType() == CellType.STRING) {
                                        String cellValue = cell.getStringCellValue();
                                        if (!cellValue.isEmpty()) {
                                            type = cellValue;
                                        }
                                    }
                                    break;
                                case 2:
                                    if (cell.getCellType() == CellType.NUMERIC) {
                                        double numericValue = cell.getNumericCellValue();
                                        String stringValue = decimalFormat.format(numericValue);
                                        terminal = stringValue;
                                    } else if (cell.getCellType() == CellType.STRING) {
                                        String cellValue = cell.getStringCellValue();
                                        if (!cellValue.isEmpty()) {
                                            terminal = cellValue;
                                        }
                                    }
                                    break;
                            }
                        }
                        Stand stand = new Stand(numStand, type, terminal);
                        stands.add(stand);
                    }
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stands;
    }

    public static ArrayList<Aerolinea> loadCias(String filePath) {
        ArrayList<Aerolinea> cias = new ArrayList<>();

        try (InputStream inputStream = SetNumFlights.class.getResourceAsStream(filePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet("CIA");
            if (sheet != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#");
                int i = 0;
                for (Row row : sheet) {
                    String oaci = null;
                    String name = null;

                    if(i>0) {
                        for (Cell cell : row) {
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    if (cell.getCellType() == CellType.NUMERIC) {
                                        double numericValue = cell.getNumericCellValue();
                                        String stringValue = decimalFormat.format(numericValue);
                                        oaci = stringValue;
                                    } else if (cell.getCellType() == CellType.STRING) {
                                        String cellValue = cell.getStringCellValue();
                                        if (!cellValue.isEmpty()) {
                                            oaci = cellValue;
                                        }
                                    }
                                    break;
                                case 1:
                                    if (cell.getCellType() == CellType.NUMERIC) {
                                        double numericValue = cell.getNumericCellValue();
                                        String stringValue = decimalFormat.format(numericValue);
                                        name = stringValue;
                                    } else if (cell.getCellType() == CellType.STRING) {
                                        String cellValue = cell.getStringCellValue();
                                        if (!cellValue.isEmpty()) {
                                            name = cellValue;
                                        }
                                    }
                                    break;
                            }
                        }
                        Aerolinea cia = new Aerolinea(oaci, name);
                        cias.add(cia);
                    }
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cias;
    }
}
