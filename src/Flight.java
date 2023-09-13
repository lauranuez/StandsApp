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
    String zonaL;
    LocalDate dateD;
    String airlineD;
    String numD;
    LocalTime timeD;
    String as;
    String af;
    String flightTypeD;
    String zonaS;
    String aircraftD;
    int seats;
    int numWeek;
    String dayWeek;
    String stand;
    String stand2;
    String puerta;
    String id;
    String type;
    String carreteo;
    String id2;
    Integer rowExcel;

    public Flight(LocalDate arrivalDate, String AH, String terminal, int pernocta, String aircraftA, String airlineA, String numA,
                  LocalTime timeA, String origenAirport, String AA, String flightTypeA, String zonaLA, LocalDate dateD, String airlineD, String numD,
                  LocalTime timeD, String as, String af, String flightTypeD, String zonaSA, String aircraftD, int seats, int week, String day, String stand, String stand2, String puerta, String id, String type, String carreteo, String id2, Integer rowExcel){

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
        this.zonaL = zonaLA;
        this.dateD = dateD;
        this.airlineD = airlineD;
        this.numD = numD;
        this.timeD = timeD;
        this.as = as;
        this.af = af;
        this.flightTypeD = flightTypeD;
        this.zonaS = zonaSA;
        this.aircraftD = aircraftD;
        this.seats = seats;
        this.numWeek = week;
        this.dayWeek = day;
        this.stand = stand;
        this.stand2 = stand2;
        this.puerta = puerta;
        this.id = id;
        this.type = type;
        this.carreteo = carreteo;
        this.id2 = id2;
        this.rowExcel = rowExcel;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public LocalTime getTimeA() {
        return timeA;
    }
}
