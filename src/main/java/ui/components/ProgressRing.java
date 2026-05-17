package ui.components;

import javax.swing.*;
import java.awt.*;

/*
 * Circular progress ring component.
 *
 * Used in:
 * - dashboard summary
 * - progress tracking
 */
public class ProgressRing extends JComponent {

    private final int value;

    public ProgressRing(int value) {

        this.value = value;

        setPreferredSize(
                new Dimension(145, 145)
        );
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D graphics
                = (Graphics2D) g.create();

        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        int size
                = Math.min(
                        getWidth(),
                        getHeight()
                ) - 30;

        int x
                = (getWidth() - size) / 2;

        int y = 8;

        graphics.setStroke(
                new BasicStroke(
                        10,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                )
        );

        // Background circle
        graphics.setColor(
                new Color(231, 232, 245)
        );

        graphics.drawOval(
                x,
                y,
                size,
                size
        );

        // Progress arc
        graphics.setColor(
                new Color(76, 176, 224)
        );

        graphics.drawArc(
                x,
                y,
                size,
                size,
                90,
                -(int) (360 * (value / 100.0))
        );

        // Percentage text
        graphics.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        graphics.setColor(
                new Color(45, 45, 65)
        );

        String label = value + "%";

        FontMetrics metrics
                = graphics.getFontMetrics();

        graphics.drawString(
                label,
                (getWidth()
                - metrics.stringWidth(label)) / 2,
                y + size / 2 + 8
        );
// Subtitle
        graphics.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        String subtitle = "Goal Progress";

        metrics = graphics.getFontMetrics();

        graphics.setColor(
                new Color(125, 125, 145)
        );

        graphics.drawString(
                subtitle,
                (getWidth()
                - metrics.stringWidth(subtitle)) / 2,
                y + size + 25
        );

        graphics.dispose();
    }
}
