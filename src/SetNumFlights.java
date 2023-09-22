import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class SetNumFlights {
    static ArrayList<FlightPairs> flightPairs = new ArrayList<>();

    public static ArrayList<Flight> associateExcel(ArrayList<Flight> flights, File rutaExcel) throws FileNotFoundException {
        ArrayList<Flight> flightListAEA = flights;
        ArrayList<Flight> removeFlightList = new ArrayList<>();

        loadData(rutaExcel);

        if (!flightPairs.isEmpty()) {
            for (FlightPairs pair : flightPairs) {
                for (Flight flight : flightListAEA) {
                    if (flight.airlineA.equals(pair.airlineA) && flight.numA.equals(pair.numFlightAr)) {
                        for (Flight flight1 : flightListAEA) {
                            if (flight1.numD.equals(pair.numFlightDep) && flight1.airlineD.equals(pair.airlineD) && flight1.aircraftD.equals(flight.aircraftA)) {
                                if (flight.numD == "-") {
                                    if (flight.pernocta == 0) {
                                        if (flight1.dateD.isEqual(flight.dateA)) {
                                            if (pair.aircraft != null) {
                                                if (pair.aircraft.equals(flight1.aircraftD)) {
                                                    flight.dateD = flight1.dateD;
                                                    flight.airlineD = flight1.airlineD;
                                                    flight.numD = flight1.numD;
                                                    flight.timeD = flight1.timeD;
                                                    flight.as = flight1.as;
                                                    flight.af = flight1.af;
                                                    flight.flightTypeD = flight1.flightTypeD;
                                                    flight.aircraftD = flight1.aircraftD;
                                                    removeFlightList.add(flight1);
                                                }
                                            } else {
                                                flight.dateD = flight1.dateD;
                                                flight.airlineD = flight1.airlineD;
                                                flight.numD = flight1.numD;
                                                flight.timeD = flight1.timeD;
                                                flight.as = flight1.as;
                                                flight.af = flight1.af;
                                                flight.flightTypeD = flight1.flightTypeD;
                                                flight.aircraftD = flight1.aircraftD;
                                                removeFlightList.add(flight1);
                                            }
                                        }
                                    } else {
                                        LocalDate dateFlight = flight.dateA;
                                        LocalDate dateFlight1 = dateFlight.plusDays(flight.pernocta);
                                        if (flight1.dateD.isEqual(dateFlight1)) {
                                            if (pair.aircraft != null) {
                                                if (pair.aircraft.equals(flight1.aircraftD)) {
                                                    flight.dateD = flight1.dateD;
                                                    flight.airlineD = flight1.airlineD;
                                                    flight.numD = flight1.numD;
                                                    flight.timeD = flight1.timeD;
                                                    flight.as = flight1.as;
                                                    flight.af = flight1.af;
                                                    flight.flightTypeD = flight1.flightTypeD;
                                                    flight.aircraftD = flight1.aircraftD;
                                                    removeFlightList.add(flight1);
                                                }
                                            } else {
                                                flight.dateD = flight1.dateD;
                                                flight.airlineD = flight1.airlineD;
                                                flight.numD = flight1.numD;
                                                flight.timeD = flight1.timeD;
                                                flight.as = flight1.as;
                                                flight.af = flight1.af;
                                                flight.flightTypeD = flight1.flightTypeD;
                                                flight.aircraftD = flight1.aircraftD;
                                                removeFlightList.add(flight1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                flightListAEA.removeAll(removeFlightList);
            }
            return flightListAEA;
        }
        else {
            return null;
        }
    }

    public static ArrayList<FlightPairs> loadData(File file) throws FileNotFoundException {
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
                Sheet sheet = workbook.getSheet("ASOCIAR");
                if (sheet != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    int i = 0;
                    for (Row row : sheet) {
                        String aircraft = null;
                        String airlineA = null;
                        String numFlightAr = null;
                        String numFlightDep = null;
                        String airlineD = null;

                        if (i > 0) {
                            for (Cell cell : row) {
                                switch (cell.getColumnIndex()) {
                                    case 0:
                                        if (cell.getCellType() == CellType.STRING) {
                                            String cellValue = cell.getStringCellValue();
                                            if (!cellValue.isEmpty()) {
                                                airlineA = cellValue;
                                            }
                                        }
                                        break;
                                    case 1:
                                        if (cell.getCellType() == CellType.STRING) {
                                            String cellValue = cell.getStringCellValue();
                                            if (!cellValue.isEmpty()) {
                                                airlineD = cellValue;
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (cell.getCellType() == CellType.NUMERIC) {
                                            double numericValue = cell.getNumericCellValue();
                                            String stringValue = decimalFormat.format(numericValue);
                                            numFlightAr = stringValue;
                                        } else if (cell.getCellType() == CellType.STRING) {
                                            String cellValue = cell.getStringCellValue();
                                            if (!cellValue.isEmpty()) {
                                                numFlightAr = cellValue;
                                            }
                                        }
                                        break;
                                    case 3:
                                        if (cell.getCellType() == CellType.NUMERIC) {
                                            double numericValue = cell.getNumericCellValue();
                                            String stringValue = decimalFormat.format(numericValue);
                                            numFlightDep = stringValue;
                                        } else if (cell.getCellType() == CellType.STRING) {
                                            String cellValue = cell.getStringCellValue();
                                            if (!cellValue.isEmpty()) {
                                                numFlightDep = cellValue;
                                            }
                                        }
                                        break;
                                    case 4:
                                        if (cell.getCellType() == CellType.NUMERIC) {
                                            double numericValue = cell.getNumericCellValue();
                                            String stringValue = decimalFormat.format(numericValue);
                                            aircraft = stringValue;
                                        } else if (cell.getCellType() == CellType.STRING) {
                                            String cellValue = cell.getStringCellValue();
                                            if (!cellValue.isEmpty()) {
                                                aircraft = cellValue;
                                            }
                                        }
                                        break;
                                }
                            }
                            FlightPairs flight = new FlightPairs(aircraft, airlineA, airlineD, numFlightAr, numFlightDep);
                            flightPairs.add(flight);
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
        return flightPairs;
    }
}
