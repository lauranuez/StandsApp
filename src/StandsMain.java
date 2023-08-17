import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private static JTextField nameText;
    private static JComboBox<LocalTime> startTimeComboBox;
    private static JComboBox<LocalTime> endTimeComboBox;
    private static JTabbedPane daysTP;
    private static String numWeek;

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
                if (!cellValue.equals(null)) {
                    if (cellValue.equals("  ")){
                        cellComponent.setBackground(Color.green);
                    }
                    else if(cellValue.equals(" ")){
                        cellComponent.setBackground(Color.cyan);
                    }
                    else{
                        if (cellValue.startsWith("MTO")){
                            cellComponent.setBackground(Color.green);
                        } else{
                            cellComponent.setBackground(Color.cyan);
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
                    flight = Utils.getFlightByNumber(flightNumber,flightListWeek);
                    if (flight != null) {
                        System.out.println("Stand al hacer click" + flight.stand);
                        String newStand = newStandMessage(flight);
                        if (!newStand.equals(flight.stand) && !newStand.equals("")){
                            newStand(flight, newStand, table);
                        }
                    }
                }
            }
        }
    }

    //Crea las pestañas de cada dia y añade a cada una su tabla
    public JTabbedPane  setDaysTPanel() {
        JTabbedPane daysTP = new JTabbedPane();

        for (String day : days){
            ArrayList<Flight> flightsDay  = Utils.getFlightsDayStands(day, flightListWeek, rowNames);
            JPanel panelDays = new JPanel();
            JScrollPaneWithRowHeaders scrollPaneRow = setTable(flightsDay);
            panelDays.add(scrollPaneRow);
            daysTP.addTab(day, panelDays);
        }
        return daysTP;
    }

    //Busca si existe el nuevo stand que se asigna al vuelo, si existe, busca colision
    public static void newStand(Flight flight, String newStand, JTable table){
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
                System.out.println("newStand antes de llamar a searchCollision" + flight.stand);
                searchCollision(newStand, flight, table);
            }
            else{
                JOptionPane.showMessageDialog(null, "No existe ese stand",
                        "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //Abre la ventana para elgir el nuevo stand
    public static String newStandMessage(Flight flight){
        InputDialog dialog = new InputDialog(frame, flight);
        dialog.setVisible(true);

        String door = dialog.getTextDoorValue();
        String stand = dialog.getTextStandValue();

        System.out.println("Valor 1: " + door);
        System.out.println("Valor 2: " + stand);

        return stand;
    }

    //Busca si el stand esta ocupado por otro vuelo
    private static void searchCollision(String newStand, Flight flight, JTable table){
        int currentTabIndex = daysTP.getSelectedIndex();
        String day = daysTP.getTitleAt(currentTabIndex);

        ArrayList<Flight> flightsDay  = Utils.getFlightsDay(day, flightListWeek, rowNames);

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
            newStand(flight, newStandCollision, table);
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

    //Metodo para guardar el mantenimiento establecido
    public static void saveMTO(){
        LocalTime selectedStartTime = (LocalTime) startTimeComboBox.getSelectedItem();
        LocalTime selectedEndTime = (LocalTime) endTimeComboBox.getSelectedItem();
        int currentTabIndex = daysTP.getSelectedIndex();
        String day = daysTP.getTitleAt(currentTabIndex);

        DayOfWeek dayOfWeek = Utils.dayOfWeekWithDayName(day);

        if(dayOfWeek!=null) {
            LocalDate date = LocalDate.now()
                    .with(TemporalAdjusters.firstDayOfYear())
                    .plusWeeks(Integer.parseInt(numWeek) - 1);

            if (dayOfWeek == DayOfWeek.SUNDAY) {
                date = date.plusWeeks(1); // Agregar una semana si es domingo
            }

            date = date.with(TemporalAdjusters.nextOrSame(dayOfWeek));

            Flight MTO = new Flight(date, "-", "terminal", 0, "-", "-", "-", selectedStartTime, "-", "-", "-", "-",date, "-", "-",
                    selectedEndTime, "-", "-", "-", "-", "-",0, Integer.parseInt(numWeek), day, standText.getText(), "-", nameText.getText(), "M");
            System.out.println("name: " + nameText.getText() + " starTime: " + selectedStartTime + " endTime: " + selectedEndTime + " stand: " + standText.getText() + " en la pestaña: " + daysTP.getTitleAt(currentTabIndex));

            ArrayList<Flight> flightsDay  = Utils.getFlightsDay(day, flightListWeek, rowNames);
            ArrayList<Flight> flightsCollision = standsUtils.flightsCollision(flightsDay, standText.getText(), MTO);

            System.out.println("Mto collision: " + flightsCollision.size());
            if (flightsCollision.isEmpty()){
                flightListWeek.add(MTO);
                updateTable(day);
                System.out.println(flightListWeek.size());
                Flight MTOadded = flightListWeek.get(flightListWeek.size() - 1);
                System.out.println("dateA: " + MTOadded.dateA + " timeA: " + MTOadded.timeA + " dateD: " + MTOadded.dateD + " timeD: " + MTOadded.timeD + " numWeek: " + MTOadded.numWeek + " day: " + MTOadded.dayWeek + " stand: " + MTOadded.stand + "id: " + MTOadded.id);
                Flight MTOadded2 = flightListWeek.get(flightListWeek.size() - 2);
                System.out.println("dateA: " + MTOadded2.dateA + "timeA: " + MTOadded2.timeA + "dateD: " + MTOadded2.dateD + "timeD: " + MTOadded2.timeD + "numWeek: " + MTOadded2.numWeek + "day: " + MTOadded2.dayWeek + "stand: " + MTOadded2.stand + "id: " + MTOadded2.id);
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
    }

    public static void updateTable(String day) {
        int tabIndex = Arrays.asList(days).indexOf(day);
        ArrayList<Flight> flightsDay = Utils.getFlightsDay(day, flightListWeek, rowNames);
        JScrollPaneWithRowHeaders scrollPaneRow = setTable(flightsDay);
        JPanel panelDays = new JPanel();
        panelDays.add(scrollPaneRow);
        daysTP.setComponentAt(tabIndex, panelDays);
        //tabIndex.repaint();
        //daysTP.getComponentAt(tabIndex).repaint();
    }

}
