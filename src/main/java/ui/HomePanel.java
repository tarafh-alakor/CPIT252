package ui;

import facade.CarePlatformFacade;
import ui.cards.AppointmentsCard;
import ui.cards.HeroCard;
import ui.cards.ProgressCard;
import ui.cards.ReportsCard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import ui.utils.UIConstants;

/* Main dashboard panel displayed after login.
 * Organizes:
 * - child overview
 * - appointments
 * - reports
 * - progress tracking
 * Uses reusable dashboard cards
 * for modular UI design.*/
public class HomePanel extends JPanel {

    public HomePanel(CarePlatformFacade facade) {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND);
        setBorder(new EmptyBorder(14, 14, 14, 14));
        initializeUI(facade);
    }

    private void initializeUI(CarePlatformFacade facade) {
        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 12, 0);

        gbc.gridy = 0;
        gbc.weighty = 0.26;
        page.add(new HeroCard(facade), gbc);

        JPanel middle = new JPanel(new GridLayout(1, 2, 14, 0));
        middle.setOpaque(false);
        middle.add(new AppointmentsCard(facade));
        middle.add(new ReportsCard(facade));

        gbc.gridy = 1;
        gbc.weighty = 0.42;
        page.add(middle, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.24;
        gbc.insets = new Insets(0, 0, 0, 0);
        page.add(new ProgressCard(facade), gbc);

        add(page, BorderLayout.CENTER);
    }
}
