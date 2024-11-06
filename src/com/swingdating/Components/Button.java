package com.swingdating.Components;
import com.swingdating.App;
import com.swingdating.System.AppDesign;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class Button extends JButton {

    private AppDesign appdesign;

    public Button(String title, AppDesign appdesign) {
        super(title);
        this.appdesign = appdesign;

        setMinimumSize(new Dimension(appdesign.inputFieldWidth/3*2,appdesign.inputFieldHeight));
        setMaximumSize(new Dimension(appdesign.inputFieldWidth/3*2,appdesign.inputFieldHeight));
        setPreferredSize(new Dimension(appdesign.inputFieldWidth/3*2,appdesign.inputFieldHeight));

        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setBorderPainted(false);
        setForeground(appdesign.Color_FontPrimary);
        setFont(new Font("Roboto Medium", Font.PLAIN, 14));



        // Hover-Effekt
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(appdesign.Color_BackgroundOnContainer);
                App.getAppInstance().setCursor(Cursor.HAND_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(appdesign.Color_BackgroundContainer);
                App.getAppInstance().setCursor(Cursor.DEFAULT_CURSOR);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Hintergrund zeichnen
        g2.setColor(getModel().isRollover() ? appdesign.Color_BackgroundOnContainer : appdesign.Color_BackgroundContainer);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());

        // 1px Border zeichnen
        g2.setColor(appdesign.Color_BorderLight);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());

        super.paintComponent(g2);
        g2.dispose();
    }
}
