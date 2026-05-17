package ui.cards;

import facade.CarePlatformFacade;
import model.Report;
import ui.components.RoundedPanel;
import ui.utils.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReportsCard extends RoundedPanel {

    private static final Color BLUE
            = new Color(72, 169, 215);

    public ReportsCard(
            CarePlatformFacade facade
    ) {

        super(24, Color.WHITE, true);

        setLayout(new BorderLayout(10, 10));

        add(
                UIHelper.sectionTitle(
                        "Latest Reports"
                ),
                BorderLayout.NORTH
        );

        JPanel list
                = new JPanel(
                        new GridLayout(3, 1, 0, 10)
                );
        list.setOpaque(false);

        List<Report> reports
                = facade.getReports();

        for (int i = reports.size() - 1;
                i >= Math.max(0, reports.size() - 3);
                i--) {

            Report report = reports.get(i);

            list.add(
                    UIHelper.rowCard(
                            report.getRole()
                            + " - "
                            + report.getText(),
                            BLUE
                    )
            );
        }

        add(list, BorderLayout.CENTER);
    }
}
