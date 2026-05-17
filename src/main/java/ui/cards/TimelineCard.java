package ui.cards;

import ui.components.RoundedPanel;
import ui.components.StatusDot;
import ui.utils.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TimelineCard extends RoundedPanel {

    private static final Color BLUE
            = new Color(72, 169, 215);

    private static final Color GREEN
            = new Color(83, 181, 122);

    private static final Color ORANGE
            = new Color(237, 164, 67);

    private static final Color PINK
            = new Color(236, 118, 153);

    private static final Color PURPLE
            = new Color(94, 75, 166);

    public TimelineCard() {

        super(24, Color.WHITE, true);

        setPreferredSize(
                new Dimension(0, 160)
        );

        setLayout(new BorderLayout(10, 10));

        add(
                UIHelper.sectionTitle("Timeline"),
                BorderLayout.NORTH
        );

        JPanel line
                = new JPanel(
                        new GridLayout(1, 5, 12, 0)
                );

        line.setOpaque(false);

        line.add(item(
                "Speech session report",
                "2 hours ago",
                BLUE
        ));

        line.add(item(
                "School note added",
                "1 day ago",
                GREEN
        ));

        line.add(item(
                "New appointment created",
                "3 days ago",
                ORANGE
        ));
        line.add(item(
                "Medical follow-up report",
                "1 week ago",
                PINK
        ));

        line.add(item(
                "Child profile updated",
                "1 week ago",
                PURPLE
        ));

        add(line, BorderLayout.CENTER);
    }

    private JPanel item(
            String title,
            String date,
            Color color
    ) {

        RoundedPanel item
                = new RoundedPanel(18, Color.WHITE, false);

        item.setLayout(new BorderLayout(0, 8));

        item.setBorder(
                new EmptyBorder(10, 12, 10, 12)
        );

        item.add(
                new StatusDot(color),
                BorderLayout.NORTH
        );

        item.add(
                new JLabel(
                        "<html><center><b>"
                        + UIHelper.safe(title)
                        + "</b><br>"
                        + "<span style='color:#888888'>"
                        + UIHelper.safe(date)
                        + "</span></center></html>",
                        SwingConstants.CENTER
                ),
                BorderLayout.CENTER
        );

        return item;
    }
}
