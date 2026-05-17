package ui.components;

import ui.utils.UIConstants;
import ui.utils.UIHelper;

import javax.swing.*;
import java.awt.*;

/*
 * Reusable care team member component.
 */
public class MemberBox extends JPanel {

    public MemberBox(
            String letter,
            String name,
            String role
    ) {

        setLayout(
                new BorderLayout(0, 6)
        );

        setOpaque(false);

        initializeUI(
                letter,
                name,
                role
        );
    }

    private void initializeUI(
            String letter,
            String name,
            String role
    ) {

        JLabel text =
                new JLabel(
                        "<html><center><b>"
                                + UIHelper.safe(name)
                                + "</b><br>"
                                + "<span style='color:#888888'>"
                                + UIHelper.safe(role)
                                + "</span></center></html>",
                        SwingConstants.CENTER
                );

        text.setFont(UIConstants.FONT);

        add(
                new Avatar(
                        letter,
                        46,
                        new Color(238, 233, 255),
                        UIConstants.PURPLE
                ),
                BorderLayout.NORTH
        );

        add(text, BorderLayout.CENTER);
    }
}
