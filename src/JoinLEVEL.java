import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class JoinLEVEL {
    public static ArrayList<FlightLEVEL> joinLEVEL(ArrayList<FlightARMS> flightsARMS, ArrayList<String> matriculas) {
        ArrayList<FlightLEVEL> flightListLEVEL = new ArrayList<>();

        for (String matricula : matriculas){
            for (FlightARMS flight : flightsARMS){
                if (matricula.equals(flight.matricula)){
                    if (flight.dest.equals("BCN")){ //Vuelo de llegada
                        Boolean find = false;
                        for (FlightARMS flight1 : flightsARMS){
                            int pernocta = 0;
                            if(!find && flight1.origen.equals("BCN") && flight1.matricula.equals(flight.matricula) && (flight1.dateS.isEqual(flight.dateL) || flight1.dateS.isAfter(flight.dateL))){
                                if (flight1.dateS.isEqual(flight.dateL)){
                                    if (flight1.timeS.isAfter(flight.timeL)){
                                        find = true;
                                    }
                                }
                                else if (flight1.dateS.isAfter(flight.dateL)){
                                    find = true;
                                    pernocta = (int) ChronoUnit.DAYS.between(flight1.dateS, flight.dateL);

                                }
                                if (find) {
                                    if (flight.aircraft.equals(flight1.aircraft)) {
                                        FlightLEVEL flightLEVEL = new FlightLEVEL(flight.asientos, flight.aircraft, flight.airline, flight.dateL, flight.numFlight, flight.origen, flight.dest,
                                                flight.matricula, flight.timeL, flight1.timeS, flight1.dateS, flight1.numFlight, flight1.origen, flight1.dest, Math.abs(pernocta));
                                        flightListLEVEL.add(flightLEVEL);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return flightListLEVEL;
    }

    public static ArrayList<Flight> associateLEVELs(ArrayList<Flight> flights, ArrayList<FlightLEVEL> flightListLEVEL) {
        ArrayList<Flight> flightListAssociate = flights;
        ArrayList<Flight> removeFlightList = new ArrayList<>();

        if (!flightListLEVEL.isEmpty()) {
            for (FlightLEVEL pair : flightListLEVEL) {
                    for (Flight flight : flightListAssociate) {
                        if (flight.airlineA.equals(pair.airline) && flight.numA.equals(pair.numFlightAr) && flight.dateA.isEqual(pair.dateAr)) {
                            for (Flight flight1 : flightListAssociate) {
                                if (flight1.numD.equals(pair.numFlightDep) && flight1.airlineD.equals(pair.airline) && flight1.aircraftD.equals(flight.aircraftA) && flight1.dateD.isEqual(pair.dateDep)) {
                                    if (flight.numD == "-") {
                                        flight.pernocta = pair.pernocta;
                                        flight.dateD = flight1.dateD;
                                        flight.airlineD = flight1.airlineD;
                                        flight.numD = flight1.numD;
                                        flight.timeD = flight1.timeD;
                                        flight.as = flight1.as;
                                        flight.af = flight1.af;
                                        flight.flightTypeD = flight1.flightTypeD;
                                        flight.aircraftD = flight1.aircraftD;
                                        removeFlightList.add(flight1);
                                        break;
                                    }
                                }
                            }
                        }
                }
            }
            flightListAssociate.removeAll(removeFlightList);
            for(Flight flightRemov: removeFlightList){
                System.out.println("FLIGHTREMOVELIST aircraftA: " + flightRemov.aircraftA + " aircraftD: " + flightRemov.aircraftD +" airlineA: " + flightRemov.airlineA + " airlineD: " + flightRemov.airlineD +" numD: " + flightRemov.numD + " numA: " + flightRemov.numA +" OA: " + flightRemov.origenAirport + " AF: " + flightRemov.af + " dateA: " + flightRemov.dateA +
                        " dateD: " + flightRemov.dateD + " timeAr: " + flightRemov.timeA + " timeDep: " + flightRemov.timeD + " pernocta: " + flightRemov.pernocta + " numFlightDep: " );
            }
            return flightListAssociate;
        }
        else {
            return null;
        }
    }

}
