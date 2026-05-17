package ui.cards;

import facade.CarePlatformFacade;
import model.CareTeamMember;
import model.ChildProfile;
import ui.components.ChildPhoto;
import ui.components.MemberBox;
import ui.components.RoundedPanel;
import ui.utils.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/*
 * Dashboard hero card.
 *
 * Responsibility:
 * - Displays the latest child profile from the system data
 * - Displays care team members from the system data
 */
public class HeroCard extends RoundedPanel {

    private final CarePlatformFacade facade;

    public HeroCard(CarePlatformFacade facade) {

        super(
                28,
                Color.WHITE,
                true
        );

        this.facade = facade;

        setPreferredSize(
                new Dimension(0, 150)
        );

        setLayout(new BorderLayout(20, 0));

        setBorder(
                new EmptyBorder(18, 22, 18, 22)
        );

        initializeUI();
    }

    private void initializeUI() {

        add(createChildSection(), BorderLayout.WEST);

        add(createTeamSection(), BorderLayout.EAST);
    }

    /*
     * Left child section.
     * It reads from the repository through the Facade, so when the mother adds
     * a new child, the dashboard can show the updated child data.
     */
    private JPanel createChildSection() {

        JPanel child =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                18,
                                0
                        )
                );

        child.setOpaque(false);

        child.add(new ChildPhoto());

        List<ChildProfile> children = facade.getChildren();

        String childHtml;
        if (children.isEmpty()) {
            childHtml = "<html>"
                    + "<h2 style='margin:0'>No child added</h2>"
                    + "<p style='margin:4px 0'>Add a child from Profile</p>"
                    + "<p style='color:#6b55a8;margin:4px 0'>Profile details</p>"
                    + "</html>";
        } else {
            ChildProfile selectedChild = children.get(children.size() - 1);
            childHtml = "<html>"
                    + "<h2 style='margin:0'>" + UIHelper.safe(selectedChild.getName()) + "</h2>"
                    + "<p style='margin:4px 0'>" + selectedChild.getAge() + " years old</p>"
                    + "<p style='margin:4px 0'>" + UIHelper.safe(selectedChild.getCondition()) + "</p>"
                    + "<p style='color:#6b55a8;margin:4px 0'>Profile details</p>"
                    + "</html>";
        }

        child.add(new JLabel(childHtml));

        return child;
    }

    /*
     * Right team section.
     * It reads the care team members added by the mother.
     */
    private JPanel createTeamSection() {

        JPanel team =
                new JPanel(
                        new GridLayout(1, 4, 14, 0)
                );

        team.setOpaque(false);

        List<CareTeamMember> members = facade.getCareTeam();

        if (members.isEmpty()) {
            team.add(new MemberBox("?", "No member", "Add from Care Team"));
        } else {
            int start = Math.max(0, members.size() - 4);
            for (int i = start; i < members.size(); i++) {
                CareTeamMember member = members.get(i);
                String letter = member.getName().isEmpty()
                        ? "?"
                        : member.getName().substring(0, 1).toUpperCase();
                team.add(
                        new MemberBox(
                                letter,
                                member.getName(),
                                member.getRole()
                        )
                );
            }
        }

        JPanel wrapper =
                new JPanel(new BorderLayout(0, 8));

        wrapper.setOpaque(false);

        wrapper.add(
                UIHelper.sectionTitle("Care Team"),
                BorderLayout.NORTH
        );

        wrapper.add(team, BorderLayout.CENTER);

        return wrapper;
    }
}
