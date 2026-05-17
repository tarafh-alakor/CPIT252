package ui.cards;

import ui.utils.UIConstants;

import javax.swing.*;
import java.awt.*;

/*
 * Compact horizontal progress bar used on the Home dashboard.
 */
public class HomeProgressBar extends JComponent {

    private final int value;

    public HomeProgressBar(int value) {
        this.value = Math.max(0, Math.min(100, value));
        setPreferredSize(new Dimension(0, 16));
    }

    //Draws the custom rounded progress bar.
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = 8;
        int y = (getHeight() - height) / 2;
        int arc = 8;

        graphics.setColor(new Color(235, 231, 247));
        graphics.fillRoundRect(0, y, width, height, arc, arc);

        int fillWidth = Math.max(8, (int) (width * (value / 100.0)));
        graphics.setColor(UIConstants.PURPLE);
        graphics.fillRoundRect(0, y, fillWidth, height, arc, arc);

        graphics.dispose();
    }
}
