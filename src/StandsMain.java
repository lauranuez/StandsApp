import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;

public class StandsMain extends JFrame {
    private static String[] days = {"lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo"};
    private static JFrame frame;
    private static String[] rowNames = {"HOLD1", "HOLD2", "HOLD3", "214", "217", "221", "245", "247", "250", "270","273", "277", "285", "291", "295", "287", "281", "340", "138A", "137", "136"};
    private static ArrayList<Flight> flightListWeek = new ArrayList<>();
    private static ArrayList<LocalTime> columnNames;
    private static JTextField standText;
    private static JTextField puertaText;
    private static JTextField nameText;
    private static JComboBox<LocalTime> startTimeComboBox;
    private static JComboBox<LocalTime> endTimeComboBox;
    private static JTabbedPane daysTP;
    private static String numWeek;
    private static ArrayList<String> daysPernocta;


    public StandsMain(ArrayList<Flight> flightListWeek, String numWeekSelected) {
        this.flightListWeek = flightListWeek;
        this.numWeek = numWeekSelected;

        frame = new JFrame("Asignación stands");

        columnNames = Utils.generarVectorHoras();

        daysTP = setDaysTPanel();

        JPanel maintenancePanel = setMTOLayout();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(maintenancePanel, BorderLayout.CENTER);
        mainPanel.add(daysTP, BorderLayout.NORTH);

        frame.getContentPane().add(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void removeFlight(Flight flight, String day){
        flightListWeek.remove(flight);
        updateTable(day);
    }

    public static void setDaysCollision(ArrayList<String> days){
        daysPernocta = days;
    }

    //Crea la tabla y le añade el clickListener
    public static JScrollPaneWithRowHeaders setTable(ArrayList<Flight> flightsDay){
        StandsTableModel model = new StandsTableModel(flightsDay, columnNames, rowNames);

        JTable table = new JTable(model);

        table.removeColumn(table.getColumnModel().getColumn(0));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked on table");
                mouseClickedTable(e, table);
            }
        });

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);

        int tablePreferredWidth = 1800;
        table.setPreferredScrollableViewportSize(new Dimension(tablePreferredWidth, table.getRowHeight() * table.getRowCount()));

        setCellRender(table);

        JTable rowHeader = setRowHeader(model);

        JScrollPaneWithRowHeaders scrollPaneRow = new JScrollPaneWithRowHeaders(table, rowHeader);

        return scrollPaneRow;
    }

    //Crea el Layout para añadir un mantenimiento
    public static JPanel setMTOLayout(){
        JPanel infoPanel = new JPanel();
        JLabel labelDefinirMantenimiento = new JLabel("Definir mantenimiento:");
        labelDefinirMantenimiento.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(labelDefinirMantenimiento);

        JPanel panelCells = new JPanel(new GridLayout(5, 2));
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
        panelCells.add(new JLabel("Puerta:"));
        puertaText = new JTextField(8);
        panelCells.add(puertaText);

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

        return elementsPanel;
    }

    //Crea la columna estatica del nombre de las filas
    public static JTable setRowHeader(StandsTableModel model){
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
        return rowHeader;
    }

    //Establece el diseño de las celdas
    public static void setCellRender(JTable table) {
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cellComponent.setBackground(Color.WHITE);
                Font headerFont = cellComponent.getFont();
                Font newHeaderFont = headerFont.deriveFont(Font.BOLD, 16);
                cellComponent.setFont(newHeaderFont);
                String cellValue = (String) value;
                try{
                if (cellValue != null) {
                    if (cellValue.equals("  ")){
                        cellComponent.setBackground(Color.green);
                    }
                    else if(cellValue.equals(" ")){
                        cellComponent.setBackground(Color.cyan);
                    }
                    else if(cellValue.equals("   ")){
                        cellComponent.setBackground(Color.orange);
                    }
                    else if(cellValue.equals("    ")){
                        cellComponent.setBackground(Color.magenta);
                    }
                    else{
                        String id = cellValue;
                        Flight flight = Utils.getFlightByNumber(id, flightListWeek);
                        if (flight.carreteo.equals("N")) {
                            if (flight.type.equals("M")) {
                                cellComponent.setBackground(Color.green);
                            } else if (flight.type.equals("P")) {
                                cellComponent.setBackground(Color.orange);
                            } else {
                                cellComponent.setBackground(Color.cyan);
                            }
                        }
                        else{
                            cellComponent.setBackground(Color.magenta);
                        }
                    }
                }}
                catch(NullPointerException e){

                }
                return cellComponent;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);
    }

    //Metodo para controlar el click de la tabla
    public static void mouseClickedTable(MouseEvent e, JTable table){
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        Flight flight;
        String flightNumber = null;
        String valueCol = null;

        if (row >= 0 && col > 0) {
            Object valueCell = table.getValueAt(row, col);
            if (valueCell == null) {
                System.out.println("No hay vuelo en esta celda.");
            } else {
                if (valueCell.equals(" ") || valueCell.equals("  ") || valueCell.equals("   ") || valueCell.equals("    ")) {
                    boolean found = false;
                    while (!found) {
                        col = col - 1;
                        valueCell = table.getValueAt(row, col);
                        if (valueCell != null && !valueCell.equals(" ") && !valueCell.equals("  ") && !valueCell.equals("   ") && !valueCell.equals("    ")){
                            found = true;
                            flightNumber = valueCell.toString();
                            valueCol = table.getColumnName(col);
                        }
                    }
                }else{
                    flightNumber = valueCell.toString();
                    valueCol = table.getColumnName(col);
                }
                if (flightNumber != null) {
                    flight = Utils.getFlightByNumberTable(flightNumber, flightListWeek,valueCol);
                    int currentTabIndex = daysTP.getSelectedIndex();
                    String day = daysTP.getTitleAt(currentTabIndex);
                    if (flight != null) {
                        if (flight.type == "F") {
                            //Vuelo
                            System.out.println("Stand al hacer click" + flight.stand);
                            String newStand = newStandMessage(flight,day);
                            if (!newStand.equals(flight.stand) && !newStand.equals("") && !newStand.equals(flight.stand)) {
                                newStand(flight, newStand, table);
                            }
                        } else if (flight.type == "M") {
                            //Mantenimiento
                            System.out.println("ClickMTO" + flight.stand);
                            newMTOMessage(flight, table);
                        } else if (flight.type == "P") {
                            //Pernocta
                            ArrayList<Flight> listDay = Utils.getFlightsDay(day, numWeek, flightListWeek);
                            Flight flightInDay = Utils.getFlightByNumberTable(flight.id, listDay, valueCol);
                            if (flightInDay.type.equals("PAD")){
                                System.out.println("Stand al hacer click" + flight.stand);
                                String newStand = newStandMessage(flight, day);
                                if (!newStand.equals(flight.stand) && !newStand.equals("")) {
                                    newStand(flight, newStand, table);
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(
                                        frame,
                                        "No se puede mover esta pernocta",
                                        "PERNOCTA",
                                        JOptionPane.WARNING_MESSAGE,
                                        null
                                );
                            }
                        }
                    }
                }
            }
        }
    }

    //Crea las pestañas de cada dia y añade a cada una su tabla
    public JTabbedPane setDaysTPanel() {
        JTabbedPane daysTP = new JTabbedPane();

        for (String day : days){
            ArrayList<Flight> flightsDay  = Utils.getFlightsDayStands(day, rowNames, numWeek, flightListWeek);
            JPanel panelDays = new JPanel();
            JScrollPaneWithRowHeaders scrollPaneRow = setTable(flightsDay);
            panelDays.add(scrollPaneRow);
            daysTP.addTab(day, panelDays);
        }
        return daysTP;
    }

    //Busca si existe el nuevo stand que se asigna al vuelo, si existe, busca colision
    public static void newStand(Flight flight, String newStand, JTable table){
        if (newStand != null) {
            Boolean standSearchBool = Utils.standSearch(rowNames, newStand);
            if (standSearchBool)
            {
                System.out.println("newStand antes de llamar a searchCollision" + flight.stand);
                if (flight.type == "P") {
                    searchCollisionPernocta(newStand, flight, table);
                } else {
                    searchCollision(newStand, flight, table);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No existe ese stand",
                        "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //Abre la ventana para elgir el nuevo stand
    public static String newStandMessage(Flight flight , String day) {
        InputDialog dialog = new InputDialog(frame, flight, day);
        dialog.setVisible(true);

        String door = dialog.getTextDoorValue();
        String stand = dialog.getTextStandValue();

        System.out.println("Valor 1: " + door);
        System.out.println("Valor 2: " + stand);

        return stand;
    }

    private static void newMTOMessage(Flight flight, JTable table){
        InputDialogMTO dialog = new InputDialogMTO(frame, flight, columnNames);
        dialog.setVisible(true);

        String name = dialog.getIdMTO(); //""
        String door = dialog.getTextDoorValue();
        String stand = dialog.getTextStandValue();
        LocalTime selectedEndTime = dialog.getEndTimeComboBox();
        LocalTime selectedStartTime = dialog.getStartTimeComboBox();

        if (selectedStartTime.isBefore(selectedEndTime)){
            changeMTO(door, stand, name, selectedStartTime, selectedEndTime, flight, table);
        }else{
            JOptionPane.showMessageDialog(null, "La hora de salida es anterior a la de llegada",
                    "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void changeMTO(String door, String stand, String name, LocalTime startTime, LocalTime endTime, Flight flight, JTable table){
        int currentTabIndex = daysTP.getSelectedIndex();
        String day = daysTP.getTitleAt(currentTabIndex);

        ArrayList<Flight> flightsDay  = Utils.getFlightsDay(day, numWeek, flightListWeek);

        if (stand != null && !stand.equals("")) {
            Boolean standSearchBool = Utils.standSearch(rowNames, stand);
            if (standSearchBool)
            {
                if(!stand.equals(flight.stand)){
                    flight.timeA = startTime;
                    flight.timeD = endTime;
                    ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flightsDay, stand, flight);
                    if (flightsCollision.size() == 0){
                        flight.setStand(stand);
                        table.repaint();
                    }
                    else if (flightsCollision.size() == 1) {
                        if (flight.id.equals(flightsCollision.get(0).id)){
                            flight.setStand(stand);
                            table.repaint();
                        }
                        else{
                            String collision = "Vuelos con los que se solapa: ";
                            System.out.println(flightsCollision.size());
                            for (Flight fl : flightsCollision) {
                                System.out.println(fl.id);
                                collision = collision + " " + fl.id;
                            }
                            System.out.println(collision);
                            String newStandCollision = newStandCollision(collision, stand);
                            if (newStandCollision!=null) {
                                newStand(flight, newStandCollision, table);
                            }
                        }
                    }
                    else{
                        String collision = "Vuelos con los que se solapa: ";
                        System.out.println(flightsCollision.size());
                        for (Flight fl : flightsCollision) {
                            System.out.println(fl.id);
                            collision = collision + " " + fl.id;
                        }
                        System.out.println(collision);
                        String newStandCollision = newStandCollision(collision, stand);
                        if (newStandCollision!=null) {
                            newStand(flight, newStandCollision, table);
                        }
                    }
                }
                else{ //Cambiar hora, pero mismo stand y ver si coincide con alguno
                    flight.timeA = startTime;
                    flight.timeD = endTime;
                    ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flightsDay, stand, flight);
                    if (flightsCollision.size() == 0){
                        flight.setStand(stand);
                        table.repaint();
                    }else if (flightsCollision.size() == 1) {
                        if (flight.id.equals(flightsCollision.get(0).id)){
                            flight.setStand(stand);
                            table.repaint();
                        }
                    }
                    else{
                        String collision = "Vuelos con los que se solapa: ";
                        System.out.println(flightsCollision.size());
                        for (Flight fl : flightsCollision) {
                            System.out.println(fl.id);
                            collision = collision + " " + fl.id;
                        }
                        System.out.println(collision);
                        String newStandCollision = newStandCollision(collision, stand);
                        if (newStandCollision!=null) {
                            newStand(flight, newStandCollision, table);
                        }
                    }
                }
                if(!name.equals("")) {
                    if (!name.equals(flight.id)) {
                        flight.id = name;
                        table.repaint();
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No existe ese stand",
                        "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //Busca si el stand esta ocupado por otro vuelo
    private static void searchCollision(String newStand, Flight flight, JTable table){
        int currentTabIndex = daysTP.getSelectedIndex();
        String day = daysTP.getTitleAt(currentTabIndex);

        ArrayList<Flight> flightsDay  = Utils.getFlightsDay(day, numWeek, flightListWeek);

        ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flightsDay, newStand, flight);
        if (flightsCollision.size() == 0){
            flight.setStand(newStand);
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
            if(newStandCollision != null){
                newStand(flight, newStandCollision, table);
            }
        }
    }

    private static void searchCollisionPernocta(String newStand, Flight flight, JTable table){
        int currentTabIndex = daysTP.getSelectedIndex();
        String day = daysTP.getTitleAt(currentTabIndex);

        //ArrayList<Flight> flightsDay  = Utils.getFlightsDay(day, numWeek, flightListWeek);

        ArrayList<Flight> flightsCollision = standsUtils.flightsCollisionPernocta(flightListWeek, newStand, flight, day, numWeek);
        if (flightsCollision.size() == 0){
            flight.setStand(newStand);
            //table.repaint();
            for(String daysPer : daysPernocta){
                updateTable(daysPer);
            }
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
            if(newStandCollision != null){
                newStand(flight, newStandCollision, table);
            }
        }
    }

    //Dialogo cuando hay una colision entre vuelos en el mismo stand
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

    public static String standCarreteo(Flight flight) {
        String newStandCarreteo = (String) JOptionPane.showInputDialog(
                frame,
                null,
                "Stand carreteo: " + flight.id,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                flight.stand
        );
        return newStandCarreteo;
    }
    public static void searchCollisionCarreteo( Flight flight , String day, String newStandCarreteo){
        if (newStandCarreteo != null) {
            if (!newStandCarreteo.equals(flight.stand)) {
                ArrayList<Flight> flightsDay = Utils.getFlightsDay(day, numWeek, flightListWeek);
                LocalTime departureTime = flight.timeD;
                LocalTime carreteoTime = departureTime.minusHours(2).minusMinutes(30);
                LocalTime stopTime = carreteoTime.minusMinutes(2);
                Flight f1 = new Flight(flight.dateA, flight.AH, flight.terminal, flight.pernocta, flight.aircraftA, flight.airlineA, flight.numA, flight.timeA, flight.origenAirport, flight.AA, flight.flightTypeA, flight.zonaL, flight.dateD, flight.airlineD, flight.numD,
                        stopTime, flight.as, flight.af, flight.flightTypeD, flight.zonaS, flight.aircraftD, flight.seats, flight.numWeek, flight.dayWeek, flight.stand, null, flight.puerta, flight.id, flight.type, "Y");
                Flight f2 = new Flight(flight.dateA, flight.AH, flight.terminal, flight.pernocta, flight.aircraftA, flight.airlineA, flight.numA, carreteoTime, flight.origenAirport, flight.AA, flight.flightTypeA, flight.zonaL, flight.dateD, flight.airlineD, flight.numD,
                        flight.timeD, flight.as, flight.af, flight.flightTypeD, flight.zonaS, flight.aircraftD, flight.seats, flight.numWeek, flight.dayWeek, newStandCarreteo, null, flight.puerta, flight.id, flight.type, "Y");
                ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flightsDay, newStandCarreteo, f2);
                if (flightsCollision.size() == 0) {
                    flight.stand2 = newStandCarreteo;
                    flight.carreteo = "Y";
                    flightListWeek.add(f1);
                    flightListWeek.add(f2);
                    updateTable(day);
                } else {
                    String collision = "Vuelos con los que se solapa: ";
                    for (Flight fl : flightsCollision) {
                        collision = collision + " " + fl.id;
                    }
                    carreteo(flight, day);
                }

            }
        }
    }

    public static void carreteo(Flight flight, String day){
        String newStandCarreteo = standCarreteo(flight);
        if (newStandCarreteo != null) {
            Boolean standSearchBool = Utils.standSearch(rowNames, newStandCarreteo);
            if (standSearchBool)
            {
               searchCollisionCarreteo(flight, day, newStandCarreteo);
            }
            else{
                JOptionPane.showMessageDialog(null, "No existe ese stand",
                        "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //Metodo para guardar el mantenimiento establecido
    public static void saveMTO(){
        Boolean standExist = Utils.standSearch(rowNames,standText.getText());
        if(standExist){
            LocalTime selectedStartTime = (LocalTime) startTimeComboBox.getSelectedItem();
            LocalTime selectedEndTime = (LocalTime) endTimeComboBox.getSelectedItem();

            if (selectedStartTime.isBefore(selectedEndTime)){
                int currentTabIndex = daysTP.getSelectedIndex();
                String day = daysTP.getTitleAt(currentTabIndex);
                LocalDate date = Utils.getLocalDateWithDayName(day,numWeek);
                if (date != null){
                    Flight MTO = new Flight(date, "-", "terminal", 0, "-", "-", "-", selectedStartTime, "-", "-", "-", "-",date, "-", "-",
                            selectedEndTime, "-", "-", "-", "-", "-",0, Integer.parseInt(numWeek), day, standText.getText(), null, puertaText.getText(), nameText.getText(), "M", "N");
                    System.out.println("name: " + nameText.getText() + " starTime: " + selectedStartTime + " endTime: " + selectedEndTime + " stand: " + standText.getText() + " en la pestaña: " + daysTP.getTitleAt(currentTabIndex));

                    ArrayList<Flight> flightsDay  = Utils.getFlightsDay(day, numWeek, flightListWeek);
                    ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flightsDay, standText.getText(), MTO);

                    System.out.println("Mto collision: " + flightsCollision.size());
                    if (flightsCollision.isEmpty()){
                        flightListWeek.add(MTO);
                        updateTable(day);
                    }
                    else{
                        JOptionPane.showMessageDialog(
                                frame,
                                "El stand " + standText.getText() + "esta ocupado",
                                standText.getText(),
                                JOptionPane.WARNING_MESSAGE,
                                null
                        );
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "La hora de salida es anterior a la de llegada",
                        "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "No existe ese stand",
                    "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void updateTable(String day) {
        int tabIndex = Arrays.asList(days).indexOf(day);
        ArrayList<Flight> flightsDay = Utils.getFlightsDay(day, numWeek, flightListWeek);
        JScrollPaneWithRowHeaders scrollPaneRow = setTable(flightsDay);
        JPanel panelDays = new JPanel();
        panelDays.add(scrollPaneRow);
        daysTP.setComponentAt(tabIndex, panelDays);
        //tabIndex.repaint();
        //daysTP.getComponentAt(tabIndex).repaint();
    }
}
