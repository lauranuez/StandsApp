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

    static Flight getFlightByNumber(String flightNumber, ArrayList<Flight> flightListWeek) {
        Flight flightRequired = null;
        for (Flight flight : flightListWeek) {
            if (flight.id.equals(flightNumber)) {
                flightRequired = flight;
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
                                flight.timeD, flight.as, flight.af, flight.flightTypeD, flight.zonaS, flight.aircraftD, flight.seats, flight.numWeek, flight.dayWeek, flight.stand, flight.stand2, flight.puerta, flight.id, "PDD", flight.carreteo);
                        flights.add(pernocta);
                    } else if (flight.dateA.isEqual(date) && flight.dateD.isAfter(date)) {
                        Flight pernocta = new Flight(flight.dateA, flight.AH, flight.terminal, flight.pernocta, flight.aircraftA, flight.airlineA, flight.numA, flight.timeA, flight.origenAirport, flight.AA, flight.flightTypeA, flight.zonaL, flight.dateD, flight.airlineD, flight.numD,
                                LocalTime.parse("23:55"), flight.as, flight.af, flight.flightTypeD, flight.zonaS, flight.aircraftD, flight.seats, flight.numWeek, flight.dayWeek, flight.stand, flight.stand2, flight.puerta, flight.id, "PAD", flight.carreteo);
                        flights.add(pernocta);
                    } else if (flight.dateA.isBefore(date) && flight.dateD.isAfter(date)) {
                        Flight pernocta = new Flight(flight.dateA, flight.AH, flight.terminal, flight.pernocta, flight.aircraftA, flight.airlineA, flight.numA, LocalTime.parse("00:00"), flight.origenAirport, flight.AA, flight.flightTypeA, flight.zonaL, flight.dateD, flight.airlineD, flight.numD,
                                LocalTime.parse("23:55"), flight.as, flight.af, flight.flightTypeD, flight.zonaS, flight.aircraftD, flight.seats, flight.numWeek, flight.dayWeek, flight.stand, flight.stand2, flight.puerta, flight.id, "PL", flight.carreteo);
                        flights.add(pernocta);
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
       System.out.println("----------------------------FlightsDAY -> " + day + "-----------------------------------------------------------");
       System.out.println();
       for (Flight flight : flights) {
           System.out.println(" id: " + flight.id + " dateAr: " + flight.dateA  + " timeA: " + flight.timeA + " timeDep: " + flight.timeD + " dateDep: " + flight.dateD + " pernocta: " + flight.pernocta + " stand: " + flight.stand + " dayWeek: " + flight.dayWeek + " flightType " + flight.type);
       }
       System.out.println("--------------------------------------------------------------");

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

    public static ArrayList<Flight> getFlightsDayStands(String day, String[] rowNames, String numWeek, ArrayList<Flight> flightListWeek) {
        ArrayList<Flight> flightsForDay = new ArrayList<>();
        ArrayList<Flight> flights = getFlightsDay(day, numWeek, flightListWeek);

        if (flights.size() > 0) {
            int i = 3;
            for (Flight flight : flights) {
                if (!flight.type.equals("P")) {
                    if (flight.type.equals("PL") || flight.type.equals("PDD") || flight.type.equals("PAD") || flight.type.equals("P")) {
                        if (flight.stand == null) {
                            String stand = rowNames[i];
                            ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flights, stand, flight);
                            if (flightsCollision.size() != 0) {
                                i++;
                            } else {
                                flight.stand = rowNames[i];
                                Flight flightMainList = getFlightByNumber(flight.id, flightListWeek);
                                flightMainList.stand = stand;
                                flightsForDay.add(flight);
                            }
                        }
                        else{
                            flightsForDay.add(flight);
                        }
                    } else {
                        String stand = rowNames[i];
                        ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flights, stand, flight);
                        if (flightsCollision.size() != 0) {
                            i++;
                        } else {
                            flight.stand = rowNames[i];
                            flightsForDay.add(flight);
                        }
                    }
                    if (i == 13) {
                        break;
                    }
                    i++;
                }
            }
        }
        System.out.println("----------------------------FlightsSTAND -> " + day + "--------------------------------------------+++---------------");
        System.out.println();
        for (Flight flight : flightsForDay) {
            System.out.println(" id: " + flight.id + " dateAr: " + flight.dateA  + " timeA: " + flight.timeA + " timeDep: " + flight.timeD + " dateDep: " + flight.dateD + " pernocta: " + flight.pernocta + " stand: " + flight.stand + " dayWeek " + flight.dayWeek + " flightType " + flight.type );
        }
        System.out.println("--------------------------------------------------------------");
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
