import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class standsUtils {

    public static ArrayList<Flight> flightsCollision(ArrayList<Flight> flightsDay, String newStand, Flight flight){
        ArrayList<Flight> flightsCollision = new ArrayList<>();

        for (Flight flightFor : flightsDay) {
            try{
                if (flightFor.stand.equals(newStand) && !flightFor.id.equals(flight.id)) {
                    if (flight.timeA.isAfter(flightFor.timeA)) {
                        if (flight.timeA.isBefore(flightFor.timeD)) {
                            if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                                flightsCollision.add(flightFor);
                            }
                        }
                    } else if (flight.timeA.isBefore(flightFor.timeA)) {
                        if (flight.timeD.isAfter(flightFor.timeA)) {
                            if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                                flightsCollision.add(flightFor);
                            }
                        }
                    } else {
                        if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                            flightsCollision.add(flightFor);
                        }
                    }
                }

            }catch(NullPointerException e){

            }
        }

        return flightsCollision;
    }

    public static ArrayList<Flight> flightsCollisionPernocta(ArrayList<Flight> flightWeek, String newStand, Flight flight, String day, String numWeekI){
        ArrayList<Flight> flightsCollision = new ArrayList<>();
        ArrayList<Flight> flightsToLook = new ArrayList<>();
        ArrayList<String> days = new ArrayList<>();

        LocalDate date = Utils.getLocalDateWithDayName(day,numWeekI);

        while ((date.isBefore(flight.dateD) || date.isEqual(flight.dateD))){
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String dayWeek = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
            int numWeek = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
            if(numWeekI.equals(String.valueOf(numWeek))) {
                flightsToLook = Utils.getFlightsDay(dayWeek, String.valueOf(numWeek), flightWeek);
                days.add(dayWeek);
            }
            else{
                break;
            }
            date = date.plusDays(1);
        }

        for (Flight flightFor : flightsToLook) {
            try{
                if (flightFor.stand.equals(newStand) && !flightFor.id.equals(flight.id)) {
                    if (flight.timeA.isAfter(flightFor.timeA)) {
                        if (flight.timeA.isBefore(flightFor.timeD)) {
                            if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                                flightsCollision.add(flightFor);
                            }
                        }
                    } else if (flight.timeA.isBefore(flightFor.timeA)) {
                        if (flight.timeD.isAfter(flightFor.timeA)) {
                            if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                                flightsCollision.add(flightFor);
                            }
                        }
                    } else {
                        if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                            flightsCollision.add(flightFor);
                        }
                    }
                }
            }catch(NullPointerException e){

            }
        }
        StandsMain.setDaysCollision(days);
        return flightsCollision;
    }

}
