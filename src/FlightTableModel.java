import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class FlightTableModel extends AbstractTableModel {
    private ArrayList<Flight> flights;
    private String[] columnNames = {"Num Week", "Day Week", "Fecha L", "Hora L", "Terminal", "Pernocta", "AH", "Cía L", "Num L", "AO", "AA", "Clase L", "Zona L", "Avión L", "Fecha S", "Hora S", "Cía S", "Num S","AS", "AF", "Clase S", "Zona S", "Avion S", "Asientos"};

    public FlightTableModel(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public int getRowCount() {
        return flights.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flight flight = flights.get(rowIndex);
        switch (columnIndex) {
            case 0: return flight.numWeek;
            case 1: return flight.dayWeek;
            case 2: return flight.dateA;
            case 3: return flight.timeA;
            case 4: return flight.terminal;
            case 5: return flight.pernocta;
            case 6: return flight.AH;
            case 7: return flight.airlineA;
            case 8: return flight.numA;
            case 9: return flight.origenAirport;
            case 10: return flight.AA;
            case 11: return flight.flightTypeA;
            case 12: return flight.zonaL;
            case 13: return flight.aircraftA;
            case 14: return flight.dateD;
            case 15: return flight.timeD;
            case 16: return flight.airlineD;
            case 17: return flight.numD;
            case 18: return flight.as;
            case 19: return flight.af;
            case 20: return flight.flightTypeD;
            case 21: return flight.zonaS;
            case 22: return flight.aircraftD;
            case 23: return flight.seats;
            default: return null;
        }
    }
    public void setFlightList(ArrayList<Flight> flightList) {
        this.flights = flightList;
    }
}
