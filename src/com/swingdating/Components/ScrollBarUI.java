package com.swingdating.Components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import com.swingdating.App;
import com.swingdating.System.AppDesign;

public class ScrollBarUI extends BasicScrollBarUI {
    private boolean isThumbHovered = false;
    private AppDesign appdesign = App.getAppDesign();
    private boolean onContainer = false;
    public ScrollBarUI(boolean onContainer) {
        super();
        this.onContainer = onContainer;
    }
    public ScrollBarUI() {
        super();
    }
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = onContainer ? appdesign.Color_BackgroundOnContainer : appdesign.Color_BackgroundContainer;
        this.trackColor = onContainer ? appdesign.Color_BackgroundContainer : appdesign.Color_BackgroundMain;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(isThumbHovered ? (onContainer ? appdesign.Color_FontPrimary : appdesign.Color_BackgroundOnContainer) : (onContainer ? appdesign.Color_BackgroundOnContainer : appdesign.Color_BackgroundContainer));

        int arc = thumbBounds.width > thumbBounds.height ? thumbBounds.height : thumbBounds.width;
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, arc, arc);

        g2.dispose();
    }

    @Override
    protected void installListeners() {
            super.installListeners();
            scrollbar.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (getThumbBounds().contains(e.getPoint())) {
                        isThumbHovered = true;
                    } else {
                        isThumbHovered = false;
                    }
                    scrollbar.repaint();
                }
            });

            scrollbar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    isThumbHovered = false;
                    scrollbar.repaint();
                }
            });
        }
        
}
