import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class DataProcessorLEVEL {

    public static ArrayList<String> matriculasLEVEL = new ArrayList<>();

    public static ArrayList<FlightARMS> processFlightDataLEVEL(Sheet sheet) {
        ArrayList<FlightARMS> flightListARMS = new ArrayList<>();

        int i = 0;

        boolean error = false;

        try {
            for (Row row : sheet) {
                LocalDate date = null;
                String aircraft = null;
                String airline = null;
                String numFlight = null;
                String origen = null;
                String dest = null;
                String matricula = null;
                String type = null;
                LocalDate dateS = null;
                LocalTime timeS = null;
                LocalDate dateL = null;
                LocalTime timeL = null;
                int seats = 0;
                if (i > 0) {
                    for (Cell cell : row) {
                        switch (cell.getColumnIndex()) {
                            case 0: //fecha
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        date = cell.getLocalDateTimeCellValue().toLocalDate();
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        date = LocalDate.parse(cell.getStringCellValue(), format);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                }
                                break;
                            case 1: //Asientos
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    try {
                                        seats = Integer.parseInt(cell.getStringCellValue());
                                    } catch (NumberFormatException e) {
                                        error = true;
                                    }
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        seats = (int) cell.getNumericCellValue();
                                    } catch (NumberFormatException e) {
                                        error = true;
                                    }
                                }
                                break;
                            case 3: //Avion
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    aircraft = cell.getStringCellValue();
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    error = true;
                                }
                                break;
                            case 4: //Compañía
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    airline = cell.getStringCellValue();
                                } else {
                                    error = true;
                                }
                                break;
                            case 5: // Nº vuelo
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    try {
                                        String flightNumber = cell.getStringCellValue();
                                        if (flightNumber.startsWith("IB")) {
                                            String val = flightNumber.substring(2);
                                            numFlight = val.trim();
                                        }
                                    } catch (NumberFormatException e) {
                                        numFlight = "-";
                                    }
                                } else {
                                    try {
                                        numFlight = String.valueOf(cell.getNumericCellValue());
                                    } catch (NumberFormatException e) {
                                        error = true;
                                    }
                                }
                                break;
                            case 6: //Origen
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    origen = cell.getStringCellValue();
                                } else {
                                    error = true;
                                }
                                break;
                            case 7: //Destino
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    dest = cell.getStringCellValue();
                                } else {
                                    error = true;
                                }
                                break;
                            case 8: //Matricula
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    matricula = cell.getStringCellValue();
                                } else {
                                    matricula = "-";
                                    error = true;
                                }
                                boolean find = false;
                                if (matriculasLEVEL.size() > 1) {
                                    for (String matriculaVar : matriculasLEVEL) {
                                        if (matricula.equals(matriculaVar)) {
                                            find = true;
                                        }
                                    }
                                    if (find != true) {
                                        matriculasLEVEL.add(matricula);
                                    }
                                } else {
                                    matriculasLEVEL.add(matricula);
                                }

                                break;
                            case 9: //Tipo servicio
                                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != "") {
                                    type = cell.getStringCellValue();
                                } else {
                                    type = "-";
                                    error = true;
                                }
                                break;
                            case 10: //Fecha salida
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        dateS = cell.getLocalDateTimeCellValue().toLocalDate();
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        dateS = LocalDate.parse(cell.getStringCellValue(), format);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                }
                                break;

                            case 11: // Hora Salida
                                if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                        timeS = LocalTime.parse(cell.getStringCellValue(), format);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                        timeS = LocalTime.parse("00:00", format);
                                    }
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
                                        Instant instant = javaDate.toInstant();
                                        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                                        timeS = dateTime.toLocalTime();
                                    } catch (DateTimeException e) {
                                        error = true;
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                        timeS = LocalTime.parse("00:00", format);
                                    }
                                } else {
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                    timeS = LocalTime.parse("00:00", format);
                                }
                                break;
                            case 12: //Fecha llegada
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        dateL = cell.getLocalDateTimeCellValue().toLocalDate();
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        dateL = LocalDate.parse(cell.getStringCellValue(), format);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                    }
                                }
                                break;

                            case 13: // Hora llegada
                                if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                    try {
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                        timeL = LocalTime.parse(cell.getStringCellValue(), format);
                                    } catch (DateTimeParseException e) {
                                        error = true;
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                        timeL = LocalTime.parse("00:00", format);
                                    }
                                } else if (cell.getCellType() == CellType.NUMERIC) {
                                    try {
                                        Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
                                        Instant instant = javaDate.toInstant();
                                        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                                        timeL = dateTime.toLocalTime();
                                    } catch (DateTimeException e) {
                                        error = true;
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                        timeL = LocalTime.parse("00:00", format);
                                    }
                                } else {
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                    timeL = LocalTime.parse("00:00", format);
                                }
                                break;
                        }
                    }
                    FlightARMS flight = new FlightARMS(date, seats, aircraft, airline, numFlight, origen, dest, matricula, type, dateS, timeS, dateL, timeL);
                    flightListARMS.add(flight);
                }
                i++;
            }
            if (error) {
                return null;
            } else {
                return flightListARMS;
            }
        } catch (Exception e){
            return null;
        }
    }
}
