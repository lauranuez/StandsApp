import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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

    static ArrayList<Flight> getFlightByNumber(String flightNumber, ArrayList<Flight> flightListWeek) {
        ArrayList<Flight> flightRequired = new ArrayList<>();
        for (Flight flight : flightListWeek) {
            if (flight.id.equals(flightNumber)) {
                flightRequired.add(flight);
            }
        }
        return flightRequired;
    }

    static Flight getFlightByNumberTable(String flightNumber, ArrayList<Flight> flightListWeek, String column) {
        Flight flightRequired = null;
        for (Flight flight : flightListWeek) {
            if (flight.id.equals(flightNumber)){
                if (flight.carreteo.equals("Y")){
                     if (flight.timeA.equals(LocalTime.parse(column)) && flight.stand2 == null) {
                         flightRequired = flight;
                    }
                }
                else{
                    flightRequired = flight;
                }
            }
        }
        return flightRequired;
    }

   public static ArrayList<Flight> getFlightsDay(String day, String numWeek, ArrayList<Flight> flightListWeek) {
        ArrayList<Flight> flights = new ArrayList<>();
        LocalDate date = getLocalDateWithDayName(day, numWeek);

        for (Flight flight : flightListWeek) {
            if (flight.stand2 == null) {
                if (flight.pernocta == 0 && !flight.type.equals("P")) {
                    if (flight.dayWeek.equals(day)) {
                        flights.add(flight);
                    }
                } else {
                    if (flight.dateA.isBefore(date) && flight.dateD.isEqual(date)) {
                        Flight pernocta = new Flight(flight.dateA, flight.AH, flight.terminal, flight.pernocta, flight.aircraftA, flight.airlineA, flight.numA, LocalTime.parse("00:00"), flight.origenAirport, flight.AA, flight.flightTypeA, flight.zonaL, flight.dateD, flight.airlineD, flight.numD,
                                flight.timeD, flight.as, flight.af, flight.flightTypeD, flight.zonaS, flight.aircraftD, flight.seats, flight.numWeek, flight.dayWeek, flight.stand, flight.stand2, flight.puerta, flight.id, "PDD", flight.carreteo, flight.id2 , flight.rowExcel);
                        flights.add(pernocta);
                    } else if (flight.dateA.isEqual(date) && flight.dateD.isAfter(date)) {
                        Flight pernocta = new Flight(flight.dateA, flight.AH, flight.terminal, flight.pernocta, flight.aircraftA, flight.airlineA, flight.numA, flight.timeA, flight.origenAirport, flight.AA, flight.flightTypeA, flight.zonaL, flight.dateD, flight.airlineD, flight.numD,
                                LocalTime.parse("23:55"), flight.as, flight.af, flight.flightTypeD, flight.zonaS, flight.aircraftD, flight.seats, flight.numWeek, flight.dayWeek, flight.stand, flight.stand2, flight.puerta, flight.id, "PAD", flight.carreteo, flight.id2, flight.rowExcel);
                        flights.add(pernocta);
                    } else if (flight.dateA.isBefore(date) && flight.dateD.isAfter(date)) {
                        Flight pernocta = new Flight(flight.dateA, flight.AH, flight.terminal, flight.pernocta, flight.aircraftA, flight.airlineA, flight.numA, LocalTime.parse("00:00"), flight.origenAirport, flight.AA, flight.flightTypeA, flight.zonaL, flight.dateD, flight.airlineD, flight.numD,
                                LocalTime.parse("23:55"), flight.as, flight.af, flight.flightTypeD, flight.zonaS, flight.aircraftD, flight.seats, flight.numWeek, flight.dayWeek, flight.stand, flight.stand2, flight.puerta, flight.id, "PL", flight.carreteo, flight.id2, flight.rowExcel);
                        flights.add(pernocta);
                    }
                    else if (flight.carreteo.equals("Y") && flight.dateD.equals(date)) {
                        flights.add(flight);
                    }
                }
            }
            /*
            String flightNumWeek = String.valueOf(flight.numWeek);
            if (numWeekSelectedPrevious == flight.numWeek  && flight.pernocta != 0){
                if (flight.dateD.isEqual(mondayOfWeek) || (flight.dateD.isBefore(sundayOfWeek) && flight.dateD.isAfter(mondayOfWeek)) || flight.dateD.isEqual(sundayOfWeek)){
                    flight.type = "P";
                    flightListWeek.add(flight);
                }
            } else if (numWeekSelected.equals(flightNumWeek)){
                flightListWeek.add(flight);
            }
            */
        }

       return flights;
    }

    public static LocalDate getLocalDateWithDayName(String day, String numWeek){
        DayOfWeek dayOfWeek = Utils.dayOfWeekWithDayName(day);

        LocalDate date = LocalDate.now()
                .with(TemporalAdjusters.firstDayOfYear())
                .plusWeeks(Integer.parseInt(numWeek) - 1);

        if (dayOfWeek == DayOfWeek.SUNDAY) {
            date = date.plusWeeks(1); // Agregar una semana si es domingo
        }

        date = date.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        return date;
    }

    public static void getFlightsDayStands(String day, ArrayList<Stand> rowNames, String numWeek, ArrayList<Flight> flightListWeek) {
        //ArrayList<Flight> flightsForDay = new ArrayList<>();
        ArrayList<Flight> flights = getFlightsDay(day, numWeek, flightListWeek);
        Boolean standAsignado;
        if (flights.size() > 0) {
            for (Flight flight : flights) {
                standAsignado = false;
                if (flight.stand == null) {
                    for (Stand stand: rowNames){
                        if (!stand.numStand.equals("HOLD1") && !stand.numStand.equals("HOLD2") && !stand.numStand.equals("HOLD3")  && !stand.numStand.equals("HOLD4") && !standAsignado) {
                            if (stand.terminal.equals(flight.terminal)) {
                                ArrayList<Flight> flightsCollisionStand = standsUtils.flightsCollision(flights, stand.numStand, flight);
                                if (flightsCollisionStand.size() < 1) {
                                    //ArrayList<Flight> flightsCollisionPer = standsUtils.flightsCollisionPernocta(flights, stand.numStand, flight, day, numWeek);
                                    //if (flightsCollisionPer.size() < 1) {
                                        ArrayList<Flight> flightMainList = getFlightByNumber(flight.id, flightListWeek);
                                        if (flightMainList.size() == 1) {
                                            flightMainList.get(0).stand = stand.numStand;
                                            flight.stand =stand.numStand;
                                            standAsignado = true;
                                            //flightsForDay.add(flight);
                                        }
                                    //}
                                }
                            }
                            if(standAsignado){
                                break;
                            }
                        }
                    }
                    if(standAsignado){
                        continue;
                    }
                }
            }
        }
    }
        /*
        if (flights.size() > 0) {
            int i = 4;
            for (Flight flight : flights) {
                if (!flight.type.equals("P")) {
                    if (flight.type.equals("PL") || flight.type.equals("PDD") || flight.type.equals("PAD") || flight.type.equals("P")) {
                        if (flight.stand == null) {
                            String stand = rowNames.get(i).numStand;
                            ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flights, stand, flight);
                            if (flightsCollision.size() != 0) {
                                i++;
                            } else {
                                flight.stand = rowNames.get(i).numStand;
                                ArrayList<Flight> flightMainList = getFlightByNumber(flight.id, flightListWeek);
                                if (flightMainList.size() == 1){
                                    flightMainList.get(0).stand = stand;
                                    flightsForDay.add(flight);
                                }
                            }
                        }
                        else{
                            flightsForDay.add(flight);
                        }
                    } else {
                        String stand = rowNames.get(i).numStand;
                        ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flights, stand, flight);
                        if (flightsCollision.size() != 0) {
                            i++;
                        } else {
                            flight.stand = rowNames.get(i).numStand;
                            flightsForDay.add(flight);
                        }
                    }
                    i++;
                }
            }
        }*/
    //}

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

    public static Boolean standSearch(ArrayList<Stand> rowNames, String newStand){
        Boolean standSearchBool = false;
        for (Stand standSearch : rowNames){
            if(newStand.equals(standSearch.numStand)){
                standSearchBool = true;
                break;
            }
        }
        return standSearchBool;
    }

    public static Boolean flightsCollisionStand(ArrayList<Flight> flightsDay, String newStand, Flight flight){
        ArrayList<Flight> flightsCollision = new ArrayList<>();
        LocalTime timeD = flight.timeD;
        Boolean collision = false;

        for (Flight flightFor : flightsDay) {
            try{
                if (flightFor.stand.equals(newStand) && !flightFor.id.equals(flight.id)) {
                    if (flight.timeA.isAfter(flightFor.timeA)) {
                        if (flight.timeA.isBefore(flightFor.timeD)) {
                            if (timeD.isAfter(flightFor.timeD) || timeD.equals(flightFor.timeD) || timeD.isBefore(flightFor.timeD)) {
                                flightsCollision.add(flightFor);
                            }
                        }
                    } else if (flight.timeA.isBefore(flightFor.timeA)) {
                        if (timeD.isAfter(flightFor.timeA)) {
                            if (timeD.isAfter(flightFor.timeD) || timeD.equals(flightFor.timeD) || timeD.isBefore(flightFor.timeD)) {
                                flightsCollision.add(flightFor);
                            }
                        }
                    } else {
                        if (timeD.isAfter(flightFor.timeD) || timeD.equals(flightFor.timeD) || timeD.isBefore(flightFor.timeD)) {
                            flightsCollision.add(flightFor);
                        }
                    }
                }

            }catch(NullPointerException e){

            }
        }

        if (flightsCollision.size() > 0){
            collision = true;
        }

        return collision;
    }
}
