package ui;

import facade.CarePlatformFacade;
import ui.cards.AppointmentsCard;
import ui.cards.HeroCard;
import ui.cards.ProgressCard;
import ui.cards.ReportsCard;
import ui.cards.TimelineCard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/*
 * Main dashboard home page.
 *
 * Responsibility:
 * - Layout only
 * - Combines dashboard cards
 */
public class HomePanel extends JPanel {

    private static final Color BACKGROUND =
            new Color(248, 246, 253);

    public HomePanel(
            CarePlatformFacade facade
    ) {

        setLayout(new BorderLayout(16, 16));

        setBackground(BACKGROUND);

        setBorder(
                new EmptyBorder(18, 18, 18, 18)
        );

        initializeUI(facade);
    }

    private void initializeUI(
            CarePlatformFacade facade
    ) {

        // Top section
        add(
                new HeroCard(facade),
                BorderLayout.NORTH
        );

        // Middle dashboard cards
        JPanel middle =
                new JPanel(
                        new GridLayout(1, 3, 16, 16)
                );

        middle.setOpaque(false);

        middle.add(
                new AppointmentsCard(facade)
        );

        middle.add(
                new ReportsCard(facade)
        );

        middle.add(
                new ProgressCard(facade)
        );

        add(middle, BorderLayout.CENTER);

        // Bottom timeline
        add(
                new TimelineCard(),
                BorderLayout.SOUTH
        );
    }
}