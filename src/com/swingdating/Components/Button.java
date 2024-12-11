package com.swingdating.Components;

import com.swingdating.System.AppDesign;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

// Customized Button Design
public class Button extends JButton {

    private AppDesign appdesign;
    private boolean isHoverd = false;

    // Constructors
    /**
     * Creates Button Component
     * @param title String Button title
     * @param appdesign AppDesign appdesign Object
     * @param onclick Runnable Method executed when the button has been clicked
     */
    public Button(String title, AppDesign appdesign, Runnable onclick) {
        this(title, appdesign, new Dimension((appdesign.inputFieldWidth/2),appdesign.inputFieldHeight), onclick);
    }
    /**
     * Creates Button Component
     * @param title String Button title
     * @param appdesign AppDesign appdesign Object
     * @param Dimension Dimension size of the button
     * @param onclick Runnable Method executed when the button has been clicked
     */
    public Button(String title, AppDesign appdesign, Dimension Dimension, Runnable onclick) {
        super(title);
        this.appdesign = appdesign;

        setMinimumSize(Dimension);
        setMaximumSize(Dimension);
        setPreferredSize(Dimension);

        setLayout(null);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setBorderPainted(false);
        setForeground(appdesign.Color_FontPrimary);
        setFont(new Font("Roboto Medium", Font.PLAIN, 14));

        // Hover Eventlistener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHoverd = true;
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHoverd = false;
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                repaint();
            }
        });
        
        // Click Eventlistener
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onclick != null) {
                    onclick.run();
                }

            }
        });
    }

    // UI Changing stuff
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        g2.setColor(isHoverd ? appdesign.Color_BackgroundOnContainer : appdesign.Color_BackgroundContainer);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
    
        g2.setColor(appdesign.Color_BorderLight);
        g2.setStroke(new BasicStroke(appdesign.BorderThickness));
        g2.drawRoundRect(appdesign.BorderThickness/2, appdesign.BorderThickness/2, getWidth()- appdesign.BorderThickness, getHeight() - appdesign.BorderThickness, appdesign.BorderRadiusComponents, appdesign.BorderRadiusComponents);
        setForeground(isHoverd ? appdesign.Color_FontSecondary : appdesign.Color_FontPrimary);

        super.paintComponent(g2);
        g2.dispose();
    }
}