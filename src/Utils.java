import com.sun.org.apache.xpath.internal.operations.Bool;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Utils {
    static ArrayList<LocalTime> generarVectorHoras() {
        ArrayList<LocalTime> horas = new ArrayList<>();
        LocalTime horaInicial = LocalTime.of(0, 0); // 00:00
        LocalTime horaFinal = LocalTime.of(23, 55); // 23:55

        int intervaloMin = 5;
        int totalHoras = (horaFinal.toSecondOfDay() - horaInicial.toSecondOfDay()) / (intervaloMin * 60);

        for (int i = 0; i <= totalHoras; i++) {
            horas.add(horaInicial.plusMinutes((long) i * intervaloMin));
        }
        return horas;
    }

    static Flight getFlightByNumber(String flightNumber, ArrayList<Flight> flightListWeek) {
        for (Flight flight : flightListWeek) {
            if (flight.id.equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

   public static ArrayList<Flight> getFlightsDay(String day, ArrayList<Flight> flightListWeek, String[] rowNames ) {
        ArrayList<Flight> flights = new ArrayList<>();

        for (Flight flight : flightListWeek) {
            if (flight.dayWeek.equals(day)) {
                flights.add(flight);
            }
        }
        return flights;
    }

    public static ArrayList<Flight> getFlightsDayStands(String day, ArrayList<Flight> flightListWeek, String[] rowNames ) {
        ArrayList<Flight> flightsForDay = new ArrayList<>();
        ArrayList<Flight> flights = new ArrayList<>();

        for (Flight flight : flightListWeek) {
            if (flight.dayWeek.equals(day)) {
                flights.add(flight);
            }
        }

        if (flights.size() > 0){
            int i = 0;
            for (Flight flight : flights) {
                flight.stand = rowNames[i];
                flightsForDay.add(flight);
                if (i == 10) {
                    break;
                }
                i++;
            }
        }
        return flightsForDay;
    }

    public static DayOfWeek dayOfWeekWithDayName(String dayName){
        DayOfWeek dayOfWeek = null;
        if (dayName.equals("lunes")){
            dayOfWeek = DayOfWeek.valueOf("MONDAY");
        } else if (dayName.equals("martes")){
            dayOfWeek = DayOfWeek.valueOf("TUESDAY");
        } else if (dayName.equals("miércoles")){
            dayOfWeek = DayOfWeek.valueOf("WEDNESDAY");
        } else if (dayName.equals("jueves")){
            dayOfWeek = DayOfWeek.valueOf("THURSDAY");
        } else if (dayName.equals("viernes")){
            dayOfWeek = DayOfWeek.valueOf("FRIDAY");
        } else if (dayName.equals("sábado")){
            dayOfWeek = DayOfWeek.valueOf("SATURDAY");
        } else if (dayName.equals("domingo")){
            dayOfWeek = DayOfWeek.valueOf("SUNDAY");
        }
        return dayOfWeek;
    }
    public static Boolean standSearch(String[] rowNames, String newStand){
        Boolean standSearchBool = false;
        for (String standSearch : rowNames){
            if(standSearch.equals(newStand)){
                standSearchBool = true;
                break;
            }
        }
        return standSearchBool;
    }
}
