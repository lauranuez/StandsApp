import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;

public class InputDialogMTO extends JDialog{
    private JTextField standText;
    private JTextField nameText;
    private JTextField doorText;
    private static JComboBox<LocalTime> startTimeComboBox;
    private static JComboBox<LocalTime> endTimeComboBox;

    public InputDialogMTO(JFrame parent, Flight flight, ArrayList<LocalTime> columnNames) {
        super(parent, "Cambio de MTO", true);

        nameText = new JTextField(8);
        nameText.setText(flight.id);
        standText = new JTextField(8);
        standText.setText(flight.stand);
        doorText = new JTextField(8);
        startTimeComboBox = new JComboBox<>(columnNames.toArray(new LocalTime[0]));
        startTimeComboBox.setSelectedItem(flight.timeA);
        endTimeComboBox = new JComboBox<>(columnNames.toArray(new LocalTime[0]));
        endTimeComboBox.setSelectedItem(flight.timeD);

        String flightInfo = "Mantenimiento: " + flight.id + " hora inicio: " + flight.timeA + " hora fin: " + flight.timeD;

        JLabel flightInfoLabel = new JLabel(flightInfo);
        flightInfoLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        JButton deleteButton = new JButton("Delete");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                standText.setText("");
                doorText.setText("");
                LocalTime close = LocalTime.of(0, 0);
                startTimeComboBox.setSelectedItem(close);
                dispose();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String day = flight.dayWeek;
                StandsMain.flightListWeek.remove(flight);
                StandsMain.updateTable(day);
                dispose();
            }
        });

        JPanel infoPanel = new JPanel();
        infoPanel.add(flightInfoLabel);
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Id:"));
        panel.add(nameText);
        panel.add(new JLabel("Stand:"));
        panel.add(standText);
        panel.add(new JLabel("Puerta:"));
        panel.add(doorText);
        panel.add(new JLabel("Hora inicio:"));
        panel.add(startTimeComboBox);
        panel.add(new JLabel("Hora final:"));
        panel.add(endTimeComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(infoPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public String getTextDoorValue() {
        return doorText.getText();
    }

    public String getTextStandValue() {
        return standText.getText();
    }

    public LocalTime getEndTimeComboBox() {
        LocalTime selectedEndTime = (LocalTime) endTimeComboBox.getSelectedItem();
        return selectedEndTime;
    }

    public LocalTime getStartTimeComboBox() {
        LocalTime selectedStartTime = (LocalTime) startTimeComboBox.getSelectedItem();
        return selectedStartTime;
    }
    public String getIdMTO(){
        return nameText.getText();
    }
}
