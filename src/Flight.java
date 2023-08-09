import java.time.LocalDate;
import java.time.LocalTime;

public class Flight {
    LocalDate dateA;
    String AH;
    String terminal;
    int pernocta;
    String aircraftA;
    String airlineA;
    String numA;
    LocalTime timeA;
    String origenAirport;
    String AA;
    String flightTypeA;
    LocalDate dateD;
    String airlineD;
    String numD;
    LocalTime timeD;
    String as;
    String af;
    String flightTypeD;
    String aircraftD;
    int seats;
    int numWeek;
    String dayWeek;
    String stand;
    String puerta;
    String id;

    public Flight(LocalDate arrivalDate, String AH, String terminal, int pernocta, String aircraftA, String airlineA, String numA,
                  LocalTime timeA, String origenAirport, String AA, String flightTypeA, LocalDate dateD, String airlineD, String numD,
                  LocalTime timeD, String as, String af, String flightTypeD, String aircraftD, int seats, int week, String day, String stand, String puerta, String id){

        this.dateA = arrivalDate;
        this.AH = AH;
        this.terminal = terminal;
        this.pernocta = pernocta;
        this.aircraftA = aircraftA;
        this.airlineA = airlineA;
        this.numA = numA;
        this.timeA = timeA;
        this.origenAirport = origenAirport;
        this.AA = AA;
        this.flightTypeA = flightTypeA;
        this.dateD = dateD;
        this.airlineD = airlineD;
        this.numD = numD;
        this.timeD = timeD;
        this.as = as;
        this.af = af;
        this.flightTypeD = flightTypeD;
        this.aircraftD = aircraftD;
        this.seats = seats;
        this.numWeek = week;
        this.dayWeek = day;
        this.stand = stand;
        this.puerta = puerta;
        this.id = id;
    }


}
