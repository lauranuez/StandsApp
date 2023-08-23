import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.apache.poi.ss.usermodel.*;

public class DataProcessor {
    public static ArrayList<Flight> processFlightData(Sheet sheet) {
        ArrayList<Flight> flightList = new ArrayList<>();

        int i = 0;

        boolean error = false;
        for (Row row : sheet) {
            LocalDate dateA = null;
            String AH = null;
            String terminal = null;
            int pernocta = 0;
            String aircraftA = null;
            String airlineA = null;
            String numA = null;
            LocalTime timeA = null;
            String origenAirport = null;
            String AA = null;
            String flightTypeA = null;
            String zonaLA = null;
            LocalDate dateD = null;
            String airlineD = null;
            String numD = null;
            LocalTime timeD = null;
            String as = null;
            String af = null;
            String flightTypeD = null;
            String zonaSA = null;
            String aircraftD = null;
            int seats = 0;

            if (i > 1) {
                for (Cell cell : row) {
                    switch (cell.getColumnIndex()) {
                        case 0: //fecha LLegada
                            if (cell.getCellType() == CellType.NUMERIC) {
                                try {
                                    dateA = cell.getLocalDateTimeCellValue().toLocalDate();
                                } catch (DateTimeParseException e) {
                                    error = true;
                                }
                            } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                try {
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    dateA = LocalDate.parse(cell.getStringCellValue(), format);
                                } catch (DateTimeParseException e) {
                                    error = true;
                                }
                            }else {
                                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                dateA = LocalDate.parse("01/01/0001", format);
                            }
                            break;
                        case 2: //AH
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                AH = cell.getStringCellValue();
                            } else {
                                AH = "-";
                            }
                            break;
                        case 3: //Terminal
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                terminal = cell.getStringCellValue();
                            } else {
                                terminal = "-";
                            }
                            break;
                        case 4: //Pernocta
                            if (cell.getCellType() == CellType.NUMERIC) {
                                try{
                                    pernocta = (int) cell.getNumericCellValue();
                                }catch (NumberFormatException e) {
                                    pernocta = 0;
                                }
                            } else if(cell.getCellType() == CellType.STRING){
                                try{
                                    pernocta = Integer.parseInt(cell.getStringCellValue());
                                }catch (NumberFormatException e) {
                                    pernocta = 0;
                                }
                            }
                            else {
                                pernocta = 0;
                            }
                            break;
                        case 5: //Asientos
                            if (cell.getCellType() == CellType.NUMERIC) {
                                seats = (int) cell.getNumericCellValue();
                            }else if(cell.getCellType() == CellType.STRING){
                                try {
                                    seats = Integer.parseInt(cell.getStringCellValue());
                                }catch (NumberFormatException e) {
                                    error = true;
                                }
                            } else {
                                seats = 0;
                            }
                            break;
                        case 6: //avion LLegada
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                aircraftA = cell.getStringCellValue();
                            } else if(cell.getCellType() == CellType.NUMERIC){
                                error = true;
                            }else {
                                aircraftA = "-";
                            }
                            break;
                        case 8: //Cia ll
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                airlineA = cell.getStringCellValue();
                            } else {
                                airlineA = "-";
                            }
                            break;
                        case 9: //Num Ll
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                numA = cell.getStringCellValue();
                            } else if(cell.getCellType() == CellType.NUMERIC){
                                numA = String.valueOf(cell.getNumericCellValue());
                            }
                            else {
                                numA = "-";
                            }
                            break;
                        case 10: //Hora Ll
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                try {
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                    timeA = LocalTime.parse(cell.getStringCellValue(), format);
                                } catch (DateTimeParseException e) {
                                    error = true;
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                    timeA = LocalTime.parse("00:00", format);
                                }
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                try {
                                    Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
                                    Instant instant = javaDate.toInstant();
                                    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                                    timeA = dateTime.toLocalTime();
                                } catch (DateTimeException e) {
                                    error = true;
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                    timeA = LocalTime.parse("00:00", format);
                                }
                            } else {
                                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                timeA = LocalTime.parse("00:00", format);
                            }
                            break;
                        case 11: //AO
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                origenAirport = cell.getStringCellValue();
                            } else {
                                origenAirport = "-";
                            }
                            break;
                        case 12: //AA
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                AA = cell.getStringCellValue();
                            } else {
                                AA = "-";
                            }
                            break;
                        case 13: //Clase L
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                flightTypeA = cell.getStringCellValue();
                            } else {
                                flightTypeA = "-";
                            }
                            break;
                        case 14: //Zona L
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                zonaLA = cell.getStringCellValue();
                            } else {
                                zonaLA = "-";
                            }
                            break;
                        case 18: //Fecha Salida
                            if (cell.getCellType() == CellType.NUMERIC) {
                                try {
                                    dateD = cell.getLocalDateTimeCellValue().toLocalDate();
                                } catch (DateTimeParseException e) {
                                    error = true;
                                }
                            } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                try {
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    dateD = LocalDate.parse(cell.getStringCellValue(), format);
                                } catch (DateTimeParseException e) {
                                    error = true;
                                }
                            }else {
                                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                dateD = LocalDate.parse("01/01/0001", format);
                            }
                            break;
                        case 19: //Cía S.
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                airlineD = cell.getStringCellValue();
                            } else {
                                airlineD = "-";
                            }
                            break;
                        case 20: //num S.
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                numD = cell.getStringCellValue();
                            } else if(cell.getCellType() == CellType.NUMERIC){
                                numD = String.valueOf(cell.getNumericCellValue());
                            } else {
                                numD = "-";
                            }
                            break;
                        case 21: //Hora S.
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                try {
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                    timeD = LocalTime.parse(cell.getStringCellValue(), format);
                                } catch (DateTimeParseException e) {
                                    error = true;
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                    timeD = LocalTime.parse("00:00", format);
                                }
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                try {
                                    Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
                                    Instant instant = javaDate.toInstant();
                                    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                                    timeD = dateTime.toLocalTime();
                                } catch (DateTimeException e) {
                                    error = true;
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                    timeD = LocalTime.parse("00:00", format);
                                }
                            } else {
                                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                                timeD = LocalTime.parse("00:00", format);
                            }
                            break;
                        case 22: //AS
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                as = cell.getStringCellValue();
                            } else {
                                as = "-";
                            }
                            break;
                        case 23: //AF
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                af = cell.getStringCellValue();
                            } else {
                                af = "-";
                            }
                            break;
                        case 24: //Clase S
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                flightTypeD = cell.getStringCellValue();
                            } else {
                                flightTypeD = "-";
                            }
                            break;
                        case 25: //Zona S
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                zonaSA = cell.getStringCellValue();
                            } else {
                                zonaSA = "-";
                            }
                            break;
                        case 28: //Avion S
                            if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                aircraftD = cell.getStringCellValue();
                            } else if(cell.getCellType() == CellType.NUMERIC){
                                error = true;
                            }else {
                                aircraftD = "-";
                            }
                            break;
                    }
                }
                if (dateA != null) {
                    DayOfWeek dayOfWeek = dateA.getDayOfWeek();
                    String dayWeek = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());

                    int numWeek = dateA.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

                    Flight flight = new Flight(dateA, AH, terminal, pernocta, aircraftA, airlineA, numA, timeA, origenAirport, AA, flightTypeA, zonaLA, dateD, airlineD, numD, timeD, as, af, flightTypeD, zonaSA, aircraftD, seats, numWeek, dayWeek, null, null, null, null, "F", "N");
                    flightList.add(flight);
                }
            }
            i++;
        }
        if (error){
            return null;
        }else{
            return flightList;
        }

    }
}
