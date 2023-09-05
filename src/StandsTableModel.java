import javax.swing.table.AbstractTableModel;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StandsTableModel extends AbstractTableModel {
    private ArrayList<Flight> flights;
    private List<LocalTime> columnNames;
    //private String[] rowNames;
    private ArrayList<Stand> rowNames;

    public StandsTableModel(ArrayList<Flight> flights, List<LocalTime> columnNames, ArrayList<Stand> rowNames) {
        this.flights = flights;
        this.columnNames = columnNames;
        this.rowNames = rowNames;
    }

    @Override
    public int getRowCount() {
        return rowNames.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String stand = rowNames.get(rowIndex).numStand;
        if (columnIndex == 0) {
            return stand;
        } /*else if (columnIndex != 0 && stand.equals("  ")) {
            return "     "; //5
        }*/
        else {
            LocalTime time = columnNames.get(columnIndex - 1);
            for (Flight flight : flights) {
                if (flight.stand == null) {
                    continue;
                }else{
                    try {
                        if (flight.carreteo.equals("N")) {
                            if (flight.type.equals("F")) {
                                if (flight.stand.equals(stand) && time.equals(flight.timeA)) {
                                    return flight.id;
                                } else if (flight.stand.equals(stand) && time.isAfter(flight.timeA) && time.isBefore(flight.timeD)) {
                                    return " "; //1
                                } else if (flight.stand.equals(stand) && time.equals(flight.timeD)) {
                                    return " "; //1
                                }
                            } else if (flight.type.equals("PL") || flight.type.equals("PDD") || flight.type.equals("PAD") || flight.type.equals("P")) {
                                if (flight.stand.equals(stand) && time.equals(flight.timeA)) {
                                    return flight.id;
                                } else if (flight.stand.equals(stand) && time.isAfter(flight.timeA) && time.isBefore(flight.timeD)) {
                                    return "   "; //3
                                } else if (flight.stand.equals(stand) && time.equals(flight.timeD)) {
                                    return "   "; //3
                                }
                            } else {
                                if (flight.stand.equals(stand) && time.equals(flight.timeA)) {
                                    return flight.id;
                                } else if (flight.stand.equals(stand) && time.isAfter(flight.timeA) && time.isBefore(flight.timeD)) {
                                    return "  "; //2
                                } else if (flight.stand.equals(stand) && time.equals(flight.timeD)) {
                                    return "  "; //2
                                }
                            }
                        }
                        else{
                            if (flight.stand.equals(stand) && time.equals(flight.timeA)) {
                                return flight.id;
                            } else if (flight.stand.equals(stand) && time.isAfter(flight.timeA) && time.isBefore(flight.timeD)) {
                                return "    "; //4
                            } else if (flight.stand.equals(stand) && time.equals(flight.timeD)) {
                                return "    "; //4
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
}
