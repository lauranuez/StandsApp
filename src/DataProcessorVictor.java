import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DataProcessorVictor {
    public static ArrayList<FlightLEVEL> processFlightDataVictor(Sheet sheet) {
        ArrayList<FlightLEVEL> flightListVictor = new ArrayList<>();

        int i = 0;

        boolean error = false;

        for (Row row : sheet) {
            int asientos = 0;
            String aircraft = null; /////
            String airline = "IBE";
            LocalDate dateAr = null; /////////
            String numFlightAr = null; ////////////////////
            String origenAr = null; ///////
            String destAr = null; ////////////
            LocalTime timeAr = null; ////////////
            LocalDate dateDep = null;////////
            String numFlightDep = null;///////////
            String origenDep = null;/////////////
            String destDep = null;/////////
            LocalTime timeDep = null;////////
            String matricula = "-";
            int pernocta = 0;
            boolean empty = false;
            if (i > 1) {
                for (Cell cell : row) {
                    if (!empty) {
                        switch (cell.getColumnIndex()) {
                            case 1:
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() == " " || cell == null || cell.getCellType() == CellType.BLANK) {
                                    empty = true;
                                }
                                break;
                            case 2: //Avion
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    aircraft = cell.getStringCellValue();
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        double aircraftDouble = cell.getNumericCellValue();
                                        aircraft = String.format("%.0f", aircraftDouble);
                                    } catch (NumberFormatException e) {
                                        error = true;
                                    }
                                }
                                break;
                            case 4: //NumFlightAr
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    try {
                                        numFlightAr = cell.getStringCellValue();
                                    } catch (NumberFormatException e) {
                                        error = true;
                                    }
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        double numFlightArDouble = cell.getNumericCellValue();
                                        numFlightAr = String.format("%.0f", numFlightArDouble);
                                    } catch (NumberFormatException e) {
                                        error = true;
                                    }
                                }
                                break;
                            case 5: //origenAr
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    origenAr = cell.getStringCellValue();
                                }
                                break;
                            case 6: //DestAr
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    destAr = cell.getStringCellValue();
                                }
                                break;
                            case 11: //dateAr
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        dateAr = cell.getLocalDateTimeCellValue().toLocalDate();
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        dateAr = LocalDate.parse(cell.getStringCellValue(), format);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                }
                                break;
                            case 12: // Time Arrival
                                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        timeAr = LocalTime.parse(cell.getStringCellValue(), format);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                        timeAr = LocalTime.of(0, 0); // Default value if parsing fails
                                    }
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    double numericValue = cell.getNumericCellValue();
                                    long totalMillis = Math.round(numericValue * 24 * 60 * 60 * 1000); // Round to the nearest millisecond
                                    timeAr = LocalTime.ofNanoOfDay(TimeUnit.MILLISECONDS.toNanos(totalMillis));
                                } else {
                                    timeAr = LocalTime.of(0, 0); // Default value for non-numeric and empty cells
                                }
                                break;

                            case 17: // FL number
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    try {
                                        numFlightDep = cell.getStringCellValue();
                                    } catch (NumberFormatException e) {
                                        error = true;
                                    }
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        double numFlightArDouble = cell.getNumericCellValue();
                                        numFlightDep = String.format("%.0f", numFlightArDouble);
                                    } catch (NumberFormatException e) {
                                        error = true;
                                    }
                                }
                                break;
                            case 18: //origenDep
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    origenDep = cell.getStringCellValue();
                                } else {
                                    empty = true;
                                }
                                break;
                            case 19: //DestDep
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    destDep = cell.getStringCellValue();
                                } else {
                                    empty = true;
                                }
                                break;
                            case 22: //date departure
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        dateDep = cell.getLocalDateTimeCellValue().toLocalDate();
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        DateTimeFormatter formatD = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        dateDep = LocalDate.parse(cell.getStringCellValue(), formatD);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                }
                                break;
                            case 23: //time Dep
                                DateTimeFormatter formatT = DateTimeFormatter.ofPattern("HH:mm");
                                if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        timeDep = LocalTime.parse(cell.getStringCellValue(), formatT);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                        timeDep = LocalTime.parse("00:00", formatT);
                                    }
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    double numericValue = cell.getNumericCellValue();
                                    long totalMillis = Math.round(numericValue * 24 * 60 * 60 * 1000); // Round to the nearest millisecond
                                    timeDep = LocalTime.ofNanoOfDay(TimeUnit.MILLISECONDS.toNanos(totalMillis));
                                } else {
                                    timeDep = LocalTime.of(0, 0); // Default value for non-numeric and empty cells
                                }
                                break;
                        }
                    }
                }
                if (dateDep != null & dateAr != null){
                    if (dateDep.isAfter(dateAr)){
                        pernocta = (int) ChronoUnit.DAYS.between(dateDep, dateAr);
                    }
                    FlightLEVEL flight = new FlightLEVEL(asientos,aircraft,airline,dateAr,numFlightAr,origenAr,destAr,matricula,timeAr,timeDep,dateDep,numFlightDep,origenDep,destDep,Math.abs(pernocta));
                    flightListVictor.add(flight);
                }
            }
            i++;
        }
        if (error){
            return null;
        } else {
            return flightListVictor;
        }
    }
}