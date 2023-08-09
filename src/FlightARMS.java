import java.time.LocalDate;
import java.time.LocalTime;

public class FlightARMS {
    LocalDate date;
    int asientos;
    String aircraft;
    String airline;
    String numFlight;
    String origen;
    String dest;
    String matricula;
    String type;
    LocalDate dateS;
    LocalTime timeS;
    LocalDate dateL;
    LocalTime timeL;

    public FlightARMS(LocalDate date, int asientos, String aircraft, String airline, String numFlight, String origen, String dest,
                  String matricula, String type, LocalDate dateS, LocalTime timeS, LocalDate dateL,
                  LocalTime timeL){

        this.date = date;
        this.asientos = asientos;
        this.aircraft = aircraft;
        this.airline = airline;
        this.numFlight = numFlight;
        this.origen = origen;
        this.dest = dest;
        this.matricula = matricula;
        this.type = type;
        this.dateS = dateS;
        this.timeS = timeS;
        this.dateL = dateL;
        this.timeL = timeL;
    }
}
