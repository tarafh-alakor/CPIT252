package ui.cards;

import facade.CarePlatformFacade;
import model.Report;
import ui.components.RoundedPanel;
import ui.utils.UIConstants;
import ui.utils.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
/*
 * Dashboard card that displays
 * the latest submitted reports.
 */
public class ReportsCard extends RoundedPanel {

    public ReportsCard(CarePlatformFacade facade) {
        super(22, Color.WHITE, true);
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(12, 14, 12, 14));
        add(UIHelper.sectionTitle("Latest Reports"), BorderLayout.NORTH);

        JPanel list = new JPanel(new GridLayout(0, 1, 0, 8));
        list.setOpaque(false);

        List<Report> reports = facade.getReports();
        int shown = 0;
        for (int i = reports.size() - 1; i >= 0 && shown < 2; i--, shown++) {
            Report report = reports.get(i);
            list.add(UIHelper.rowCard(report.getRole() + " - " + report.getText(), UIConstants.BLUE));
        }
        if (reports.isEmpty()) {
            list.add(UIHelper.rowCard("No reports added yet.", UIConstants.BLUE));
        }

        add(list, BorderLayout.CENTER);
    }
}
