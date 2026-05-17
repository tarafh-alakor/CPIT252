package ui.components;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

// Reusable rounded card used by the whole interface.
// The soft shadow makes the GUI closer to the reference dashboard design.
public class RoundedPanel extends JPanel {
    private final int radius;
    private final Color backgroundColor;
    private boolean shadow = true;

    public RoundedPanel(int radius, Color backgroundColor) {
        this.radius = radius;
        this.backgroundColor = backgroundColor;
        setOpaque(false);
    }

    public RoundedPanel(int radius, Color backgroundColor, boolean shadow) {
        this(radius, backgroundColor);
        this.shadow = shadow;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (shadow) {
            graphics.setColor(new Color(80, 60, 120, 20));
            graphics.fillRoundRect(4, 6, getWidth() - 8, getHeight() - 10, radius, radius);
        }

        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 10, radius, radius);
        graphics.dispose();
        super.paintComponent(g);
    }
}
