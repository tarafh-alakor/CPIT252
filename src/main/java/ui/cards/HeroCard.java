package ui.cards;

import facade.CarePlatformFacade;
import model.CareTeamMember;
import model.ChildProfile;
import ui.components.ChildPhoto;
import ui.components.RoundedPanel;
import ui.utils.UIConstants;
import ui.utils.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import ui.components.MemberBox;

/*
 * Compact dashboard hero card.
 */
public class HeroCard extends RoundedPanel {

    private final CarePlatformFacade facade;

    public HeroCard(CarePlatformFacade facade) {
        super(24, Color.WHITE, true);
        this.facade = facade;
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(14, 18, 14, 18));
        initializeUI();
    }

    private void initializeUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 18);

        gbc.gridx = 0;
        gbc.weightx = 0.46;
        add(createChildSection(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.54;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(createTeamSection(), gbc);
    }

    private JPanel createChildSection() {
        JPanel child = new JPanel(new BorderLayout(16, 0));
        child.setOpaque(false);
        child.add(new ChildPhoto(), BorderLayout.WEST);

        List<ChildProfile> children = facade.getChildren();
        String childHtml;
        if (children.isEmpty()) {
            childHtml = "<html><div style='width:260px'><h2 style='margin:0'>No child added</h2>"
                    + "<p style='margin:4px 0'>Add a child from Profile</p>"
                    + "<p style='color:#6b55a8;margin:4px 0'>Profile details</p></div></html>";
        } else {
            ChildProfile selectedChild = children.get(children.size() - 1);
            childHtml = "<html><div style='width:260px'><h2 style='margin:0'>" + UIHelper.safe(selectedChild.getName()) + "</h2>"
                    + "<p style='margin:4px 0'>" + selectedChild.getAge() + " years old</p>"
                    + "<p style='margin:4px 0'>" + UIHelper.safe(selectedChild.getCondition()) + "</p>"
                    + "<p style='color:#6b55a8;margin:4px 0'>Profile details</p></div></html>";
        }

        JLabel childText = new JLabel(childHtml);
        childText.setFont(UIConstants.FONT);
        child.add(childText, BorderLayout.CENTER);
        return child;
    }

    private JPanel createTeamSection() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 10));
        wrapper.setOpaque(false);

        JLabel title = UIHelper.sectionTitle("Care Team");
        title.setHorizontalAlignment(SwingConstants.LEFT);
        wrapper.add(title, BorderLayout.NORTH);

        JPanel team = new JPanel(new GridLayout(1, 3, 10, 0));
        team.setOpaque(false);

        List<CareTeamMember> members = facade.getCareTeam();
        if (members.isEmpty()) {
            team.add(new MemberBox("?", "No member", "Add from Care Team"));
        } else {
            int start = Math.max(0, members.size() - 3);
            for (int i = start; i < members.size(); i++) {
                CareTeamMember member = members.get(i);
                String letter = member.getName().isEmpty() ? "?" : member.getName().substring(0, 1).toUpperCase();
                team.add(new MemberBox(letter, member.getName(), member.getRole()));
            }
        }

        wrapper.add(team, BorderLayout.CENTER);
        return wrapper;
    }
}
