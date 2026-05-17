package ui.components;

import javax.swing.*;
import java.awt.*;

/*
 * Small colored status dot.
 *
 * Used in:
 * - timeline items
 * - status indicators
 */
public class StatusDot extends JComponent {

    private final Color color;

    public StatusDot(Color color) {

        this.color = color;

        setPreferredSize(
                new Dimension(18, 18)
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
          graphics.setColor(color);

        graphics.fillOval(
                5,
                5,
                8,
                8
        );

        graphics.dispose();
    }
}
