package ui.utils;

import ui.components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIHelper {

    private UIHelper() {
    }

    /*
     * Reusable dashboard card.
     */
    public static RoundedPanel createCard(String title) {

        RoundedPanel card
                = new RoundedPanel(
                        24,
                        UIConstants.CARD,
                        true
                );

        card.setLayout(new BorderLayout(10, 10));

        card.setBorder(
                new EmptyBorder(18, 18, 20, 18)
        );

        card.add(
                sectionTitle(title),
                BorderLayout.NORTH
        );

        return card;
    }

    /*
     * Reusable section title.
     */
    public static JLabel sectionTitle(String title) {

        JLabel label = new JLabel(title);

        label.setFont(UIConstants.TITLE);

        label.setForeground(UIConstants.TEXT);

        return label;
    }

    /*
     * Safe HTML text.
     */
    public static String safe(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
    }

    /*
 * Reusable dashboard row card.
     */
    public static JPanel rowCard(
            String text,
            Color color
    ) {

        RoundedPanel row
                = new RoundedPanel(
                        18,
                        new Color(252, 250, 255),
                        false
                );

        row.setLayout(new BorderLayout(12, 0));

        row.setBorder(
                new EmptyBorder(10, 12, 10, 12)
        );

        JPanel bar = new JPanel();

        bar.setBackground(color);

        bar.setPreferredSize(
                new Dimension(5, 1)
        );

        JLabel label
                = new JLabel(
                        "<html><div style='width:245px'>"
                        + safe(text)
                        + "</div></html>"
                );

        label.setFont(UIConstants.FONT);

        label.setForeground(UIConstants.TEXT);

        row.add(bar, BorderLayout.WEST);

        row.add(label, BorderLayout.CENTER);

        return row;
    }
}
