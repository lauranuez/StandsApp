import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FlightPairsTable extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private Set<Integer> newRows = new HashSet<>();

    public FlightPairsTable(ArrayList<FlightPairs> flightPairs) {
        setTitle("Vuelos");
        setSize(800, 200);
        setLocationRelativeTo(null);

        String[] columnNames = {"Cía", "Num L", "Num S", "Avión"};
        Object[][] data = new Object[flightPairs.size()][4];

        for (int i = 0; i < flightPairs.size(); i++) {
            FlightPairs flight = flightPairs.get(i);
            data[i][0] = flight.airline;
            data[i][1] = flight.numFlightAr;
            data[i][2] = flight.numFlightDep;
            data[i][3] = flight.aircraft;
        }

        tableModel = new DefaultTableModel(data, columnNames);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Archivo");

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener((event) -> saveDataToExcel());
        getContentPane().add(saveButton, BorderLayout.SOUTH);
    }


    private void saveDataToExcel() {
    }
}
