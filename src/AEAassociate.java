import java.time.LocalDate;
import java.util.ArrayList;

public class AEAassociate {

    public static ArrayList<Flight> associate(ArrayList<Flight> flights) {
        //Leer archivo de texto
        ArrayList<Flight> flightListAEA = flights;
        ArrayList<Flight> removeFlightList = new ArrayList<>();
        String numAr1  = "7701";
        String numDep1 = "7706";
        String numAr2 = "7703";
        String numDep2 = "7708";

        for (Flight flight: flightListAEA)
        {
            if (flight.airlineA.equals("AEA")){
                if (flight.numA.equals(numAr1)){
                    for (Flight flight1 : flightListAEA){
                        if (flight1.numD.equals(numDep1) && flight1.dateA.isEqual(flight.dateA)){
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
                if (flight.numA.equals(numAr2)){
                    for (Flight flight1 : flightListAEA){
                        if (flight1.numD.equals(numDep2) && flight1.dateA.isEqual(flight.dateA) && flight1.aircraftD.equals(flight.aircraftA)){
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
        flightListAEA.removeAll(removeFlightList);
        return flightListAEA;
    }
}
