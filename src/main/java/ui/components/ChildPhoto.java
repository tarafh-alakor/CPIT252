package ui.components;

import javax.swing.*;
import java.awt.*;

/*
 * Illustrated child profile component.
 *
 * Avoids external image files.
 */
public class ChildPhoto extends JComponent {

    public ChildPhoto() {

        setPreferredSize(
                new Dimension(92, 92)
        );
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D graphics =
                (Graphics2D) g.create();

        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Background
        graphics.setColor(
                new Color(255, 230, 226)
        );

        graphics.fillRoundRect(
                0,
                0,
                90,
                90,
                24,
                24
        );

        // Hair
        graphics.setColor(
                new Color(115, 78, 52)
        );

        graphics.fillOval(
                25,
                12,
                40,
                44
        );

        // Face
        graphics.setColor(
                new Color(245, 196, 158)
        );

        graphics.fillOval(
                29,
                22,
                32,
                32
        );

        // Shirt
        graphics.setColor(
                new Color(255, 156, 178)
        );

        graphics.fillRoundRect(
                22,
                55,
                48,
                28,
                18,
                18
        );

        // Eyes
        graphics.setColor(
                new Color(55, 45, 65)
        );

        graphics.fillOval(38, 35, 3, 3);
        graphics.fillOval(51, 35, 3, 3);

        // Smile
        graphics.drawArc(
                40,
                39,
                14,
                8,
                190,
                160
        );

        graphics.dispose();
    }
}
