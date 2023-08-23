import java.awt.*;
import javax.swing.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class main extends JFrame {
    private ArrayList<Flight> flightList = new ArrayList<>();
    private ArrayList<Flight> flightListWeek = new ArrayList<>();
    private ArrayList<Flight> flightListDayWeek = new ArrayList<>();
    private ArrayList<FlightLEVEL> flightListARMS = new ArrayList<>();
    private ArrayList<FlightLEVEL> flightListVictor = new ArrayList<>();
    private ArrayList <FlightARMS> flightListProcessorARMS = new ArrayList<>();
    private JTable table;
    private JLabel dataTextLabel;
    private JComboBox<String> weeksComboBox;
    private String selectedWeek;
    private String selectedLEVEL;
    private JLabel rotacionesLabel;
    private JLabel LEVELTextLabel;
    private JButton loadFlightsButton;
    private JButton showDataBtn;
    private JButton buildBtn;
    private String numWeekSelected = null;

    public main(){
        createMenuBar();
        setTitle("WB");
        setSize(800, 900);
        setPreferredSize(new Dimension(800, 900));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        JLabel label = new JLabel("Fichero Datos Geslot");

        loadFlightsButton = new JButton("Cargar");
        dataTextLabel = new JLabel(" ");
        JPanel dataGeslotPanel = new JPanel(new FlowLayout());
        dataGeslotPanel.add(label);
        dataGeslotPanel.add(loadFlightsButton);
        dataGeslotPanel.add(dataTextLabel);

        loadFlightsButton.addActionListener((event) -> loadFlights());
        JLabel weekLabel = new JLabel("Semana a construir");

        weeksComboBox = new JComboBox<>(getWeeksArray());

        weeksComboBox.addActionListener(e -> {
            selectedWeek = (String) weeksComboBox.getSelectedItem();
            if (selectedWeek != " " && selectedWeek != null) {
                int startIndex = selectedWeek.indexOf("Week ") + 5;
                int endIndex = selectedWeek.indexOf(":", startIndex);
                numWeekSelected = selectedWeek.substring(startIndex, endIndex).trim();
                System.out.println("Número de la semana: " + numWeekSelected);
                System.out.println("Semana seleccionada: " + selectedWeek);
            }
        });

        JPanel weeksPanel = new JPanel(new FlowLayout());
        weeksPanel.add(weekLabel);
        weeksPanel.add(weeksComboBox);

        rotacionesLabel = new JLabel("Rotaciones LEVEL");

        JComboBox<String> rotacionesComboBox = new JComboBox<>();
        rotacionesComboBox.addItem("None");
        rotacionesComboBox.addItem("ARMS");
        rotacionesComboBox.addItem("Victor");

        rotacionesComboBox.addActionListener(e -> {
            selectedLEVEL = (String) rotacionesComboBox.getSelectedItem();
            if (selectedLEVEL == "ARMS") {
                ARMS();
            } else if (selectedLEVEL == "Victor") {
                ficheroVictor();
            }
        });

        LEVELTextLabel = new JLabel(" ");

        JPanel panelLEVEL = new JPanel(new FlowLayout());
        panelLEVEL.add(rotacionesLabel);
        panelLEVEL.add(rotacionesComboBox);
        panelLEVEL.add(LEVELTextLabel);

        JLabel aatReferenciaLabel = new JLabel("AAT referencia");
        JComboBox<String> aatComboBox = new JComboBox<>();

        aatComboBox.addItem("Elemento 1");
        aatComboBox.addItem("Elemento 2");

        JPanel aatRefPanel = new JPanel(new FlowLayout());
        aatRefPanel.add(aatReferenciaLabel);
        aatRefPanel.add(aatComboBox);

        JLabel natReferenciaLabel = new JLabel("AAT referencia");
        JComboBox<String> natComboBox = new JComboBox<>();

        natComboBox.addItem("Elemento 1");
        natComboBox.addItem("Elemento 2");

        JPanel natRefPanel = new JPanel(new FlowLayout());
        natRefPanel.add(natReferenciaLabel);
        natRefPanel.add(natComboBox);

        showDataBtn = new JButton("Mostrar datos");

        showDataBtn.addActionListener(e -> {
            showData();
        });

        buildBtn = new JButton("Build");

        buildBtn.addActionListener(e -> {
            build();
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(showDataBtn);
        buttonsPanel.add(buildBtn);

        FlightTableModel tableModel = new FlightTableModel(flightList);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        tableModel.setFlightList(flightList);
        tableModel.fireTableDataChanged();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(dataGeslotPanel, gbc);

        gbc.gridy = 1;
        add(weeksPanel, gbc);

        gbc.gridy = 2;
        add(panelLEVEL, gbc);

        gbc.gridy = 3;
        add(aatRefPanel, gbc);

        gbc.gridy = 4;
        add(natRefPanel, gbc);

        gbc.gridy = 5;
        add(buttonsPanel, gbc);

        gbc.gridy = 6;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableScrollPane, gbc);

        pack();
    }

    private void build() {
        if (numWeekSelected != null && !flightList.isEmpty()) {
            if (flightListWeek.size() > 0){
                for (Flight flight : flightListWeek) {
                    flight.id = flight.airlineA + flight.numA + "/" + flight.numD + " " + flight.origenAirport + "-" + flight.af + " (" + flight.aircraftA + ") " + flight.dateA + " " + flight.terminal;
                    if(flight.pernocta != 0){
                        flight.type = "P";
                    }
                }
                openStandsMain(flightListWeek);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Se debe elegir la semana a construir/añadir datos", "Elegir semana a construir", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void openStandsMain(ArrayList<Flight> flightListWeek) {
        SwingUtilities.invokeLater(() -> {
            StandsMain standsMain = new StandsMain(flightListWeek, numWeekSelected);
        });
    }

    private void showData() {
        if (numWeekSelected != null && flightList != null) {
            asociar();
            FlightTableModel tableModel = (FlightTableModel) table.getModel();
            tableModel.setFlightList(flightListWeek);
            tableModel.fireTableDataChanged();
        }
        else{
            JOptionPane.showMessageDialog(null, "Se debe elegir la semana a construir/añadir datos", "Elegir semana a construir", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void asociar(){
        flightListWeek.removeAll(flightListWeek);
        ArrayList<Flight> flightListJoinStaticFlights = SetNumFlights.associateExcel(flightList);

        if (flightListJoinStaticFlights != null) {
            flightList = flightListJoinStaticFlights;
        } else {
            JOptionPane.showMessageDialog(null, "No se han asociado los vuelos", "Vuelos no asociados", JOptionPane.WARNING_MESSAGE);
        }

        if (selectedLEVEL == "ARMS") {
            ArrayList<Flight> flightListJoinARMS = JoinLEVEL.associateLEVELs(flightList,flightListARMS);
            if (flightListJoinARMS != null) {
                flightList = flightListJoinARMS;
            } else {
                JOptionPane.showMessageDialog(null, "Cargar otro fichero de excel", "Formato no válido", JOptionPane.WARNING_MESSAGE);
            }
        } else if (selectedLEVEL == "Victor") {
            ArrayList<Flight> flightListJoinVictor = JoinLEVEL.associateLEVELs(flightList,flightListVictor);
            if (flightListJoinVictor != null) {
                flightList = flightListJoinVictor;
            } else {
                JOptionPane.showMessageDialog(null, "Cargar otro fichero de excel", "Formato no válido", JOptionPane.WARNING_MESSAGE);
            }
        }

        int numWeekSelectedPrevious = Integer.parseInt(numWeekSelected) - 1;
        for (Flight flight : flightList){
            String flightNumWeek = String.valueOf(flight.numWeek);
            LocalDate date = LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
            LocalDate mondayOfWeek = date.plusWeeks(numWeekSelectedPrevious);
            LocalDate sundayOfWeek = mondayOfWeek.plusDays(6);
            if (numWeekSelectedPrevious == flight.numWeek  && flight.pernocta != 0){
                if (flight.dateD.isEqual(mondayOfWeek) || (flight.dateD.isBefore(sundayOfWeek) && flight.dateD.isAfter(mondayOfWeek)) || flight.dateD.isEqual(sundayOfWeek)){
                    //flight.type = "P";
                    flightListWeek.add(flight);
                }
            } else if (numWeekSelected.equals(flightNumWeek)){
                flightListWeek.add(flight);
            }
        }
    }

    private String[] getWeeksArray() {
        ArrayList<String> weeksList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int currentWeek = currentDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int currentYear = currentDate.getYear();

        String init = " ";
        weeksList.add(init);

        for (int i = currentWeek; i <= 52; i++) {
            LocalDate startOfWeek = LocalDate.ofYearDay(currentYear, 1)
                    .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, i)
                    .with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
            LocalDate endOfWeek = startOfWeek.plusDays(6);
            String weekString = "Week " + i + ": " + startOfWeek.format(formatter) + " - " + endOfWeek.format(formatter);
            weeksList.add(weekString);
        }

        for (int i = 1; i <= currentWeek; i++) {
            LocalDate startOfWeek = LocalDate.ofYearDay(currentYear + 1, 1)
                    .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, i)
                    .with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
                    .minusDays(1); // Obtenemos el domingo anterior y luego sumamos 1 día para obtener el lunes
            LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)); // Obtenemos el domingo de la misma semana
            String weekString = "Week " + i + ": " + startOfWeek.format(formatter) + " - " + endOfWeek.format(formatter);
            weeksList.add(weekString);
        }

        return weeksList.toArray(new String[0]);
    }

    public void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Archivo");

        //definirVuelosBtn = new JMenuItem("Definir parejas de vuelos");

        //definirVuelosBtn.addActionListener((event) -> definirVuelos());

        //fileMenu.add(definirVuelosBtn);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    public void loadFlights(){
        flightList = null;
        JFileChooser fileChooser = new JFileChooser();

        Workbook workbook = null;

        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Archivos de Excel", "xlsx", "xls");
        fileChooser.setFileFilter(excelFilter);

        int result = fileChooser.showOpenDialog(main.this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            try {
                FileInputStream inputStream = new FileInputStream(file);
                if (filePath.toLowerCase().endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(inputStream);
                } else if (filePath.toLowerCase().endsWith(".xls")) {
                    workbook = new HSSFWorkbook(inputStream);
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de archivo de Excel no válido", "Formato no válido", JOptionPane.WARNING_MESSAGE);
                }
                if (workbook != null) {
                    Sheet sheet = workbook.getSheetAt(0);

                    flightList = DataProcessor.processFlightData(sheet);

                    workbook.close();

                    if (flightList != null){
                        dataTextLabel.setText("Los datos se han cargado correctamente.");
                    } else {
                        dataTextLabel.setText("No se han cargado los datos, el formato del excel no es el correcto.");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Exception: " + ex, "Exception", JOptionPane.WARNING_MESSAGE);
            }
        }
        toFront();
    }

    private void ficheroVictor() {
        System.out.println("Victor");
        JFileChooser fileChooser = new JFileChooser();

        Workbook workbook = null;

        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Archivos de Excel", "xlsx", "xls");
        fileChooser.setFileFilter(excelFilter);

        int result = fileChooser.showOpenDialog(main.this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            try {
                FileInputStream inputStream = new FileInputStream(file);
                if (filePath.toLowerCase().endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(inputStream);
                } else if (filePath.toLowerCase().endsWith(".xls")) {
                    workbook = new HSSFWorkbook(inputStream);
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de archivo de Excel no válido", "Formato no válido", JOptionPane.WARNING_MESSAGE);
                }
                if (workbook!= null) {
                    Sheet sheet = workbook.getSheetAt(0);
                    flightListVictor = DataProcessorVictor.processFlightDataVictor(sheet);
                    if (flightListVictor != null) {
                        LEVELTextLabel.setText("Los datos del fichero Victor se han cargado correctamente.");
                    } else {
                        LEVELTextLabel.setText("No se han cargado los datos  del fichero Victor, el formato del excel no es el correcto.");
                    }
                    workbook.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Exception: " + ex, "Exception", JOptionPane.WARNING_MESSAGE);
            }
        }
        toFront();
    }

    private void ARMS() {
        JFileChooser fileChooser = new JFileChooser();

        Workbook workbook = null;

        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Archivos de Excel", "xlsx", "xls");
        fileChooser.setFileFilter(excelFilter);

        int result = fileChooser.showOpenDialog(main.this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            try {
                FileInputStream inputStream = new FileInputStream(file);
                if (filePath.toLowerCase().endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(inputStream);
                } else if (filePath.toLowerCase().endsWith(".xls")) {
                    workbook = new HSSFWorkbook(inputStream);
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de archivo de Excel no válido", "Formato no válido", JOptionPane.WARNING_MESSAGE);
                }
                if (workbook!= null) {
                    Sheet sheet = workbook.getSheetAt(0);
                    flightListProcessorARMS = DataProcessorLEVEL.processFlightDataLEVEL(sheet);
                    ArrayList<String> matriculasLEVEL = DataProcessorLEVEL.matriculasLEVEL;
                    flightListARMS = JoinLEVEL.joinLEVEL(flightListProcessorARMS, matriculasLEVEL);
/*
                    System.out.println("----------------------------ARMS---------------");
                    for (FlightLEVEL flight : flightListARMS) {
                        System.out.println("asientos " + flight.asientos + " aircraft: " + flight.aircraft + " airline: " + flight.airline + " dateAr: " + flight.dateAr + " numFlightAr: " + flight.numFlightAr + " origenAr: " + flight.origenAr + " destAr: " + flight.destAr +
                                " matricula: " + flight.matricula + " timeAr: " + flight.timeAr + " timeDep: " + flight.timeDep + " dateDep: " + flight.dateDep + " numFlightDep: " + flight.numFlightDep + " origenDep: " + flight.origenDep + " desDep" + flight.destDep + " pernocta " + flight.pernocta);
                    }
                    System.out.println("--------------------------------------------------------------");
*/
                    /*ArrayList<Flight> flightListJoin = JoinLEVEL.associateLEVELs(flightList,flightListLEVEL);
                    if (flightListJoin != null) {
                        flightList = flightListJoin;
                    } else {
                        JOptionPane.showMessageDialog(null, "Cargar otro fichero de excel", "Formato no válido", JOptionPane.WARNING_MESSAGE);
                    }*/

                    if (flightListARMS != null) {
                        LEVELTextLabel.setText("Los datos del fichero ARMS se han cargado correctamente.");
                    } else {
                        LEVELTextLabel.setText("No se han cargado los datos del fichero ARMS, el formato del excel no es el correcto.");
                    }
                    workbook.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Exception: " + ex, "Exception", JOptionPane.WARNING_MESSAGE);
            }
        }
        toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            main main = new main();
            main.setVisible(true);
        });
    }
}
