import javax.swing.table.AbstractTableModel;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StandsTableModel extends AbstractTableModel {
    private ArrayList<Flight> flights;
    private List<LocalTime> columnNames;
    private String[] rowNames;

    public StandsTableModel(ArrayList<Flight> flights, List<LocalTime> columnNames, String[] rowNames) {
        this.flights = flights;
        this.columnNames = columnNames;
        this.rowNames = rowNames;
    }

    @Override
    public int getRowCount() {
        return rowNames.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String stand = rowNames[rowIndex];
        if (columnIndex == 0) {
            return stand;
        } else {
            LocalTime time = columnNames.get(columnIndex - 1);
            for (Flight flight : flights) {
                if (flight.stand == null) {
                    continue;
                }else{
                    try {
                        if (flight.type.equals("F")) {
                            if (flight.stand.equals(stand) && time.equals(flight.timeA)) {
                                return flight.id;
                            } else if (flight.stand.equals(stand) && time.isAfter(flight.timeA) && time.isBefore(flight.timeD)) {
                                return " ";
                            } else if (flight.stand.equals(stand) && time.equals(flight.timeD)) {
                                return " ";
                            }
                        } else {
                            if (flight.stand.equals(stand) && time.equals(flight.timeA)) {
                                return flight.id;
                            } else if (flight.stand.equals(stand) && time.isAfter(flight.timeA) && time.isBefore(flight.timeD)) {
                                return "  ";
                            } else if (flight.stand.equals(stand) && time.equals(flight.timeD)) {
                                return "  ";
                            }
                        }
                    }catch (NullPointerException e){
                        return null;
                    }
                }
            }
            return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return "Stands";
        } else {
            return String.valueOf(columnNames.get(columnIndex - 1));
        }
    }

    public void updateFlightStand(String flightNumber, String newStand) {
        for (Flight flight : flights) {
            if (flight.id.equals(flightNumber)) {
                flight.stand = newStand;
                break;
            }
        }
        fireTableDataChanged();
    }

    public String[] getRowNames() {
        return rowNames;
    }
}
