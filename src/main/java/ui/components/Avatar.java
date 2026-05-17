package ui.components;

import javax.swing.*;
import java.awt.*;

/*
 * Reusable circular avatar component.
 *
 * Displays:
 * - initials
 * - colored circular background
 */
public class Avatar extends JComponent {

    private final String text;
    private final int size;

    private final Color background;
    private final Color foreground;

    public Avatar(
            String text,
            int size,
            Color background,
            Color foreground
    ) {

        this.text = text;
        this.size = size;
        this.background = background;
        this.foreground = foreground;

        setPreferredSize(
                new Dimension(size, size)
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

        // Background circle
        graphics.setColor(background);

        graphics.fillOval(
                0,
                0,
                size - 1,
                size - 1
        );

        // Text
        graphics.setColor(foreground);

        graphics.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        size / 2
                )
        );

        FontMetrics metrics =
                graphics.getFontMetrics();

        graphics.drawString(
                text,
                (size - metrics.stringWidth(text)) / 2,
                (size + metrics.getAscent()) / 2 - 3
        );

        graphics.dispose();
    }
}