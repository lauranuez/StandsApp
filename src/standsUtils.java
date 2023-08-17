import java.util.ArrayList;

public class standsUtils {

    public static ArrayList<Flight> flightsCollision(ArrayList<Flight> flightsDay, String newStand, Flight flight){
        ArrayList<Flight> flightsCollision = new ArrayList<>();

        for (Flight flightFor : flightsDay) {
            try{
                if (flightFor.stand.equals(newStand)) {
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


}
