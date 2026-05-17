package ui.cards;

import facade.CarePlatformFacade;
import model.ChildProfile;
import ui.components.RoundedPanel;
import ui.utils.UIConstants;
import ui.utils.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
/*
 * Dashboard card that displays
 * child progress and achievement summary.
 */
public class ProgressCard extends RoundedPanel {

    public ProgressCard(CarePlatformFacade facade) {
        super(22, Color.WHITE, true);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(12, 14, 12, 14));

        int progress = 0;
        List<ChildProfile> children = facade.getChildren();
        if (!children.isEmpty()) {
            progress = children.get(children.size() - 1).getProgressPercentage();
        }

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(UIHelper.sectionTitle("Personal Summary"), BorderLayout.WEST);
        JLabel percent = new JLabel(progress + "%");
        percent.setFont(new Font("Segoe UI", Font.BOLD, 19));
        percent.setForeground(UIConstants.PURPLE);
        header.add(percent, BorderLayout.EAST);

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setOpaque(false);
        center.add(new HomeProgressBar(progress), BorderLayout.NORTH);

        JPanel goals = new JPanel(new GridLayout(1, 3, 8, 0));
        goals.setOpaque(false);
        goals.add(goal("Completed", "Word pronunciation", progress >= 70));
        goals.add(goal("Completed", "Focus 10 minutes", progress >= 50));
        goals.add(goal("Pending", "Class participation", progress >= 85));
        center.add(goals, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private JLabel goal(String status, String text, boolean done) {
        JLabel label = new JLabel("<html><center><b>" + (done ? status : "Pending") + "</b><br>" + UIHelper.safe(text) + "</center></html>");
        label.setOpaque(true);
        label.setBackground(new Color(252, 250, 255));
        label.setBorder(new EmptyBorder(8, 10, 8, 10));
        label.setForeground(done ? UIConstants.GREEN : new Color(130, 126, 154));
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return label;
    }
}
