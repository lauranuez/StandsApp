import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class SetNumFlights {
    static ArrayList<FlightPairs> flightPairs = new ArrayList<>();

    public static ArrayList<Flight> associateExcel(ArrayList<Flight> flights) {
        ArrayList<Flight> flightListAEA = flights;
        ArrayList<Flight> removeFlightList = new ArrayList<>();

        loadData();

        if (!flightPairs.isEmpty()) {
            for (FlightPairs pair : flightPairs) {
                for (Flight flight : flightListAEA) {
                    if (flight.airlineA.equals(pair.airline) && flight.numA.equals(pair.numFlightAr)) {
                        for (Flight flight1 : flightListAEA) {
                            if (flight1.numD.equals(pair.numFlightDep) && flight1.aircraftD.equals(flight.aircraftA)) {
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
                flightListAEA.removeAll(removeFlightList);
            }
            return flightListAEA;
        }
        else {
            return null;
        }
    }

    public static ArrayList<FlightPairs> loadData() {
        String filePath = "files/AEA.xlsx";

        try (InputStream inputStream = SetNumFlights.class.getResourceAsStream(filePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet("ASOCIAR");
            if (sheet != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#");
                for (Row row : sheet) {
                    String aircraft = null;
                    String airline = null;
                    String numFlightAr = null;
                    String numFlightDep = null;
                    for (Cell cell : row) {
                        switch (cell.getColumnIndex()) {
                            case 0:
                                if (cell.getCellType() == CellType.STRING) {
                                    String cellValue = cell.getStringCellValue();
                                    if (!cellValue.isEmpty()) {
                                        airline = cellValue;
                                    }
                                }
                                break;
                            case 1:
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
                            case 2:
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
                            case 3:
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
                    FlightPairs flight = new FlightPairs(aircraft, airline, numFlightAr, numFlightDep);
                    flightPairs.add(flight);
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
