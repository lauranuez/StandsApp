import javax.swing.*;
import java.awt.*;

public class JScrollPaneWithRowHeaders extends JScrollPane {
    private JTable table;

    public JScrollPaneWithRowHeaders(JTable table, JTable rowHeader){
        super(table);
        this.table = table;
        setRowHeaderView(rowHeader);
        setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, rowHeader.getTableHeader());
        setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JLabel(" "));
    }

    public void setRowHeaderView(Component view) {
        JViewport viewport = new JViewport();
        viewport.setView(view);
        viewport.setPreferredSize(view.getPreferredSize());
        super.setRowHeaderView(viewport);
    }


}
