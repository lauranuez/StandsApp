import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class LoadStands {
    //static String filePath = "files/AEA.xlsx";

    public static ArrayList<Stand> loadData(File file) throws IOException {
        ArrayList<Stand> stands = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(file);
        String filePath = file.getAbsolutePath();
        Workbook workbook = null;

        try{
            if (filePath.toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (filePath.toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                JOptionPane.showMessageDialog(null, "Formato de archivo de Excel no válido", "Formato no válido", JOptionPane.WARNING_MESSAGE);
            }
            if (workbook != null) {
                Sheet sheet = workbook.getSheet("STD");
                if (sheet != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    int i = 0;
                    for (Row row : sheet) {
                        String numStand = null;
                        String type = null;
                        String terminal = null;
    
                        if (i > 0) {
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
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stands;
    }

    public static ArrayList<Aerolinea> loadCias(File file) throws FileNotFoundException {
        ArrayList<Aerolinea> cias = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(file);
        String filePath = file.getAbsolutePath();
        Workbook workbook = null;

        try{
            if (filePath.toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (filePath.toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                JOptionPane.showMessageDialog(null, "Formato de archivo de Excel no válido", "Formato no válido", JOptionPane.WARNING_MESSAGE);
            }
            if (workbook != null) {
                Sheet sheet = workbook.getSheet("CIA");
                if (sheet != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    int i = 0;
                    for (Row row : sheet) {
                        String oaci = null;
                        String name = null;

                        if (i > 0) {
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
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cias;
    }
}
