import java.time.LocalDate;
import java.time.LocalTime;

public class FlightLEVEL {

    int asientos;
    String aircraft;
    String airline;
    LocalDate dateAr;
    String numFlightAr;
    String origenAr;
    String destAr;
    LocalTime timeAr;
    LocalDate dateDep;
    String numFlightDep;
    String origenDep;
    String destDep;
    LocalTime timeDep;
    String matricula;
    int pernocta;

    public FlightLEVEL(int asientos, String aircraft, String airline,LocalDate dateAr, String numFlightAr, String origenAr, String destAr,
                      String matricula, LocalTime timeAr, LocalTime timeDep, LocalDate dateDep, String numFlightDep, String origenDep, String destDep, int pernocta){

        this.dateAr = dateAr;
        this.asientos = asientos;
        this.aircraft = aircraft;
        this.airline = airline;
        this.numFlightAr = numFlightAr;
        this.origenAr = origenAr;
        this.destAr = destAr;
        this.matricula = matricula;
        this.timeAr = timeAr;
        this.dateDep = dateDep;
        this.numFlightDep = numFlightDep;
        this.origenDep = origenDep;
        this.destDep = destDep;
        this.timeDep = timeDep;
        this.pernocta = pernocta;
    }
}
