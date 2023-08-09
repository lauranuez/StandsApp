import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputDialog extends JDialog {
    private JTextField standText;
    private JTextField doorText;

    public InputDialog(JFrame parent, Flight flight) {
        super(parent, "Cambio de stand/puerta", true);

        standText = new JTextField(8);
        standText.setText(flight.stand);
        doorText = new JTextField(8);

        String flightInfo = "Indicativo del vuelo: " + flight.id + " hora llegada: " + flight.timeA + " hora salida: " + flight.timeD;

        JLabel flightInfoLabel = new JLabel(flightInfo);
        flightInfoLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                standText.setText("");
                doorText.setText("");
                dispose();
            }
        });

        JPanel infoPanel = new JPanel();
        infoPanel.add(flightInfoLabel);
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Stand:"));
        panel.add(standText);
        panel.add(new JLabel("Puerta:"));
        panel.add(doorText);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

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
}
