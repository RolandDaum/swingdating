package com.swingdating.Components;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.swingdating.System.AppDesign;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Custome Action buttons for the titlebar only -> Max, Min, close
public class TitlebarActionButton extends JButton {
    AppDesign appdesign;
    Boolean exitbutton = false;

    // Constructor
    /**
     * @param assetName String defined in AppDesign class
     * @param appdesign AppDesign
     * @param exitbutton Boolean true if button is supposed to be an exit button with hover effect etc.
     */
    public TitlebarActionButton(String assetName, AppDesign appdesign, Boolean exitbutton) {
        this(assetName, appdesign);
        this.exitbutton = exitbutton;
    }
    /**
     * @param assetName String defined in AppDesign class
     * @param appdesign AppDesign
     */
    public TitlebarActionButton(String assetName, AppDesign appdesign) {
        this.appdesign = appdesign;
        setPreferredSize(new Dimension(appdesign.titlebarHeight-10, appdesign.titlebarHeight-10));
        setBorder(new LineBorder(appdesign.Color_BorderLight, appdesign.BorderThickness, true));
        setBackground(appdesign.Color_BackgroundContainer);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setButtonIcon(assetName);

        // Hover-Effekt
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (exitbutton) {
                    setBackground(appdesign.Color_AccentSecondary);
                } else {
                    setBackground(appdesign.Color_BackgroundOnContainer);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(appdesign.Color_BackgroundContainer);
            }
        });

        
    }
    
    /**
     * Add the provided assetName as Icon
     * @param assetName
     */
    public void setButtonIcon(String assetName) {
        setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/com/swingdating/assets/" + assetName).getImage().getScaledInstance(appdesign.titlebarHeight-20, appdesign.titlebarHeight-20, Image.SCALE_SMOOTH)));
    }


    // UI Stuff
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillOval(0, 0, getWidth(), getHeight());
        super.paintComponent(g2);
        g2.dispose();
    }
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(appdesign.Color_BorderLight);
        g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
        g2.dispose();
    }
    
}
