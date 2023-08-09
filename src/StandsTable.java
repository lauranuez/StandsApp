import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.ArrayList;

public class StandsTable {
    private static ArrayList<Flight> flightListWeek = new ArrayList<>();
    private static String[] rowNames = {"HOLD1", "HOLD2", "HOLD3", "214", "217", "221", "245", "247", "250", "270","273", "277", "285", "291", "295", "287", "281", "340", "138A", "137", "136"};
    private static ArrayList<LocalTime> columnNames;
    private static JTable table;
    private static JFrame frame;
    private static JTextField standText;
    private static JTextField nameText;
    private static JComboBox<LocalTime> startTimeComboBox;
    private static JComboBox<LocalTime> endTimeComboBox;
    private static String[] days = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo"};
    private static ArrayList<Flight> flightListDayWeek = new ArrayList<>();
    JTabbedPane tabbedPane = new JTabbedPane();

    public StandsTable() {
        configureTabs();
    }

    public void prepareTable(ArrayList<Flight> flights){
        this.flightListWeek = flights;

        if (flights.size() > 0){
            int i = 0;
            for (Flight flight : flightListWeek) {
                String flightDayWeek = String.valueOf(flight.dayWeek);

                if (flightDayWeek.equals(days[0])) {
                    flight.stand = rowNames[i];
                    flightListDayWeek.add(flight);
                }
                if (i == 20) {
                    break;
                }
                i++;
            }
            showTable(flightListDayWeek);
        }
    }

    public void configureTabs() {

    }

    public ArrayList<Flight> getFlightsForDay(String day) {
        ArrayList<Flight> flightsForDay = new ArrayList<>();

        for (Flight flight : flightListWeek) {
            if (flight.dayWeek.equals(day)) {
                flightsForDay.add(flight);
            }
        }

        return flightsForDay;
    }

    public void showTable(ArrayList<Flight> flightsDay){
        columnNames = generarVectorHoras();
        StandsTableModel model = new StandsTableModel(flightsDay, columnNames, rowNames);

        table = new JTable(model);
        frame = new JFrame("Tabla");

        table.removeColumn(table.getColumnModel().getColumn(0));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                Flight flight;
                String flightNumber = null;

                if (row >= 0 && col > 0) {
                    Object valueCell = table.getValueAt(row, col);
                    if (valueCell == null) {
                        System.out.println("No hay vuelo en esta celda.");
                    } else {
                        if (valueCell.equals(" ")) {
                            boolean found = false;
                            while (!found) {
                                col = col - 1;
                                valueCell = table.getValueAt(row, col);
                                if (valueCell != null && !valueCell.equals(" ")){
                                    found = true;
                                    flightNumber = valueCell.toString();
                                }
                            }
                        }else{
                            flightNumber = valueCell.toString();
                        }
                        if (flightNumber != null) {
                            flight = getFlightByNumber(flightNumber);
                            if (flight != null) {
                                String newStand = newStandMessage(flight);
                                if (!newStand.equals(flight.stand) && !newStand.equals("")){
                                    newStand(flight, newStand);
                                }
                            }
                        }
                    }
                }
            }
        });
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);

        int tablePreferredWidth = 1800;
        table.setPreferredScrollableViewportSize(new Dimension(tablePreferredWidth, table.getRowHeight() * table.getRowCount()));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cellComponent.setBackground(Color.WHITE);
                Font headerFont = cellComponent.getFont();
                Font newHeaderFont = headerFont.deriveFont(Font.BOLD, 16);
                cellComponent.setFont(newHeaderFont);

                String cellValue = (String) value;
                if (cellValue != null) {
                    cellComponent.setBackground(Color.CYAN);
                }
                return cellComponent;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);

        JTable rowHeader = new JTable(model.getRowCount(), 1);
        rowHeader.getColumnModel().getColumn(0).setPreferredWidth(80);
        rowHeader.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cellComponent.setBackground(Color.LIGHT_GRAY);
                String stand = model.getValueAt(row, 0).toString();
                setText(stand);
                return cellComponent;
            }
        });

        JPanel infoPanel = new JPanel();
        JLabel labelDefinirMantenimiento = new JLabel("Definir mantenimiento:");
        labelDefinirMantenimiento.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(labelDefinirMantenimiento);

        JPanel panelCells = new JPanel(new GridLayout(4, 2));
        panelCells.add(new JLabel("Nombre:"));
        nameText = new JTextField(8);
        panelCells.add(nameText);
        panelCells.add(new JLabel("Hora inicio:"));
        startTimeComboBox = new JComboBox<>(columnNames.toArray(new LocalTime[0]));
        panelCells.add(startTimeComboBox);
        panelCells.add(new JLabel("Hora final:"));
        endTimeComboBox = new JComboBox<>(columnNames.toArray(new LocalTime[0]));
        panelCells.add(endTimeComboBox);
        panelCells.add(new JLabel("Stand:"));
        standText = new JTextField(8);
        panelCells.add(standText);


        JButton saveMTOButton = new JButton("Añadir Mantenimiento");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveMTOButton);
        saveMTOButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveMTO();
            }
        });
        JPanel elementsPanel = new JPanel(new BorderLayout());
        elementsPanel.setLayout(new BoxLayout(elementsPanel, BoxLayout.Y_AXIS)); // Alineación vertical

        elementsPanel.add(infoPanel, BorderLayout.NORTH);
        elementsPanel.add(panelCells, BorderLayout.CENTER);
        elementsPanel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPaneWithRowHeaders scrollPaneRow = new JScrollPaneWithRowHeaders(table, rowHeader);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneRow, elementsPanel);

        frame.add(splitPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void saveMTO(){
        LocalTime selectedStartTime = (LocalTime) startTimeComboBox.getSelectedItem();
        LocalTime selectedEndTime = (LocalTime) endTimeComboBox.getSelectedItem();
        System.out.println("name: " + nameText.getText() + " starTime: " + selectedStartTime + " endTime: " + selectedEndTime + " stand: " + standText.getText());
    }

    public static String newStandMessage(Flight flight){
        /*String newStand = (String) JOptionPane.showInputDialog(
             frame,
            "Indicativo del vuelo: " + flight.getFlightNumber() + " hora llegada: " + flight.getArrivalTime() + " hora salida: " + flight.getDepartureTime(),
            "Cambio de Stand",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            flight.getStand()
        );*/

        InputDialog dialog = new InputDialog(frame, flight);
        dialog.setVisible(true);

        String door = dialog.getTextDoorValue();
        String stand = dialog.getTextStandValue();

        System.out.println("Valor 1: " + door);
        System.out.println("Valor 2: " + stand);

        return stand;
    }

    public static String newStandCollision(String collision, String newStand){
        String newStandCollision = (String) JOptionPane.showInputDialog(
                frame,
                collision,
                "Solape en stand: " + newStand,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "HOLD1"
        );
        return newStandCollision;
    }

    public static void newStand(Flight flight, String newStand){
        Boolean standSearchBool = false;
        if (newStand != null) {
            for (String standSearch : rowNames){
                if(standSearch.equals(newStand)){
                    standSearchBool = true;
                    break;
                }
            }
            if (standSearchBool)
            {
                searchCollision(newStand, flight);
            }
            else{
                JOptionPane.showMessageDialog(null, "No existe ese stand",
                        "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public static ArrayList<LocalTime> generarVectorHoras() {
        ArrayList<LocalTime> horas = new ArrayList<>();
        LocalTime horaInicial = LocalTime.of(0, 0); // 00:00
        LocalTime horaFinal = LocalTime.of(23, 55); // 23:55

        int intervaloMin = 5;
        int totalHoras = (horaFinal.toSecondOfDay() - horaInicial.toSecondOfDay()) / (intervaloMin * 60);

        for (int i = 0; i <= totalHoras; i++) {
            horas.add(horaInicial.plusMinutes((long) i * intervaloMin));
        }
        return horas;
    }

    private static Flight getFlightByNumber(String flightNumber) {
        for (Flight flight : flightListWeek) {
            if (flight.id.equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    private static void searchCollision(String newStand, Flight flight){
        ArrayList<Flight> flightsCollision = new ArrayList<>();

        for (Flight flightFor : flightListWeek) {
            if (flightFor.stand.equals(newStand)) {
                if (flight.timeA.isAfter(flightFor.timeA)){
                    if (flight.timeA.isBefore(flightFor.timeD)) {
                        if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                            flightsCollision.add(flightFor);
                        }
                    }
                }
                else if (flight.timeA.isBefore(flightFor.timeA)){
                    if (flight.timeD.isAfter(flightFor.timeA)) {
                        if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                            flightsCollision.add(flightFor);
                        }
                    }
                }
                else{
                    if (flight.timeD.isAfter(flightFor.timeD) || flight.timeD.equals(flightFor.timeD) || flight.timeD.isBefore(flightFor.timeD)) {
                        flightsCollision.add(flightFor);
                    }
                }
            }
        }
        if (flightsCollision.size() == 0){
            flight.stand = newStand;
            table.repaint();
        }
        else{
            String collision = "Vuelos con los que se solapa: ";
            System.out.println(flightsCollision.size());
            for(Flight fl:flightsCollision){
                System.out.println(fl.id);
                collision = collision + " " + fl.id;
            }
            System.out.println(collision);
            String newStandCollision = newStandCollision(collision, newStand);
            newStand(flight, newStandCollision);
        }
    }
}
