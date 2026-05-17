package ui.cards;

import facade.CarePlatformFacade;
import model.ChildProfile;
import ui.components.RoundedPanel;
import ui.components.ProgressRing;
import ui.utils.UIConstants;
import ui.utils.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ProgressCard extends RoundedPanel {

    private static final Color GREEN
            = new Color(83, 181, 122);

    private static final Color MUTED
            = new Color(130, 126, 154);

    private static final Color SOFT_PANEL
            = new Color(252, 250, 255);

    public ProgressCard(CarePlatformFacade facade) {

        super(24, Color.WHITE, true);

        setLayout(new BorderLayout(10, 10));

        add(
                UIHelper.sectionTitle(
                        "Personal Summary"
                ),
                BorderLayout.NORTH
        );
        JPanel center
                = new JPanel(new BorderLayout(0, 14));

        center.setOpaque(false);

        int progress = 0;
        List<ChildProfile> children = facade.getChildren();
        if (!children.isEmpty()) {
            progress = children.get(children.size() - 1).getProgressPercentage();
        }

        center.add(
                new ProgressRing(progress),
                BorderLayout.CENTER
        );

        JPanel goals
                = new JPanel(
                        new GridLayout(3, 1, 0, 7)
                );

        goals.setOpaque(false);

        goals.add(goal(
                "Improve word pronunciation",
                progress >= 70
        ));

        goals.add(goal(
                "Focus for 10 minutes",
                progress >= 50
        ));

        goals.add(goal(
                "Participate in class",
                progress >= 85
        ));

        center.add(goals, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);
    }

    private JLabel goal(
            String text,
            boolean done
    ) {

        JLabel label = new JLabel(
                (done ? "Completed: " : "Pending: ")
                + text
        );

        label.setOpaque(true);

        label.setBackground(SOFT_PANEL);

        label.setBorder(
                new EmptyBorder(8, 10, 8, 10)
        );

        label.setForeground(
                done ? GREEN : MUTED
        );

        label.setFont(UIConstants.FONT_BOLD);

        return label;
    }
}
